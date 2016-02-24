package it.reply.domain.dsl.prisma.restprotocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import it.reply.domain.dsl.prisma.ErrorCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "errorCode", "errorName", "errorMsg", "verbose" })
public class Error implements Serializable {

	private static final long serialVersionUID = -8789415846582759944L;

	@JsonProperty("errorCode")
	private Integer errorCode;
	@JsonProperty("errorName")
	private String errorName;
	@JsonProperty("errorMsg")
	private String errorMsg;
	@JsonProperty("verbose")
	private String verbose;
	@JsonProperty("additionalProperties")
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	
	public Error() {
	}

	public Error(ErrorCode ec) {
		this.errorCode = ec.getCode();
		this.errorMsg = ec.getDescription();
		this.errorName = ec.getName();
	}

	@JsonProperty("errorCode")
	public Integer getErrorCode() {
		return errorCode;
	}

	@JsonProperty("errorCode")
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	@JsonProperty("errorName")
	public String getErrorName() {
		return errorName;
	}

	@JsonProperty("errorName")
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	@JsonProperty("errorMsg")
	public String getErrorMsg() {
		return errorMsg;
	}

	@JsonProperty("errorMsg")
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@JsonProperty("verbose")
	public String getVerbose() {
		return verbose;
	}

	@JsonProperty("verbose")
	public void setVerbose(String verbose) {
		this.verbose = verbose;
	}
	
	@JsonProperty("additionalProperties")
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}


	@JsonProperty("additionalProperties")
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
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

}