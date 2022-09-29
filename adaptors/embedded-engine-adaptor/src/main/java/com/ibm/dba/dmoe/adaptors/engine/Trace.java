package com.ibm.dba.dmoe.adaptors.engine;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;

public class Trace {

    private String startedOn;
    private String completedOn;
   	private List<String> allRules = new ArrayList<String>();
    private List<String> rulesFired = new ArrayList<String>();
    private List<String> rulesNotFired = new ArrayList<String>();
    private ExecutionDuration executionDuration;
    private TracingLevel tracingLevel = TracingLevel.None;

    public Trace() {
    }

    public TracingLevel getTracingLevel() {
        return this.tracingLevel;
    }

    public void setTracingLevel(TracingLevel tracingLevel) {
        this.tracingLevel = tracingLevel;
    }

    public String getStartedOn() {
        return this.startedOn;
    }

    public void setStartedOn(String startedOn) {
        this.startedOn = startedOn;
    }

    public String getCompletedOn() {
        return this.completedOn;
    }

    public void setCompletedOn(String completedOn) {
        this.completedOn = completedOn;
    }

    public List<String> getRulesFired() {
        return this.rulesFired;
    }

    public void setRulesFired(List<String> rulesFired) {
        this.rulesFired = rulesFired;
    }

    public List<String> getRulesNotFired() {
        return this.rulesNotFired;
    }

    public void setRulesNotFired(List<String> rulesNotFired) {
        this.rulesNotFired = rulesNotFired;
    }

    public List<String> getAllRules() {
        return this.allRules;
    }

    public void setAllRules(List<String> allRules) {
        this.allRules = allRules;
    }

    public int getTotalRuleCount() {
        return this.allRules.size();
    }

    public int getRulesFiredCount() {
        return this.rulesFired.size();
    }

    public int getRulesNotFiredCount() {
        return this.rulesNotFired.size();
    }

    public ExecutionDuration getExecutionDuration() {
        return executionDuration;
    }

    public void setExecutionDuration(ExecutionDuration executionDuration) {
        this.executionDuration = executionDuration;
    }

    @Override
    public String toString() {
        return "startedOn=" + getStartedOn() + ", completedOn=" + getCompletedOn() + ", totalRuleCount=" + getTotalRuleCount() + ", totalRulesFired=" + getRulesFiredCount() + ", totalRulesNotFired=" + getRulesNotFiredCount() + ", executionDuration=" + getExecutionDuration();
    }
}