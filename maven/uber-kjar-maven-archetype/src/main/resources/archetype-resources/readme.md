Project Overview
================

*This section should describe this project, what it does, how it does it, it’s dependencies, etc…*

Getting Started
===============

As this project was generated using the `uber-kjar-maven-archetype`, the project `pom.xml` should already contain the base dependencies that this project requires. There are three types of dependencies added to this
`pom.xml`:

1. Red Hat Dependencies for Decision, Process, and Case Management.
2. Application Dependencies, such as dependencies created by other application groups
3. Third Party Dependencies, such as JSON libraries

As the `ba-platform-parent` `pom.xml`contains all of the dependency version and repository information, it must be the first artifact built, as the other projects which are based on this new parent require that the Maven cache contains all of the referenced dependencies. Be sure to install the`ba-platform-parent`repository into your`local/enterprise` Maven cache prior to building this project.

Adding Additional Dependencies
------------------------------

The initial version of this project’s `pom.xml` should contain a list of required product, application, and third-party dependencies. As the capabilities of your project grow you may be required to add additional
dependencies to your project’s `pom.xml`. This is accomplished by adding additional dependencies in the **&lt;dependencyManagement&gt;** section of the project’s `pom.xml` as well as listing the actual dependencies in
the **&lt;dependencies&gt;** section of the project’s `pom.xml`.

For example, if you have a rule project where additional rule requirements have been added, which result in needing additional fact model (classes) dependencies, you would specify the `bill-of-materials`
to import and the specific list of dependencies *(minus the version, which comes from the .bom)*. In the following example, the `ciao-commons-bom` is imported, thus giving us access to the various shared dependencies of the shared-platforms domain:

<properties>
                <ciao-commons-bom.version>1.0.0</ciao-commons-bom.version>
        </properties>


<dependencyManagement>
            <dependencies>
                    <!-- CIAO Commons -->
                    <dependency>
                            <groupId>com.bp.ciao</groupId>
                            <artifactId>ciao-commons-bom</artifactId>
                            <version>${ciao-commons-bom.version}</version>
                            <type>pom</type>
                            <scope>import</scope>
                    </dependency>
            </dependencies>
    </dependencyManagement>

<dependencies>
        <!-- CIAO Platforms Commons -->
        <dependency>
                <groupId>com.bp.ciao</groupId>
                <artifactId>ciao-commons-fact-model</artifactId>
        </dependency>

    <!-- RHPAM -->
    <dependency>
            <groupId>org.kie</groupId>
            <artifactId>kie-api</artifactId>
            <scope>provided</scope>
    </dependency>

    <dependency>
            <groupId>org.jbpm</groupId>
            <artifactId>jbpm-bpmn2</artifactId>
            <scope>provided</scope>
    </dependency>
<dependency>
                <groupId>org.drools</groupId>
                <artifactId>drools-core</artifactId>
                <scope>provided</scope>
          </dependency>

      <dependency>
            <groupId>org.drools</groupId>
            <artifactId>drools-compiler</artifactId>
            <scope>provided</scope>
      </dependency>
</dependencies>

**Please Note**: *Adding either product or application level dependencies to this project should always be accomplished via the Maven bill-of-materials method, as seen in the **dependencyManagement**
section listed above. Additionally, Red Hat dependencies should always be notated with the `provided` scope and version numbers are never to be included, as those come from the bill-of-materials.*

Additional Information (*Appendicies*)
======================================

This section includes references to any product or technology that is
relevant to this repository, including a full set of product
documentation.

Red Hat Product Documentation
=============================

This repository is focused on business automation using Red Hat’s
Business Automation products. The following online documentation is
available in order to learn various aspects of the products:

- **Red Hat Middleware Process Automation** - For applications that
  require full process (BPM) as well as decision services (BRMS), Red
  Hat Process Automation Manager is a the latest version of the full
  BPM/BRMS product. This product was formally known as JBoss BPM Suite
  6.4, which contained both the BRMS as well as the BPM engine and
  tooling. For applications that only require decision services (BRMS)
  a scaled down version of the product is available, known as Red Hat
  Decision Manager.

  - [**Red Hat Process Automation Manager 7
    Documentation**](https://access.redhat.com/documentation/en-us/red_hat_process_automation_manager/7.9.0.redhat-00002/)
    is the enterprise (supported) version of the full BPM/BRMS
    product and thus the first place to look for information.
    Additional sources of information or product documentation can
    be found in the community versions of the product documentation:

    - [jBPM](https://docs.jboss.org/jbpm/release/7.44.0.Final-redhat-00003/jbpm-docs/html_single/)
      is the community version of the process (BPM) side of the
      Red Hat Process Automation Manager product and thus the
      source of the codebase as well as examples and
      documentation. If you are looking for places to learn BPM
      and other BPM-related stuff, this is a great place to start.
    - [Drools](https://docs.jboss.org/drools/release/7.44.0.Final-redhat-00003/drools-docs/html_single/index.html)
      is the community version of the decision services (BRMS)
      side of the Red Hat Process Automation Manager (RHPAM)
      product and thus the source of the codebase as well as
      examples and documentation. If you are looking for places to
      learn DRL and other BRMS-related stuff, this is a great
      place to start.
  - [**Red Hat Decision Manager 7
    Documentation**](https://access.redhat.com/documentation/en-us/red_hat_decision_manager/7.9.0.redhat-00002/)
    is the enterprise (supported) version of the decision management
    (rules) portion of the Red Hat Process Automation Manager
    product. This is for applications or projects that only require
    decision services (rules) with no long running process support.

    - [Drools](https://docs.jboss.org/drools/release/7.44.0.Final-redhat-00003/drools-docs/html_single/index.html)
      is the community version of the Red Hat Decision Manager
      product and thus the source of the codebase as well as
      examples and documentation. If you are looking for places to
      learn DRL and other BRMS-related stuff, this is a great
      place to start.
- **Red Hat OpenShift Container Platform (RHOCP)** - Containers are
  standalone processes that run within their own environment,
  independent of the operating system and the underlying
  infrastructure. OpenShift helps you to develop, deploy, and manage
  container-based applications. It provides you with a self-service
  platform to create, modify, and deploy applications on demand, thus
  enabling faster development and release life cycles.

  - [**Red Hat OpenShift Container Platform
    Documentation**](https://access.redhat.com/documentation/en-us/openshift_container_platform/4.5/)
    for applications that will be based on a containerized
    architecture.

Other Product Documentation
===========================

This repository is focused on business automation using Red Hat’s
Business Automation products, which in turn rely on various open source
tools and technology. The following online documentation is available in
order to learn various aspects of these tools:

- [**Apache Maven**](https://maven.apache.org/) is a free and open
  source software project management and comprehension tool. Based on
  the concept of a project object model (POM), Maven can manage a
  project’s build, reporting and documentation from a central piece of
  information. A **getting started guide** is available
  [here](http://maven.apache.org/guides/getting-started/).
- [**Git**](https://git-scm.com//) is a free and open source
  distributed version control system designed to handle everything
  from small to very large projects with speed and efficiency. There
  is a **handbook** available
  [here](https://guides.github.com/introduction/git-handbook/), as
  well as various **guides** for learning and working with Git
  available [here](https://guides.github.com/).
