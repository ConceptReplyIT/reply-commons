package it.reply.domain.dsl.info;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Contains debug information for the invoked service and its dependencies (in a
 * microservice-like architecture).
 * 
 * @author l.biava
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "service", "dependencies" })
public class ExtendedDebugInformation {

	@JsonProperty("service")
	DebugInformation serviceDebugInformation;
	@JsonProperty("dependencies")
	Map<String, DebugInformation> dependenciesDebugInformation = new HashMap<>();

	public DebugInformation getServiceDebugInformation() {
		return serviceDebugInformation;
	}

	public void setServiceDebugInformation(DebugInformation serviceDebugInformation) {
		this.serviceDebugInformation = serviceDebugInformation;
	}

	public Map<String, DebugInformation> getDependenciesDebugInformation() {
		return dependenciesDebugInformation;
	}

	public void setDependenciesDebugInformation(Map<String, DebugInformation> dependenciesDebugInformation) {
		this.dependenciesDebugInformation = dependenciesDebugInformation;
	}

}
