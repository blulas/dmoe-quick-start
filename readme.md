# IBM Digital Business Automation - Decision Manager Open Edition - Quick Start

This repository contains various tools, scripts, and product extensions useful for managing `IBM Decision Manager Open Edition` projects.

## Overview

The purpose of this repository is to guide the developer through the process of creating a `decision service` using [**IBM Decision Manager Open Edition**](https://www.ibm.com/docs/en/ibamoe), and specifically using the [**Kogito**](https://kogito.kie.org) extensions, by using the public [**Maven**](https://maven.apache.org/) dependencies vs an on-prem installation.  

The `first step` is to make sure you local development environment or workstation is configured with the proper developer tooling, frameworks, and runtimes.  Once your development environment is configured, you can proceed to the remaining steps to create the project, add assets and unit tests, and deploy your solution.

## Tool & Environment Requirements

The following tools and frameworks are required to be installed on the developer workstation.  Please follow the link in order to download and install the various tools:

1. [**Maven**](https://maven.apache.org) - Required in order to perform Maven builds on the desktop.  
2. [**Git Client**](https://git-scm.com) - Required in order to clone and make changes to the project GIT repositories
3. [**JDK 11**](https://www.oracle.com/java/technologies/downloads/) - Requires Java 11 at this time
4. [**GraalVM (Optional)**](https://www.graalvm.org//) - Required if you want to test NATIVE compilation and deployment
5. [**VS Code IDE**](https://code.visualstudio.com/download) - An IDE is required, VS Code IDE is the default.  While you can use any IDE you wish, the embeddable editors are only available for VS Code.

*_Once you have installed VS Code, you can go to the Extensions Marketplace and install the `Kogito Bundle` for BPMN, DMN, and Test Scenarios_*.

## How To Build

Once you have configured your local development environment, you need to perform a `Maven Build` of the repository.  This repository is built using `mvn clean install` by either the CI/CD pipeline or on a local developer workstation.  If deploying artifacts to an enterprise Maven repository, please use `mvn clean deploy`, which requires configuration of the `distributionManagement` section of your project's parent pom.xml.

*_Once you have performed a build of the repository, you will have the following modules available to you in order to start creating and building decision services..._*.

## Repository Modules

This repository is organized in a series of modules:  

1.  [**Adaptors**](./adaptors/readme.md) - A set of legacy adaptors and product extensions used to augment the functionality of the legacy aspects of IBM Decision Manager Open Edition.  
2.  [**Maven**](./maven/readme.md) - A set of Maven extensions, such as custom archetypes and plugins, used to augment the functionality of IBM Decision Manager Open Edition.
3.  [**Scripts**](./scripts/readme.md) - A set of scripts useful for using various adaptors and maven extensions.
4.  [**Samples**](./samples/readme.md) - A set of sample applications generated using this kick-start repository.

## Developer Workflow

Once the tools, frameworks, and repository modules have been installed, configured, and built, you can then utilize the various components of this repository in order to manage your project.  The following represents the basic workflow that a developer would use in order to create a decision service:

1.  Select a `Maven Archetype` from the list of available archetypes in the [**Maven**](./maven/readme.md) section of this repository.  Follow the instructions on how to invoke the archetype from the developer command line, which will create the skeleton for the decision service project.

2.  Once you have created a project, you can add assets to that project.  Available asset types are:

* Decision Model (.DMN)
* Business Rules (.DRL)
* Business Rule Flow (.BPMN)
* Unit Tests (.SCESIM)

Assets added to the project are essentially just files with a specific file extension.  Assets are located in the `src/main/resources/*` folder of the project, as seen in the following:

![Decision Service Project's Resource Folder](./images/ds-project.png "Decision Service Project's Resource Folder")

3.  Once you have added your assets to the `src\main\resources\` folder, you can then perform a build of your decision service by simply performing a `mvn clean install` at the root directory of your project.  

4.  At this point you can either run the decision service locally or deploy it to a runtime environment, such as `OpenShift` or any other type of `K8` cluster.

## Additional Information (*Appendicies*)
This repository is focused on business automation using the **IBM Decison Manager Open Edition** product, which in turn relies on various open source tools and technology. The following online documentation is available in order to learn various aspects of these tools:

- [**Apache Maven**](https://maven.apache.org/) is a free and open source software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project’s build, reporting and documentation from a central piece of  information. A **getting started guide** is available [here](http://maven.apache.org/guides/getting-started/).
- [**Git**](https://git-scm.com//) is a free and open source distributed version control system designed to handle everything from small to very large projects with speed and efficiency. There is a **handbook** available [here](https://guides.github.com/introduction/git-handbook/), as well as various **guides** for learning and working with Git available [here](https://guides.github.com/
- [**Quarkus**](https://quarkus.io) Quarkus was created to enable Java developers to create applications for a modern, cloud-native world. Quarkus is a Kubernetes-native Java framework tailored for GraalVM and HotSpot, crafted from best-of-breed Java libraries and standards. The goal is to make Java the leading platform in Kubernetes and serverless environments while offering developers a framework to address a wider range of distributed application architectures.
- [**Kogito**](https://kogito.kie.org) Kogito is designed from ground up to run at scale on cloud infrastructure. If you think about business automation think about the cloud as this is where your business logic lives these days. By taking advantage of the latest technologies (Quarkus, knative, etc.), you get amazingly fast boot times and instant scaling on orchestration platforms like Kubernetes.
- [**IBM Business Automation Manager Open Edition**](https://www.ibm.com/docs/en/ibamoe) `IBM Business Automation Manager Open Editions`, which consists of `IBM Process Automation Manager Open Edition` and `IBM Decision Manager Open Edition`, offer developers the ability to build cloud-native applications that automate business operations. `IBM Process Automation Manager Open Edition` is a platform for developing containerized microservices and applications that automate processes and business decisions. `IBM Decision Manager Open Edition` is a separately available platform for developing containerized microservices and applications that automate business decisions, business rules, and complex event processing.
