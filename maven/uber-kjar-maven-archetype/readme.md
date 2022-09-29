# UBER KJAR Maven Archetype

This repository contains a custom [**Maven archetype**](https://maven.apache.org/guides/introduction/introduction-to-archetypes.html) for creating Knowledge (KJAR) project either in Business Central or on a developer’s local machine, using the VS Code IDE.

## Overview

In short, Archetype is a Maven project template generation toolkit. An archetype is defined as an original pattern or model from which all other things of the same kind are made. The name fits as we are trying to provide a system that provides a consistent means of generating Maven projects, specifically, KJAR Maven projects. The archetype will help authors create Maven project templates for users, and provides users with the means to generate parameterized versions of those project templates.

The KJAR Maven Archetype produces a standard KJAR project structure, complete with a properly configured project `pom.xml` as well as including all of the core dependencies and build targets for a project using these types of artifacts.

## Getting Started

In order to create a KJAR project using the archetype, the archetype and plugin must both be installed/deployed to a Maven cache accessible to each developer. Once this is completed, the archetype can be used by a developer using the VS Code IDE or a business user in Business Central.

## What Gets Generated?

Upon successful execution of the `uber-kjar-maven-archetype`, a directory will be created for your project containg the Maven project struture for the KJAR.  All standard Maven commands can now be used in order to build, deploy, and test the KJAR.

**_Note_**: There may be certain scenarios where the Git ignore file (_.gitignore_) may not be generated for the new KJAR project.  This is due to a bug in Maven's archetype genearation code.  In the event of this, please add the `.gitignore` file manually to the KJAR's root folder.

## Building the KJAR Project

In order to build the project, either locally or via Business Central, you must run the following command `mvn clean deploy`, which will build the `KJAR` and store it in the enterprise artifact repository as well as the local .m2 cache. Using the `Build and Install` button option in Business Central essentially performs a `mvn clean deploy` command by default. This ensures that the `KJAR` is now available to the KIE
Server’s REST API for container deployment, by using the Maven coordinates of the project.

## Using the Archetype

This custom Maven archetype can be run from the developer workstation, build server, or any other environment where a command line tool can be used, assuming that environment has access to the enterprise Maven repository for which the archetype has been installed or deployed. Once installed into Maven, the archetype can be run either via the command line or directly in Business Central.

### Using the Archetype from the Command Line

Once the archetype has been built and installed into the enterprise artifact repository, navigate to the project folder for which you would like to create the new project, and run the following command (replacing the properties with the appropriate project value):

`mvn archetype:generate -B -DarchetypeGroupId=com.syngenta.ba -DarchetypeArtifactId=uber-kjar-maven-archetype -DarchetypeVersion=1.0.0-SNAPSHOT< -DprojectName=<project name> -DartifactId=<project artifact id>`

Using the Archetype from Business Central
-----------------------------------------

Please see the published Red Hat documentation on how to configure a custom archetype in Business Central which is located at: [**Business Central Archetype Management**](https://access.redhat.com/documentation/en-us/red_hat_process_automation_manager/7.7/html/configuring_business_central_settings_and_properties/managing-business-central-archetype-con)

### Archetype Parameters

The command above represents the minimal set of properties for the archetype. The `-B command line option` essentially tells the archetype to run in batch mode, rather than interactive mode, which prompts for each archetype property. Each archetype property has a specific default value, set in the archetype descriptor, in the event the property is not provided on the command line. The remainder of the parameters are listed in the following table. All parameters can be added to the call to the archetype by specifying `-DparameterName=parameterValue` on the command line. Please note that most parameters have default values and are not required.

**KJAR Maven Archetype - Required and Optional Parameters.**
| Parameter Name | Type   | Description | Default Value | Required ? |
| :---           | :----: | :---        | :-----------: | :--------: |
| archetypeGroupId | String | Group ID for the archetype | None | Yes |
| archetypeArtifactId | String | Artifact ID for the archetype | None | Yes |
| archetypeVersion | String | Version number for the archetype | None | Yes |
| projectName | String | Project name (becomes KJAR name) | untitled | No |
| projectDescription | String | Project description | UHC KJAR Project | No |
| groupId | String | Maven groupId for generated project | com.highmark.ba | No |
| artifactId | String | Maven artifactId for generated project | untitled | No |
| version | String | Maven version for generated project | 1.0.0-SNAPSHOT | No |
| packaging | String | Maven build package target | KJAR | No |
| useDMN | Boolean | Include DMN dependencies | False | No |
| useCompiledRules | Boolean | Compile rules and rule model | False | No |
| kiePersistenceUnit | String | Sets persistence-unit in kie-deployment-descriptor.xml | org.jbpm.domain | No |
| kieAuditPersistenceUnit | String | Sets audit-persistence-unit in kie-deployment-descriptor.xml | org.jbpm.domain | No |
| kieAuditMode | String | Sets audit-mode in kie-deployment-descriptor.xml | JPA | No |
| kiePersistenceMode | String | Sets persistence-mode in kie-deployment-descriptor.xml | JPA | No |
| kieRuntimeStrategy | String | Sets runtime-strategy in kie-deployment-descriptor.xml | PER_PROCESS_INSTANCE | No |
| kieSecureREST | Boolean | Determines if REST API calls require participation in a particular group or role. | True | No |
| kieLimitSerializationClasses | Boolean | Sets limit-serialization-classes in kie-deployment-descriptor.xml | True | No |
| rhbaPlatformBomVersion | String | Version of RHPAM/DM | 7.12.0.redhat-00008 | No |
| kieMavenPluginVersion | String | Version of KIE plugins | 7.59.0.Final-redhat-00006 | No |
| mavenCompilerPluginVersion | String | Version of Maven compiler plugin | 3.8.1 | No |
| mavenResourcesPluginVersion | String | Version of Maven resources plugin | 3.2.0 | No |
| mavenShadePluginVersion | String | Version of Maven shade plugin | 3.2.4 | No |
| mavenSurefirePluginVersion | String | Version of Maven Surfire plugin | 3.0.0-M5 | No |
| slf4jVersion | String | Version of SLF4J | 1.7.32 | No |
| logbackVersion | String | Version of SLF4J | 1.2.10 | No |
| junitVersion | String | Version of JUnit | 4.13.2 | No |
| javaVersion | String | Version of Java JDK| 11 | No |

**Note:** *The parameters of this archetype are meant to be extended as the organization adds more sharable dependencies, so please be sure to update these properties, the archetype, and this documentation!*

## Additional Information (*Appendicies*)
This repository is focused on business automation using **Red Hat’s Business Automation** products, which in turn rely on various open source tools and technology. The following online documentation is available in order to learn various aspects of these tools:

- [**Apache Maven**](https://maven.apache.org/) is a free and open source software project management and comprehension tool. Based on
  the concept of a project object model (POM), Maven can manage a project’s build, reporting and documentation from a central piece of
  information. A **getting started guide** is available [here](http://maven.apache.org/guides/getting-started/).
- [**Git**](https://git-scm.com//) is a free and open source distributed version control system designed to handle everything
  from small to very large projects with speed and efficiency. There is a **handbook** available [here](https://guides.github.com/introduction/git-handbook/), as well as various **guides** for learning and working with Git available [here](https://guides.github.com/
