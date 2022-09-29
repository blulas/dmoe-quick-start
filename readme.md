# IBM Business Automation - Decision Manager Open Edition - Quick Start

This repository contains various tools, scripts, and product extensions useful for managing `IBM Decision Manager Open Edition` projects.

## How To Build

This repository is built using `mvn clean install` by either the CI/CD pipeline or on a local developer workstation.  If deploying artifacts to an enterprise Maven repository, please use `mvn clean deploy`, which requires configuration of the `distributionManagement` section of your project's parent pom.xml.

## Repository Modules

This repository is organized in a series of modules:  

1.  [**Adaptors**](./adaptors/readme.md) - A set of legacy adaptors and product extensions used to augment the functionality of the legacy aspects of IBM Decision Manager Open Edition.  
2.  [**Maven**](./maven.readme.md) - A set of Maven extensions, such as custom archetypes and plugins, used to augment the functionality of IBM Decision Manager Open Edition.
3.  [**Scripts**](./scripts/readme.md) - A set of scripts useful for using various adaptors and maven extensions.

## Additional Information (*Appendicies*)
This repository is focused on business automation using **IBM Decison Manager Open Edition** products, which in turn rely on various open source tools and technology. The following online documentation is available in order to learn various aspects of these tools:

- [**Apache Maven**](https://maven.apache.org/) is a free and open source software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project’s build, reporting and documentation from a central piece of  information. A **getting started guide** is available [here](http://maven.apache.org/guides/getting-started/).
- [**Git**](https://git-scm.com//) is a free and open source distributed version control system designed to handle everything from small to very large projects with speed and efficiency. There is a **handbook** available [here](https://guides.github.com/introduction/git-handbook/), as well as various **guides** for learning and working with Git available [here](https://guides.github.com/
- [**Quarkus**](https://quarkus.io) Quarkus was created to enable Java developers to create applications for a modern, cloud-native world. Quarkus is a Kubernetes-native Java framework tailored for GraalVM and HotSpot, crafted from best-of-breed Java libraries and standards. The goal is to make Java the leading platform in Kubernetes and serverless environments while offering developers a framework to address a wider range of distributed application architectures.
- [**Kogito**](https://kogito.kie.org) Kogito is designed from ground up to run at scale on cloud infrastructure. If you think about business automation think about the cloud as this is where your business logic lives these days. By taking advantage of the latest technologies (Quarkus, knative, etc.), you get amazingly fast boot times and instant scaling on orchestration platforms like Kubernetes.
- [**IBM Business Automation Manager Open Edition**](https://www.ibm.com/docs/en/ibamoe?topic=getting-started-business-automation-manager-open-editions) IBM Business Automation Manager Open Editions, which consists of IBM Process Automation Manager and IBM Decision Manager Open Edition, offer developers the ability to build cloud-native applications that automate business operations. IBM Process Automation Manager Open Edition is a platform for developing containerized microservices and applications that automate processes and business decisions. IBM Decision Manager Open Edition, a subset of Process Automation Manager Open Edition, is a separately available platform for developing containerized microservices and applications that automate business decisions, including complex event processing.
