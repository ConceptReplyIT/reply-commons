package it.prisma.domain.dsl.prisma;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "projectVersion", "serverHostname" })
public class DebugInformations {

	@JsonProperty("projectVersion")
	private String projectVersion;
	@JsonProperty("serverHostname")
	private String serverHostname;
	private String projectRevision;
	private String projectTimestamp;

	public String getProjectRevision() {
		return projectRevision;
	}

	public void setProjectRevision(String projectRevision) {
		this.projectRevision = projectRevision;
	}

	public String getProjectTimestamp() {
		return projectTimestamp;
	}

	public void setProjectTimestamp(String projectTimestamp) {
		this.projectTimestamp = projectTimestamp;
	}

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The projectVersion
	 */
	@JsonProperty("projectVersion")
	public String getProjectVersion() {
		return projectVersion;
	}

	/**
	 * 
	 * @param projectVersion
	 *            The projectVersion
	 */
	@JsonProperty("projectVersion")
	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	/**
	 * 
	 * @return The serverHostname
	 */
	@JsonProperty("serverHostname")
	public String getServerHostname() {
		return serverHostname;
	}

	/**
	 * 
	 * @param serverHostname
	 *            The serverHostname
	 */
	@JsonProperty("serverHostname")
	public void setServerHostname(String serverHostname) {
		this.serverHostname = serverHostname;
	}

}