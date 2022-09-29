# Decision Service Maven Archetype

This repository contains a custom [**Maven archetype**](https://maven.apache.org/guides/introduction/introduction-to-archetypes.html) for creating cloud native decision services based on [**Quarkus**](https://https://quarkus.io//).

## Overview

In short, Archetype is a Maven project template generation toolkit. An archetype is defined as an original pattern or model from which all other things of the same kind are made. The name fits as we are trying to provide a system that provides a consistent means of generating Maven projects, specifically, Kogitio Decision Service projects. The archetype will help authors create Maven project templates for users, and provides users with the means to generate parameterized versions of those project templates.

The `Decision Service Maven Archetype` produces a standard Kogito decision service project structure, complete with a properly configured project `pom.xml` as well as including all of the core dependencies and build targets for a project using these types of artifacts.  An assumption is made that there is already a proper parent pom.xml to inherit from.

## Getting Started

In order to create a decision service project using the archetype, the archetype and plugin must both be installed/deployed to a Maven cache accessible to each developer. Once this is completed, the archetype can be used by a developer using the VS Code IDE.

## What Gets Generated?

Upon successful execution of the `ds-maven-archetype`, a directory will be created for your project containg the Maven project struture for the decision service.  All standard Maven commands can now be used in order to build, deploy, and test the decision service.

**_Note_**: There may be certain scenarios where the Git ignore file (_.gitignore_) may not be generated for the new KJAR project.  This is due to a bug in Maven's archetype genearation code.  In the event of this, please add the `.gitignore` file manually to the decision service's root folder.

## Building the Decision Service Project

In order to build the project you must run the following command `mvn clean deploy`, which will build the `Decision Service` and store it in the enterprise artifact repository as well as the local .m2 cache. 

## Using the Archetype

This custom Maven archetype can be run from the developer workstation, build server, or any other environment where a command line tool can be used, assuming that environment has access to the enterprise Maven repository for which the archetype has been installed or deployed. Once installed into Maven, the archetype can be run either via the command line or directly in Business Central.

### Using the Archetype from the Command Line

Once the archetype has been built and installed into the enterprise artifact repository, navigate to the project folder for which you would like to create the new project, and run the following command (replacing the properties with the appropriate project value):

`mvn archetype:generate -B -DarchetypeGroupId=com.highmark.ba -DarchetypeArtifactId=ds-maven-archetype -DarchetypeVersion=1.0.0-SNAPSHOT< -DprojectName=<project name> -DartifactId=<project artifact id>`

### Archetype Parameters

The command above represents the minimal set of properties for the archetype. The `-B command line option` essentially tells the archetype to run in batch mode, rather than interactive mode, which prompts for each archetype property. Each archetype property has a specific default value, set in the archetype descriptor, in the event the property is not provided on the command line. The remainder of the parameters are listed in the following table. All parameters can be added to the call to the archetype by specifying `-DparameterName=parameterValue` on the command line. Please note that most parameters have default values and are not required.

**Decision Service Maven Archetype - Required and Optional Parameters.**
| Parameter Name | Type   | Description | Default Value | Required ? |
| :---           | :----: | :---        | :-----------: | :--------: |
| archetypeGroupId | String | Group ID for the archetype | None | Yes |
| archetypeArtifactId | String | Artifact ID for the archetype | None | Yes |
| archetypeVersion | String | Version number for the archetype | None | Yes |
| projectName | String | Project name (becomes decision service name) | untitled | No |
| projectDescription | String | Project description | Decision Service Project | No |
| groupId | String | Maven groupId for generated project | com.highmark.ba | No |
| artifactId | String | Maven artifactId for generated project | untitled | No |
| version | String | Maven version for generated project | 1.0.0-SNAPSHOT | No |
| parentGroupId | String | Maven parent groupId for generated project | com.highmark.ba | No |
| parentArtifactId | String | Maven parent artifactId for generated project | ba-platform-parent | No |
| parentVersion | String | Maven parent version for generated project | 1.0.0-SNAPSHOT | No |
| packaging | String | Maven build package target | jar | No |
| quarkusVersion | String | Version of Quarkus being used by Kogito | 2.2.3.Final-redhat-00013 | No |
| kogitoVersion | String | Version of Kogito | 1.17.0.Final | No |

**Note:** *The parameters of this archetype are meant to be extended as the organization adds more sharable dependencies, so please be sure to update these properties, the archetype, and this documentation!*

## Additional Information (*Appendicies*)
This repository is focused on business automation using **Red Hat’s Business Automation** products, which in turn rely on various open source tools and technology. The following online documentation is available in order to learn various aspects of these tools:

- [**Apache Maven**](https://maven.apache.org/) is a free and open source software project management and comprehension tool. Based on
  the concept of a project object model (POM), Maven can manage a project’s build, reporting and documentation from a central piece of
  information. A **getting started guide** is available [here](http://maven.apache.org/guides/getting-started/).
- [**Git**](https://git-scm.com//) is a free and open source distributed version control system designed to handle everything
  from small to very large projects with speed and efficiency. There is a **handbook** available [here](https://guides.github.com/introduction/git-handbook/), as well as various **guides** for learning and working with Git available [here](https://guides.github.com/
- [**Quarkus**](https://https://quarkus.io/) was created to enable Java developers to create applications for a modern, cloud-native world. Quarkus is a Kubernetes-native Java framework tailored for GraalVM and HotSpot, crafted from best-of-breed Java libraries and standards. The goal is to make Java the leading platform in Kubernetes and serverless environments while offering developers a framework to address a wider range of distributed application architectures.
