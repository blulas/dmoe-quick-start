package com.ibm.dba.dmoe.adaptors.kie;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import org.jbpm.services.api.DeploymentNotActiveException;
import org.jbpm.services.api.DeploymentNotFoundException;
import org.jbpm.services.api.ProcessDefinitionNotFoundException;
import org.jbpm.services.api.ProcessInstanceNotFoundException;
import org.jbpm.services.api.WorkItemNotFoundException;
import org.kie.server.api.model.definition.ProcessDefinitionList;
import org.kie.server.api.model.instance.NodeInstanceList;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.ProcessInstanceList;
import org.kie.server.api.model.instance.VariableInstanceList;
import org.kie.server.api.model.instance.WorkItemInstance;
import org.kie.server.api.model.instance.WorkItemInstanceList;
import org.kie.server.remote.rest.common.Header;
import org.kie.server.services.api.KieServerRegistry;
import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.DefinitionService;
import org.kie.server.services.impl.marshal.MarshallerHelper;
import org.kie.server.services.impl.locator.ContainerLocatorProvider;
import org.kie.server.api.model.instance.VariableInstanceList;
import org.jbpm.services.api.AdvanceRuntimeDataService;
import org.kie.server.services.jbpm.RuntimeDataServiceBase;
import org.kie.server.api.model.instance.VariableInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.kie.server.api.rest.RestURI;

import static org.kie.server.remote.rest.common.util.RestUtils.badRequest;
import static org.kie.server.remote.rest.common.util.RestUtils.buildConversationIdHeader;
import static org.kie.server.remote.rest.common.util.RestUtils.createCorrectVariant;
import static org.kie.server.remote.rest.common.util.RestUtils.createResponse;
import static org.kie.server.remote.rest.common.util.RestUtils.errorMessage;
import static org.kie.server.remote.rest.common.util.RestUtils.forbidden;
import static org.kie.server.remote.rest.common.util.RestUtils.getContentType;
import static org.kie.server.remote.rest.common.util.RestUtils.getVariant;
import static org.kie.server.remote.rest.common.util.RestUtils.internalServerError;
import static org.kie.server.remote.rest.common.util.RestUtils.noContent;
import static org.kie.server.remote.rest.common.util.RestUtils.notFound;

import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.GET_PROCESS_DEFS_RESPONSE_JSON;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.GET_PROCESS_INSTANCES_RESPONSE_JSON;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.GET_PROCESS_INSTANCE_NODES_RESPONSE_JSON;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.GET_PROCESS_INSTANCE_RESPONSE_JSON;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.GET_PROCESS_INSTANCE_SIGNALS_RESPONSE_JSON;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.GET_PROCESS_INSTANCE_VARS_LOG_RESPONSE_JSON;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.GET_PROCESS_INSTANCE_VARS_RESPONSE_JSON;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.GET_PROCESS_INSTANCE_VAR_RESPONSE_JSON;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.GET_PROCESS_INSTANCE_WORK_ITEMS_RESPONSE_JSON;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.GET_PROCESS_INSTANCE_WORK_ITEM_RESPONSE_JSON;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.JSON;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.LONG_RESPONSE_JSON;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.LONG_RESPONSE_XML;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.VAR_JSON;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.VAR_MAP_JSON;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.VAR_MAP_XML;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.VAR_XML;
import static org.kie.server.remote.rest.jbpm.docs.ParameterSamples.XML;
import static org.kie.server.remote.rest.jbpm.resources.Messages.CONTAINER_NOT_FOUND;
import static org.kie.server.remote.rest.jbpm.resources.Messages.CREATE_RESPONSE_ERROR;
import static org.kie.server.remote.rest.jbpm.resources.Messages.PROCESS_DEFINITION_NOT_FOUND;
import static org.kie.server.remote.rest.jbpm.resources.Messages.PROCESS_INSTANCE_NOT_FOUND;
import static org.kie.server.remote.rest.jbpm.resources.Messages.WORK_ITEM_NOT_FOUND;

@Api(value="Red Hat Business Automation - Extended Process Instances")
@Path("redhat/iap/ba/server/" + RestURI.PROCESS_URI)
public class ProcessResource  {

    public static final Logger logger = LoggerFactory.getLogger(ProcessResource.class);

    private ProcessService processService;
    private RuntimeDataService runtimeDataService;
    private	AdvanceRuntimeDataService advanceRuntimeDataService;
    private DefinitionService definitionService;
    private KieServerRegistry context;
	private MarshallerHelper marshallerHelper;

    public ProcessResource() {

    }

    public ProcessResource(ProcessService processService, DefinitionService definitionService, RuntimeDataService runtimeDataService, AdvanceRuntimeDataService advanceRuntimeDataService, KieServerRegistry context) {
        
        this.processService = processService;
        this.definitionService = definitionService;
        this.runtimeDataService = runtimeDataService;
        this.advanceRuntimeDataService = advanceRuntimeDataService;
        this.context = context;
		this.marshallerHelper = new MarshallerHelper(context);
    }

    protected static String getRelativePath(HttpServletRequest httpRequest) {
        
        String url =  httpRequest.getRequestURI();
        url = url.replaceAll( ".*/rest", "");
        return url;
    }

	@ApiOperation(value = "Starts a new process instance of a specified process.", response = Long.class, code = 201)
	@ApiResponses(value = { @ApiResponse(code = 201, response = Long.class, message = "Process instance started"),
			@ApiResponse(code = 500, message = "Unexpected error"),
			@ApiResponse(code = 404, message = "Process ID or Container Id not found") })
	@POST
	@Path(RestURI.START_PROCESS_POST_URI)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response startProcess(@javax.ws.rs.core.Context HttpHeaders headers,
			@ApiParam(value = "Container alias where the process definition resides", required = true) @PathParam(RestURI.CONTAINER_ID) String containerAlias,
			@ApiParam(value = "Process id that new instance should be created from", required = true) @PathParam(RestURI.PROCESS_ID) String processId,
			@ApiParam(value = "Optional map of process variables", required = false) @DefaultValue("") String payload) {

		final Variant v = getVariant(headers);
		final String type = getContentType(headers);
		final String containerId = context.getContainerId(containerAlias, ContainerLocatorProvider.get().getLocator());
		logger.info("Red Hat KIE Server Extension: startProcess(containerAlias=" + containerAlias + ", containerId=" + containerId + ", processID=" + processId + ")");

		try {
            Map<String, Object> payloadItems = new HashMap<String, Object>();
            payloadItems = marshallerHelper.unmarshal(containerId, payload, type, Map.class);

            // Start the process
			definitionService.getProcessDefinition(containerId, processId);
			Long processInstanceId = processService.startProcess(containerId, processId, payloadItems);

            // Format the response
			StartProcessResponse spResponse = new StartProcessResponse();
			spResponse.setProcessInstanceID(processInstanceId);

            // Grab the results and add to the response
            RuntimeDataServiceBase runtimeDataServiceBase = new RuntimeDataServiceBase(runtimeDataService, advanceRuntimeDataService, context);
            VariableInstanceList variableInstanceList = runtimeDataServiceBase.getVariablesCurrentState(processInstanceId);

            // Walk through the variables and add them to the response
            if (!variableInstanceList.getItems().isEmpty()) {
                for (VariableInstance var : variableInstanceList.getItems()) {

                    // This isn't a variable that we want to return, so skip it
                    if (!var.getVariableName().equals("initiator")) {

						ProcessVariable processVariable = new ProcessVariable();
						processVariable.setName(var.getVariableName());
						processVariable.setValue(var.getValue());
                        spResponse.getVariables().add(processVariable);
                    }
                }
            }

			// Format the response
			String response = marshallerHelper.marshal(containerId, type, spResponse);
			Header conversationIdHeader = buildConversationIdHeader(containerId, context, headers);
			return createResponse(response, v, Response.Status.CREATED, conversationIdHeader);
		} catch (DeploymentNotActiveException e) {
			return badRequest(e.getMessage(), v);
		} catch (DeploymentNotFoundException e) {
			return notFound(MessageFormat.format(CONTAINER_NOT_FOUND, containerId), v);
		} catch (ProcessDefinitionNotFoundException e) {
			return notFound(MessageFormat.format(PROCESS_DEFINITION_NOT_FOUND, processId, containerId), v);
		} catch (Exception e) {
			logger.error("Unexpected error during processing {}", e.getMessage(), e);
			return internalServerError(MessageFormat.format(CREATE_RESPONSE_ERROR, e.getMessage()), v);
		}
	}
}
