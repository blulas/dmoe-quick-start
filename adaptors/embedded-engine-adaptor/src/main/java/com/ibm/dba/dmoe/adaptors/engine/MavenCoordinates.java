package com.ibm.dba.dmoe.adaptors.engine;

import lombok.Data;

@Data
public class MavenCoordinates {

    private static final String DEFAULT_VERSION = "1.0.0";

    private RuleSetSource source = RuleSetSource.Classpath;
    private String groupId;
    private String artifactId;
    private String version;
}