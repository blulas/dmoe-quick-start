package com.ibm.dba.dmoe.adaptors.engine;

import org.drools.core.command.impl.ExecutableCommand;
import org.kie.internal.command.RegistryContext;
import org.drools.core.common.InternalAgenda;

import org.kie.api.runtime.Context;
import org.kie.api.runtime.KieSession;

@SuppressWarnings("serial")	
public class ActivateRuleFlowCommand implements ExecutableCommand<Object> {
		
	private String ruleFlowGroupName;
	
	public ActivateRuleFlowCommand(String ruleFlowGroupName){
		this.ruleFlowGroupName = ruleFlowGroupName;
	}

	public Void execute(Context context) {

		KieSession ksession = ((RegistryContext) context).lookup( KieSession.class );
		((InternalAgenda) ksession.getAgenda()).activateRuleFlowGroup(ruleFlowGroupName);
		return null;
	}
}