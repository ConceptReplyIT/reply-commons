package it.prisma.utils.web.ws.rest.apiencoding;

/**
 * Exception representing that the server returned an error response which is
 * not in the application format (ie. 404) <br/>
 * The original {@link RestMessage} and HTTP_STATUS_CODE are embedded for inspection.
 * 
 * @author l.biava
 * 
 */
public class ServerErrorResponseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3965412299072282395L;

	private RestMessage responseMessage;
	private int httpStatusCode;

	public RestMessage getResponseMessage() {
		return responseMessage;
	}

	public boolean hasResponseMessage() {
		return responseMessage != null;
	}

	public ServerErrorResponseException(String msg, Throwable t,
			RestMessage responseMessage, int httpStatusCode) {
		super(msg, t);
		this.responseMessage = responseMessage;
		this.httpStatusCode = httpStatusCode;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	@Override
	public String toString() {
		return "ServerErrorResponseException [responseMessage="
				+ responseMessage + ", httpStatusCode=" + httpStatusCode + "]";
	}

}
