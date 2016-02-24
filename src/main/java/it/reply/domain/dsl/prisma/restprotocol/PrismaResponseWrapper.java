package it.reply.domain.dsl.prisma.restprotocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import it.reply.domain.dsl.prisma.ErrorCode;

/**
 * This class is the wrapper for every response of Prisma Rest Protocol.
 * 
 * @author l.biava
 * 
 * @param <ResultType>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "meta", "result", "error" })
public class PrismaResponseWrapper<ResultType> implements Serializable {

	private static final long serialVersionUID = -2767269124571953296L;
	@JsonProperty("meta")
	private Meta meta;
	@JsonProperty("result")
	private ResultType result;
	@JsonProperty("error")
	private Error error;
	@JsonProperty("additionalProperties")
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();


	public Meta getMeta() {
		return meta;
	}

	@JsonProperty("meta")
	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	@JsonProperty("result")
	public ResultType getResult() {
		return result;
	}

	@JsonProperty("result")
	public void setResult(ResultType result) {
		this.result = result;
	}

	@JsonProperty("error")
	public Error getError() {
		return error;
	}

	@JsonProperty("error")
	public void setError(Error error) {
		this.error = error;
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

	/**
	 * 
	 * @return true, if the meta status is an error (not 200)
	 */
	@JsonIgnore	
	public boolean isAPIError() {
		return getMeta().isError();
	}

	public static <ResultType> PrismaResponseWrapperBuilder status(
			StatusType status) {
		if (status.getFamily() != Family.SUCCESSFUL)
			throw new IllegalArgumentException(
					"An error response MUST have an Error body.");

		return new PrismaResponseWrapperBuilder(status);
	}

	public static <ResultType> PrismaResponseWrapperBuilder status(
			StatusType status, ResultType result) {
		if (status.getFamily() != Family.SUCCESSFUL)
			if (!(result instanceof Error))
				throw new IllegalArgumentException(
						"An error response MUST have an Error body.");

		return new PrismaResponseWrapperBuilder(status, result);
	}

	public static PrismaResponseWrapperBuilder status(StatusType status,
			Error error) {
		if (status.getFamily() != Family.CLIENT_ERROR
				&& status.getFamily() != Family.SERVER_ERROR)
			throw new IllegalArgumentException(
					"Only error response CAN have an Error body.");

		return new PrismaResponseWrapperBuilder(status, error);
	}

	public static PrismaResponseWrapperBuilder status(StatusType status,
			ErrorCode errorCode, String verbose) {
		if (status.getFamily() != Family.CLIENT_ERROR
				&& status.getFamily() != Family.SERVER_ERROR)
			throw new IllegalArgumentException(
					"Only error response CAN have an Error body.");

		return new PrismaResponseWrapperBuilder(status, errorCode, verbose);
	}

	public static class PrismaResponseWrapperBuilder {

		@SuppressWarnings("rawtypes")
		private final PrismaResponseWrapper responseWrapper;

		public <ResultType> PrismaResponseWrapperBuilder(StatusType status) {
			responseWrapper = new PrismaResponseWrapper<Object>();
			//responseWrapper.setResult(null);
			Meta meta = new Meta();
			meta.setStatus(status.getStatusCode());
			responseWrapper.setMeta(meta);
		}


		@SuppressWarnings("unchecked")
		public <ResultType> PrismaResponseWrapperBuilder(StatusType status,
				ResultType result) {
			responseWrapper = new PrismaResponseWrapper<ResultType>();
			responseWrapper.setResult(result);
			Meta meta = new Meta();
			meta.setStatus(status.getStatusCode());
			responseWrapper.setMeta(meta);
		}

		public PrismaResponseWrapperBuilder(StatusType status,
				ErrorCode errorCode, String verbose) {
			responseWrapper = new PrismaResponseWrapper<Object>();

			Error error = new Error();
			error.setErrorCode(errorCode.getCode());
			error.setErrorName(errorCode.getName());
			error.setErrorMsg(errorCode.getDescription());
			error.setVerbose(verbose);
			responseWrapper.setError(error);
			Meta meta = new Meta();
			meta.setStatus(status.getStatusCode());
			responseWrapper.setMeta(meta);
		}

		public PrismaResponseWrapperBuilder(StatusType status, Error error) {
			responseWrapper = new PrismaResponseWrapper<Object>();
			responseWrapper.setError(error);
			Meta meta = new Meta();
			meta.setStatus(status.getStatusCode());
			responseWrapper.setMeta(meta);
		}

		public PrismaResponseWrapperBuilder meta(Meta meta) {
			// TODO Add and not replace
			responseWrapper.setMeta(meta);
			return this;
		}

		public PrismaResponseWrapper<?> build() {
			return responseWrapper;
		}

	}

	// public static class PrismaResponseBuilder {
	//
	// @SuppressWarnings("rawtypes")
	// private final PrismaResponseWrapper responseWrapper;
	//
	// @SuppressWarnings("unchecked")
	// public <ResultType> PrismaResponseBuilder(ResultType result) {
	// responseWrapper=new PrismaResponseWrapper<ResultType>();
	// responseWrapper.setResult(result);
	// Meta meta = new Meta();
	// meta.setStatus(200);
	// responseWrapper.setMeta(meta);
	// }
	//
	// public PrismaResponseBuilder(ErrorCode errorCode, String verbose) {
	// responseWrapper=new PrismaResponseWrapper<Object>();
	//
	// Error error = new Error();
	// error.setErrorCode(errorCode.getCode());
	// error.setErrorName(errorCode.getName());
	// error.setErrorMsg(errorCode.getDescription());
	// error.setVerbose(verbose);
	// responseWrapper.setError(error);
	// Meta meta = new Meta();
	// meta.setStatus(500);
	// responseWrapper.setMeta(meta);
	// }
	//
	// public PrismaResponseBuilder(Error error) {
	// responseWrapper=new PrismaResponseWrapper<Object>();
	// responseWrapper.setError(error);
	// Meta meta = new Meta();
	// meta.setStatus(500);
	// responseWrapper.setMeta(meta);
	// }
	//
	// public PrismaResponseBuilder meta(Meta meta) {
	// responseWrapper.setMeta(meta);
	// return this;
	// }
	//
	// public PrismaResponseWrapper<?> build() {
	// return responseWrapper;
	// }
	//
	// }

}