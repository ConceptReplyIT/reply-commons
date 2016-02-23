package it.prisma.utils.web.ws.rest.apiencoding.decode;

import it.prisma.utils.web.ws.rest.apiencoding.RestMessage;

import javax.ws.rs.core.Response.StatusType;

import com.fasterxml.jackson.databind.JavaType;

/**
 * Basic Rest Result container.
 * Can be extended to increase specific-application related informations.
 * @author l.biava
 *
 */
public class BaseRestResponseResult {
//	public enum StatusType {
//		OK, ERROR
//	}

	private StatusType status;
	private Object result;
	private JavaType resultClass;
	private RestMessage originalRestMessage;

	public BaseRestResponseResult(StatusType status, Object result,
			JavaType resultClass, RestMessage originalRestMessage) {
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

	public RestMessage getOriginalRestMessage() {
		return originalRestMessage;
	}

	public StatusType getStatus() {
		return status;
	}

	public Object getResult() {
		return result;
	}

	public JavaType getResultClass() {
		return resultClass;
	}
}
