#!/bin/bash

if [ "$1" == "" ]; then
    echo "Please specify a decision service project name..."
    exit 1
fi

# Remove the project directory if it exists
if [ -d "$1" ]; then rm -rf $1; fi

# Run the archetype and generate the project structure
mvn archetype:generate -B "-DarchetypeGroupId=com.ibm.dba.dmoe.maven" "-DarchetypeArtifactId=ds-maven-archetype" "-DarchetypeVersion=1.0.0-SNAPSHOT" \
  "-DprojectName=$1" \
  "-DartifactId=$1" 