package it.reply.utils.web.ws.rest.apiencoding;

import javax.ws.rs.core.MultivaluedMap;

import com.google.common.base.MoreObjects;

import org.jboss.resteasy.specimpl.MultivaluedMapImpl;

/**
 * This POJO holds a Rest Message (HttpStatusCode, Headers and Body).
 * @author l.biava
 *
 */
public class RestMessage<E> {

	private MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();
	private E body;
	private int httpStatusCode;

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public RestMessage(MultivaluedMap<String, Object> headers, E body) {
		super();
		this.body = body;
		this.headers = headers;
	}

	public RestMessage(MultivaluedMap<String, Object> headers, E body,
			int httpStatusCode) {
		super();
		this.headers = headers;
		this.body = body;
		this.httpStatusCode = httpStatusCode;
	}

	public void setHeaders(MultivaluedMap<String, Object> headers) {
		this.headers = headers;
	}

	public E getBody() {
		return body;
	}

	public void setBody(E body) {
		this.body = body;
	}

	public MultivaluedMap<String, Object> getHeaders() {
		return headers;
	}

	@Override
	public String toString() {
	  return MoreObjects.toStringHelper(this)
	  .add("headers", headers)
	  .add("body", body)
	  .add("httpStatusCode", httpStatusCode).toString();
	}
	
}
