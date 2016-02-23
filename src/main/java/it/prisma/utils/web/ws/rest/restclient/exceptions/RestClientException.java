package it.prisma.utils.web.ws.rest.restclient.exceptions;

/**
 * Exception representing some error has occurred during a Rest Request.
 */
public class RestClientException extends Exception {

	private static final long serialVersionUID = 8920282002793758632L;

	public RestClientException(String msg, Throwable t) {
		super(msg, t);
	}

	public RestClientException(String msg) {
		super(msg);
	}
	
	public RestClientException(Throwable t) {
		super(t);
	}
}
