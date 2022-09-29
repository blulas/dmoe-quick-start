package com.ibm.dba.dmoe.adaptors.engine;

import lombok.Getter;
import lombok.Setter; 

import org.kie.api.runtime.KieContainer;

@Getter
@Setter
public class KnowledgeContainer {                                    

    private RuleSet ruleSet;
    private KieContainer container;
}