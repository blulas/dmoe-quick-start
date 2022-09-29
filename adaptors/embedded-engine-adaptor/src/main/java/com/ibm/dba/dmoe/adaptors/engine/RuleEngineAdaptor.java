package com.ibm.dba.dmoe.adaptors.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.kie.api.KieServices;
import org.kie.api.KieBase;
import org.kie.api.command.Command;
import org.kie.api.command.ExecutableCommand;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.StatelessKieSession;

import org.kie.internal.command.CommandFactory;
import org.drools.core.command.runtime.BatchExecutionCommandImpl;
import org.drools.core.command.runtime.rule.InsertObjectCommand;
import org.drools.core.command.runtime.process.StartProcessCommand;
import org.drools.core.command.runtime.rule.GetObjectsCommand;
import org.drools.core.runtime.impl.ExecutionResultImpl;

import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;

public class RuleEngineAdaptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleEngineAdaptor.class);
    private static final String DATETIME_FORMAT="yyyy-MM-dd HH:mm:ss";

    private KnowledgeContainer kContainer = null;
    private transient Set<Class<?>> extraClasses = new HashSet<Class<?>>();

    public RuleEngineAdaptor(){
    }

    public KnowledgeContainer getKnowledgeContainer() {
        return this.kContainer;
    }

    public void setKnowledgeContainer(KnowledgeContainer kContainer) {
        this.kContainer = kContainer;
    }
    
    public KnowledgeContainer createKnowledgeContainer(final RuleSet ruleSet) {

        LocalDateTime start = LocalDateTime.now();
        KnowledgeContainer knowledgeContainer = new KnowledgeContainer();
        knowledgeContainer.setRuleSet(ruleSet);

        final KieServices kieServices = KieServices.Factory.get();

        // Use the KJAR source to determine where to load the KJAR from (Classpath or from Maven)
        if (ruleSet.getMavenCoordinates().getSource() == RuleSetSource.Classpath) {
            knowledgeContainer.setContainer(kieServices.newKieClasspathContainer());
        } else {
            final ReleaseId releaseId = kieServices.newReleaseId(ruleSet.getMavenCoordinates().getGroupId(),  ruleSet.getMavenCoordinates().getArtifactId(), ruleSet.getMavenCoordinates().getVersion());
            knowledgeContainer.setContainer(kieServices.newKieContainer(releaseId));
        }

        LocalDateTime end = LocalDateTime.now();
        ExecutionDuration ed = calculateExecutionDuration(start, end);
        return knowledgeContainer;
    }

    public Command<?> createExecutionCommands(final RuleSet ruleSet, Map<String, Object> facts) { 

        // Process the fact into the commands
        List<Command<?>> commands = new ArrayList<Command<?>>();
        Iterator<String> iFacts = facts.keySet().iterator();

        // Insert the transactional data, which basically means that we want to return this data
        while (iFacts.hasNext()) {
            String factName = (String) iFacts.next();
            commands.add(CommandFactory.newInsert(facts.get(factName), factName, true, "DEFAULT"));
        }

        // Report on Facts Inserted
        int numTotalFacts = facts.keySet().size();
        LOGGER.debug("Inserting " + numTotalFacts + " facts into working memory...");  

        // Determine how we start the engine, depending upon the existence of a ruleflow or ruleflow-group
        if (ruleSet.getRuleFlowID() != null) {

            LOGGER.debug("Executing ruleflow: " + ruleSet.getRuleFlowID());
            commands.add(CommandFactory.newStartProcess(ruleSet.getRuleFlowID()));
        } else {

            // Execute a single ruleflow-group here...
            LOGGER.debug("Executing ruleflow-group: " + ruleSet.getRuleFlowGroup());
            commands.add(new ActivateRuleFlowCommand(ruleSet.getRuleFlowGroup()));
            commands.add(CommandFactory.newFireAllRules());
        }

        return CommandFactory.newBatchExecution(commands, ruleSet.getKnowledgeSessionID());
    }
    
    public Trace createTrace(TracingLevel tracingLevel, RuleTraceEventListener listener, LocalDateTime startedOn, LocalDateTime completedOn) {

        Trace trace = new Trace();
        trace.setTracingLevel(tracingLevel);
        trace.setStartedOn(formatLocalDateTime(startedOn));
        trace.setCompletedOn(formatLocalDateTime(completedOn));

        if (tracingLevel != TracingLevel.None) {
            if (listener != null) {

                // Execution Duration
                trace.setExecutionDuration(calculateExecutionDuration(startedOn, completedOn));

                // Rules Fired
                Iterator<String> iFiredRules = listener.getRulesFired().iterator();
                while (iFiredRules.hasNext()) {
                    String firedRule = (String) iFiredRules.next();

                    if (firedRule != null) {
                        trace.getRulesFired().add(firedRule);

                        // Add to the list of all rules (no duplicates)
                        if (!trace.getAllRules().contains(firedRule)) {
                            trace.getAllRules().add(firedRule);
                        }
                    }
                }

                // Rules Not Fired
                for (Map.Entry<String, ArrayList<String>> entry : listener.getRulesNotFired().entrySet()) {
                    if (entry.getValue().size() > 0) {
                        for (String ruleName : entry.getValue()) {
                            trace.getRulesNotFired().add(ruleName);

                            if (!trace.getAllRules().contains(ruleName)) {
                                trace.getAllRules().add(ruleName);
                            }
                        }
                    }
                }    
            }
        }

        return trace;
    }

    public RuleSetResult executeStateless(Map<String, Object> facts, final TracingLevel tracingLevel) throws Exception {

        RuleSetResult result = new RuleSetResult();
        RuleTraceEventListener traceListener = null;

        try {
            // Setup the commands for the engine
            final Command<?> batchCommand = createExecutionCommands(getKnowledgeContainer().getRuleSet(), facts);

            // Create the stateless session 
            final StatelessKieSession session = getKnowledgeContainer().getContainer().newStatelessKieSession(getKnowledgeContainer().getRuleSet().getKnowledgeSessionID());

            // Add the trace listener?
            if (tracingLevel != TracingLevel.None) {
                traceListener = new RuleTraceEventListener();
                session.addEventListener(traceListener);
            }

            // Execute the ruleset
            LocalDateTime startedOn = LocalDateTime.now();
            final ExecutionResults results = (ExecutionResults) session.execute(batchCommand);
            LocalDateTime completedOn = LocalDateTime.now();
            ExecutionDuration ed = calculateExecutionDuration(startedOn, completedOn);

            // Build the trace?
            if (tracingLevel != TracingLevel.None) {
                result.setTrace(createTrace(tracingLevel, traceListener, startedOn, completedOn));
                LOGGER.debug("Rule Execution Trace: " + result.getTrace().toString());
            } else {
                LOGGER.debug("Time to execute ruleset: " + ed.toString());
            }

            // Add the list of fact to the result
            result.setFacts(facts);

        } catch (Exception e) {
            LOGGER.error("Exception executing stateless rules: msg=" + e.getMessage());
            throw e;
        }

        return result;
    }

    public RuleSetResult executeCommands(final String payload, final String contentType, final TracingLevel tracingLevel) {

        RuleSetResult result = new RuleSetResult();
        RuleTraceEventListener traceListener = null;

        try {

            // Use the kie-server's internal API to translate the command structure payload
            Marshaller marshaller = MarshallerFactory.getMarshaller(this.extraClasses, MarshallingFormat.fromType(contentType), this.getClass().getClassLoader());

            // Unmarshal the command payload into a set of batch commands
            Class<? extends Command> type = BatchExecutionCommandImpl.class;
            Command<?> batchCommand = marshaller.unmarshall(payload, type);

            // Make sure we got an actual batch execution command object back
            if (batchCommand == null || ((BatchExecutionCommandImpl) batchCommand).getCommands() == null || ((BatchExecutionCommandImpl) batchCommand).getCommands().isEmpty()) {
                LOGGER.debug("Bad request, no commands to be executed - either wrong format or no data...");
                return null;
            }

            // Adjust the ruleSessionID to the value contained in the payload
            String ruleSessionID = ((BatchExecutionCommandImpl) batchCommand).getLookup();
            LOGGER.debug(String.format("Resetting the rule session ID to the value in the payload: %s", ruleSessionID));
            getKnowledgeContainer().getRuleSet().setKnowledgeSessionID(ruleSessionID);

            // Process the various command types
            int factsInserted = 0;
            for (Command cmd : ((BatchExecutionCommandImpl) batchCommand).getCommands()) {

                // Count the number of objects being inserted into working memory
                if (cmd instanceof InsertObjectCommand) {
                    factsInserted++;
                }

                // Adjust the ruleFlowID to the value contained in the payload
                if (cmd instanceof StartProcessCommand) {

                    String ruleFlowID = ((StartProcessCommand) cmd).getProcessId();
                    LOGGER.debug(String.format("Resetting the rule flow ID to the value in the payload: %s", ruleFlowID));
                    getKnowledgeContainer().getRuleSet().setRuleFlowID(ruleFlowID);
                }
            }

            LOGGER.debug(String.format("Inserting %s facts into working memory...", factsInserted));  

            // Create the stateless session 
            final StatelessKieSession session = getKnowledgeContainer().getContainer().newStatelessKieSession(getKnowledgeContainer().getRuleSet().getKnowledgeSessionID());

            // Add the trace listener?
            if (tracingLevel != TracingLevel.None) {
                traceListener = new RuleTraceEventListener();
                session.addEventListener(traceListener);
            }

            // Execute the ruleset
            LocalDateTime startedOn = LocalDateTime.now();
            final ExecutionResults results = (ExecutionResults) session.execute(batchCommand);
            LocalDateTime completedOn = LocalDateTime.now();
            ExecutionDuration ed = calculateExecutionDuration(startedOn, completedOn);

            // Build the trace?
            if (tracingLevel != TracingLevel.None) {
                result.setTrace(createTrace(tracingLevel, traceListener, startedOn, completedOn));
                LOGGER.debug("Rule Execution Trace: " + result.getTrace().toString());
            } else {
                result.setTrace(null);
                LOGGER.debug("Time to execute ruleset: " + ed.toString());
            }

            // Add the results
            ExecutionResultImpl resImpl = (ExecutionResultImpl) results;
            for (String key : resImpl.getResults().keySet()) {
                result.getFacts().put(key, resImpl.getResults().get(key));
            }

        } catch (Exception e) {

            LOGGER.error("Exception transforming command payload: msg=" + e.getMessage());
            return null;
        }

        return result;
    }

    public ExecutionDuration calculateExecutionDuration(LocalDateTime begin, LocalDateTime end) {

        ExecutionDuration ed = new ExecutionDuration();
        ed.setDays(ChronoUnit.DAYS.between(begin, end));
        ed.setHours(ChronoUnit.HOURS.between(begin, end));
        ed.setMinutes(ChronoUnit.MINUTES.between(begin, end));
        ed.setSeconds(ChronoUnit.SECONDS.between(begin, end));
        ed.setMilliseconds(ChronoUnit.MILLIS.between(begin, end));
        return ed;
   }

   public String formatLocalDateTime(LocalDateTime ldt) {
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
       return ldt.format(formatter);
   }
}
