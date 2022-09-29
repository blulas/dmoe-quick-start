package com.ibm.dba.dmoe.adaptors.engine;

import lombok.Data;

@Data
public class ExecutionDuration {

    private long days = 0;
    private long hours = 0;
    private long minutes = 0;
    private long seconds = 0;
    private long milliseconds = 0L;
}