package it.prisma.utils.web.ws.rest.apiencoding;

import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.specimpl.MultivaluedMapImpl;

/**
 * This POJO holds a Rest Message (HttpStatusCode, Headers and Body).
 * @author l.biava
 *
 */
public class RestMessage {

	private MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();
	private Object body;
	private int httpStatusCode;

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public RestMessage(MultivaluedMap<String, Object> headers, Object body) {
		super();
		this.body = body;
		this.headers = headers;
	}

	public RestMessage(MultivaluedMap<String, Object> headers, Object body,
			int httpStatusCode) {
		super();
		this.headers = headers;
		this.body = body;
		this.httpStatusCode = httpStatusCode;
	}

	public void setHeaders(MultivaluedMap<String, Object> headers) {
		this.headers = headers;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public MultivaluedMap<String, Object> getHeaders() {
		return headers;
	}

	@Override
	public String toString() {
		return "RestMessage [headers=" + headers + ", body=" + body
				+ ", httpStatusCode=" + httpStatusCode + "]";
	}
	
}
