package com.ibm.dba.dmoe.adaptors.engine;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class RuleSetResult {

    private Map<String, Object> facts = new ConcurrentHashMap<String, Object>();
    private Trace trace = new Trace();
}