This repository contains contains a sample extension to the kie-server REST API which adds custom REST API endpoints to the existing kie-server's REST API.

Custom Start Process Endpoint
=
Normally the **_startProcess_** API only returns the process instance Id back to the caller, as most processes contain nodes that allow them to go into a `wait state`.  Examples of this would be a process that waits for a human task to be completed by an external user, or simply waiting on a message being dropped into a message queue.  A `ruleflow` is technicalyl a process, however a ruleflow is limited as to what type of nodes are allowed, which typically would not be any type of node that can go into a wait state.  The normal method of obtaining the variables modified by the process is to perform a query after the process has ended, and that query is typically a separate REST API call.

In order to support using processes as ruleflows, a new version of `startProcess` has been added to this custom KIE Server Extention.  The URI is essentially the same as the out of the box API, with the exception of adding the **redhat/consulting/ba** prefix beore the *_server_* portion of the URI, this making this a **Red Hat Consulting Business Automation** version of the API.

*The URL for executing the new **_startProcess+** API is as follows:*

```
http://${server}:${port}/kie-server/services/rest/redhat/consulting/ba/server/containers/{containerAlias)/processes/{ruleflowID}/instances
```

*The payload for this method needs to be structured in the following way*:

```
1 {    
2	"application": {
3        "com.redhat.consulting.ba.samples.model.Application": {
4            "id": "12345
5		     ...
6         }
7   }
8 }
```

* On line #2, you can see that each process variable must start with a name.  This name must be matched at the ruleflow level, both by name and data type.
* On line #3, you can see that each process variable must specify it's package name, if it is a Java class
* On line #4, you can see that each attribute of the variable can now be listed within the brackets.

*The new API returns not only the process instance ID _(PID)_, but also all process variables and their current state*:

```
{
    "PID": 2,
    "Variables": [
        {
            "name": "person",
            "old-value": "Person:  age=50, hat color=null, car is=Cadillac",
            "value": "{car=Cadillac, hatColor=Green, age=50}",
            "process-instance-id": 2,
            "modification-date": {
                "java.util.Date": 1613073400186
            }
        }
    ]
}
```

Installation and Configuration
=
The extension to the kie-server's REST API is packaged as a single JAR file and deployed to `kie-server.war` application.  In order to build the extension, simply perform a Maven build, using `mvn clean install`, and then copy the resulting JAR file to the `kie-server.war` file, typically located at `$JBOSS_HOME/standalone/deployments/kie-server.war/WEB-INF/lib`.  In order to install the extension simply copy the extension JAR file to this location and restart the server _(required)_.  JBoss must be restarted anytime that this jar file is updated.

If you are building a custom KIE Server image please be sure to add the jar file to the Dockerfile!


## Additional Information (*Appendicies*)
This repository is focused on business automation using **Red Hat’s Business Automation** products, which in turn rely on various open source tools and technology. The following online documentation is available in order to learn various aspects of these tools:

- [**Apache Maven**](https://maven.apache.org/) is a free and open source software project management and comprehension tool. Based on
  the concept of a project object model (POM), Maven can manage a project’s build, reporting and documentation from a central piece of
  information. A **getting started guide** is available [here](http://maven.apache.org/guides/getting-started/).
- [**Git**](https://git-scm.com//) is a free and open source distributed version control system designed to handle everything
  from small to very large projects with speed and efficiency. There is a **handbook** available [here](https://guides.github.com/introduction/git-handbook/), as well as various **guides** for learning and working with Git available [here](https://guides.github.com/
