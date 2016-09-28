package it.reply.utils.web.ws.rest.apiencoding.decode;

import javax.ws.rs.core.Response.StatusType;

import com.fasterxml.jackson.databind.JavaType;

import it.reply.utils.web.ws.rest.apiencoding.RestMessage;

/**
 * Basic Rest Result container.
 * Can be extended to increase specific-application related informations.
 * @author l.biava
 *
 */
public class BaseRestResponseResult<R, O> {
//	public enum StatusType {
//		OK, ERROR
//	}

	private StatusType status;
	private R result;
	private JavaType resultClass;
	private RestMessage<O> originalRestMessage;

	public BaseRestResponseResult(StatusType status, R result,
			JavaType resultClass, RestMessage<O> originalRestMessage) {
		super();
		this.status = status;
		this.result = result;
		this.resultClass = resultClass;
		this.originalRestMessage=originalRestMessage;
	}
	
//	public BaseRestResponseResult(StatusType status, Object result,
//			JavaType resultClass) {
//		super();
//		this.status = status;
//		this.result = result;
//		this.resultClass = resultClass;
//	}

	public RestMessage<O> getOriginalRestMessage() {
		return originalRestMessage;
	}

	public StatusType getStatus() {
		return status;
	}

	public R getResult() {
		return result;
	}

	public JavaType getResultClass() {
		return resultClass;
	}
}
