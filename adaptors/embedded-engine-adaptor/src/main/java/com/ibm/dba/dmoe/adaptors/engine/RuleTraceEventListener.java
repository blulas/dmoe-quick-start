package com.ibm.dba.dmoe.adaptors.engine;

import org.drools.core.reteoo.InitialFactImpl;
import org.kie.api.definition.KiePackage;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.kie.api.runtime.KieRuntime;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.time.LocalDateTime;

public class RuleTraceEventListener implements AgendaEventListener {

    private List<String> rulesFired = new ArrayList<String>();
	private Map<String, ArrayList<String>> rulesInPackage = new ConcurrentHashMap<String, ArrayList<String>>();

    public List<String> getRulesFired() {
        return this.rulesFired;
    }
    
    public void setRulesFired(List<String> rulesFired) {
        this.rulesFired = rulesFired;
    }

	public Map<String, ArrayList<String>> getRulesNotFired() {
		return this.rulesInPackage;
	}

    public void afterMatchFired(AfterMatchFiredEvent event) {

		// Remove the rule from the list of rules. so we can get the list of rules that were not fired.
		if (rulesInPackage.size() != 0) {
			rulesInPackage.get(event.getMatch().getRule().getPackageName()).remove(event.getMatch().getRule().getName());
		}

		// Add the name of the rule that fired
		rulesFired.add(event.getMatch().getRule().getName());
    }

    public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {

		KieRuntime kieRuntime = event.getKieRuntime();

		// Create HashMap for Packages and get all rules
		if (rulesInPackage.isEmpty()) {

            Collection<KiePackage> packages = event.getKieRuntime().getKieBase().getKiePackages();
			for (KiePackage pack: packages) {
				rulesInPackage.put(pack.getName(), new ArrayList<String>());
				
				for (org.kie.api.definition.rule.Rule rule : pack.getRules()) {
                    rulesInPackage.get(pack.getName()).add(rule.getName()); 
				}
			}
		}
    }

    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
	}

    public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
	}

    public void agendaGroupPushed(AgendaGroupPushedEvent event) {
	}

    public void matchCreated(MatchCreatedEvent event) {
	}

    public void matchCancelled(MatchCancelledEvent event) {
	}

    public void beforeMatchFired(BeforeMatchFiredEvent event) {
	}

    public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
	}

    public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
	}
}