# ${projectName} - ${projectDescription}

This repository contains the `${projectName} decision service`, packaged as a `Quarkus Microservice JAR)`, and was originally generated by the custom `Decision Service Maven Archetype` in order to ensure compliancee with Maven best practices. 

*This section should describe this project, what it does, how it does it, it’s dependencies, etc…*

## How To Build ${projectName}

This repository is built using `mvn clean install (deploy)` by either the CI/CD pipeline or on a local developer workstation.  Maven `install` places the packaged JAR file into the local Maven `~/.M2/repository`.  Maven `deploy` places the packaged JAR file into the enterprise Maven repository *(ie: Artifactory)*.  

**Note**: To run a unit test, provided all depencencies are available to the decision service via Maven, simply perform: `mvn clean install -DskipTests=false`.  If you wish to have unit test execution on by default, change the property in the decision service's pom.xml to default the `skipTests` property to `false` _(true is the default)_.

## How To Deploy ${projectName}

This repository is simply packaged as a JAR file and stored in the application's enterprise Maven repository *(ie: Artifactory)*.

## How To Configure Logging for ${projectName}

This project uses the logging framework [**Logback**](https://logback.qos.ch/) and [**SLF4J**](https://www.slf4j.org/) as the logging facade.  The logging configuraiton can be updated by modifying the file located at `src/main/resources/logback.xml`.  The various configuration settings in this file cover not only this project, but also any depencency where the `SLF4J` facade is being used.

**_Note_**: Logback was selected as the logging implementation over LOG4J based on recent news regarding security vulnerabilities with `LOG4J`.

### Package Logging Configuration for ${projectName}
You can configure the logging level for varous packages by adding or updateing `logger` entries in the configuraiton file.  The following is an example of the logging configuration file:

```xml
  <configuration>
      <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
            </encoder>
      </appender>

      <!-- Drools (Red Hat) Packages - Logging Configuration -->
      <logger name="logging.level.org.drools.core.xml"                level="error" />  
      <logger name="org.drools.compiler.kie.builder.impl"             level="error" /> 
      <logger name="org.drools.modelcompiler"                         level="error" /> 
      <logger name="org.kie.server.api.marshalling.MarshallerFactory" level="error" /> 
      <logger name="org.kie.internal.pmml.PMMLImplementationsUtil"    level="error" /> 

      <!-- Global Logging Level -->
      <root level="error">
          <appender-ref ref="STDOUT" />
      </root>
    </configuration>
```

## Additional Information (*Appendicies*)
This repository is focused on business automation using the **IBM Decison Manager Open Edition** product, which in turn relies on various open source tools and technology. The following online documentation is available in order to learn various aspects of these tools:

- [**Apache Maven**](https://maven.apache.org/) is a free and open source software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project’s build, reporting and documentation from a central piece of  information. A **getting started guide** is available [here](http://maven.apache.org/guides/getting-started/).
- [**Git**](https://git-scm.com//) is a free and open source distributed version control system designed to handle everything from small to very large projects with speed and efficiency. There is a **handbook** available [here](https://guides.github.com/introduction/git-handbook/), as well as various **guides** for learning and working with Git available [here](https://guides.github.com/
- [**Quarkus**](https://quarkus.io) Quarkus was created to enable Java developers to create applications for a modern, cloud-native world. Quarkus is a Kubernetes-native Java framework tailored for GraalVM and HotSpot, crafted from best-of-breed Java libraries and standards. The goal is to make Java the leading platform in Kubernetes and serverless environments while offering developers a framework to address a wider range of distributed application architectures.
- [**Kogito**](https://kogito.kie.org) Kogito is designed from ground up to run at scale on cloud infrastructure. If you think about business automation think about the cloud as this is where your business logic lives these days. By taking advantage of the latest technologies (Quarkus, knative, etc.), you get amazingly fast boot times and instant scaling on orchestration platforms like Kubernetes.
- [**IBM Business Automation Manager Open Edition**](https://www.ibm.com/docs/en/ibamoe?topic=getting-started-business-automation-manager-open-editions) IBM Business Automation Manager Open Editions, which consists of IBM Process Automation Manager and IBM Decision Manager Open Edition, offer developers the ability to build cloud-native applications that automate business operations. IBM Process Automation Manager Open Edition is a platform for developing containerized microservices and applications that automate processes and business decisions. IBM Decision Manager Open Edition, a subset of Process Automation Manager Open Edition, is a separately available platform for developing containerized microservices and applications that automate business decisions, including complex event processing.
