package com.ibm.dba.dmoe.adaptors.kie;

import java.util.List;
import java.util.ArrayList;

import org.kie.server.api.model.instance.VariableInstance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"PID", "Variables"})
public class StartProcessResponse {

    private Long processInstanceID;
    public List<ProcessVariable> variables = new ArrayList<ProcessVariable>();

	@JsonProperty("PID")
    public Long getProcessInstanceID() {
        return processInstanceID;
    }

    public void setProcessInstanceID(Long processInstanceID) {
        this.processInstanceID = processInstanceID;
    }

	@JsonProperty("Variables")
    public List<ProcessVariable> getVariables() {
        return variables;
    }

    public void setVariabless(List<ProcessVariable> variables) {
        this.variables = variables;
    }
}