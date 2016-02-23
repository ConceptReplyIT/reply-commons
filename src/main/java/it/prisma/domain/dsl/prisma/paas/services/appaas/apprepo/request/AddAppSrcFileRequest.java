package it.prisma.domain.dsl.prisma.paas.services.appaas.apprepo.request;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "userId", "tag", "appName",
		"appDescription", "public", "fileName" })
public class AddAppSrcFileRequest {

	@JsonProperty("tag")
	private String tag;
	@JsonProperty("appName")
	private String appName;
	@JsonProperty("appDescription")
	private String appDescription;
	@JsonProperty("fileName")
	private String fileName;
	@JsonProperty("url")
	private String url;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("tag")
	public String getTag() {
		return tag;
	}

	@JsonProperty("tag")
	public void setTag(String tag) {
		this.tag = tag;
	}

	@JsonProperty("appName")
	public String getAppName() {
		return appName;
	}

	@JsonProperty("appName")
	public void setAppName(String appName) {
		this.appName = appName;
	}

	@JsonProperty("appDescription")
	public String getAppDescription() {
		return appDescription;
	}

	@JsonProperty("appDescription")
	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}

	@JsonProperty("fileName")
	public String getFileName() {
		return fileName;
	}

	@JsonProperty("fileName")
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@JsonProperty("url")
	public String getURL() {
		return url;
	}

	@JsonProperty("url")
	public void setURL(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}