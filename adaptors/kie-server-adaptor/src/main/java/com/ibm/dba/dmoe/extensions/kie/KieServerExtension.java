package com.ibm.dba.dmoe.adaptors.kie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.DefinitionService;
import org.jbpm.services.api.AdvanceRuntimeDataService;

import org.kie.server.services.api.KieServerApplicationComponentsService;
import org.kie.server.services.api.KieServerRegistry;
import org.kie.server.services.api.SupportedTransports;
import org.kie.server.services.impl.KieServerImpl;
import org.kie.server.services.impl.KieServerLocator;
import org.kie.server.services.jbpm.JbpmKieServerExtension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KieServerExtension implements KieServerApplicationComponentsService {

	private static final Logger logger = LoggerFactory.getLogger(KieServerExtension.class);
	private final String OWNER_EXTENSION = JbpmKieServerExtension.EXTENSION_NAME;

	public Collection<Object> getAppComponents(String extension, SupportedTransports type, Object... services) {

		if (!OWNER_EXTENSION.equals(extension)) {
			return Collections.emptyList();
		}

		KieServerRegistry context = null;
		KieServerImpl server = KieServerLocator.getInstance();
		RuntimeDataService runtimeDataService = null;
		AdvanceRuntimeDataService advanceRuntimeDataService = null;
        DefinitionService definitionService = null;
        ProcessService  processService = null;

		for (Object object : services) {

            if (ProcessService.class.isAssignableFrom(object.getClass())) {
				logger.info("Obtaining handle to the process service...");
				processService = (ProcessService) object;
				continue;
			}
			else if (DefinitionService.class.isAssignableFrom(object.getClass())) {
				logger.info("Obtaining handle to the process definition service...");
				definitionService = (DefinitionService) object;
				continue;
			}
		  	else if (RuntimeDataService.class.isAssignableFrom(object.getClass())) {
				logger.info("Obtaining handle to the runtime data service...");
				runtimeDataService = (RuntimeDataService) object;
				continue;
			}
			else if (AdvanceRuntimeDataService.class.isAssignableFrom(object.getClass())) {
				logger.info("Obtaining handle to the advance runtime data service...");
				advanceRuntimeDataService = (AdvanceRuntimeDataService) object;
				continue;
			}
			else if (KieServerRegistry.class.isAssignableFrom(object.getClass())) {
				logger.info("Obtaining handle to the kie-server registry...");
				context = (KieServerRegistry) object;
				continue;
			}
		}
	
		List<Object> components = new ArrayList<Object>(1);
		if (SupportedTransports.REST.equals(type)) {

			try {
				logger.info("Adding process service resource endpoints...");
				components.add(new ProcessResource(processService, definitionService, runtimeDataService, advanceRuntimeDataService, context));

			} catch (Exception e) {
				logger.error("Unable to bind resource: Exception=" + e.getMessage());
			}
		}

		return components;
	}
}