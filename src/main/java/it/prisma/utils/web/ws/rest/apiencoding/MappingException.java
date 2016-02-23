package it.prisma.utils.web.ws.rest.apiencoding;

/**
 * Exception representing that a mapping exception has occurred (ie Jackson Json
 * parsing exception) <br/>
 * The original {@link RestMessage} is embedded for inspection.
 * 
 * @author l.biava
 * 
 */
public class MappingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3965412299072282395L;

	private RestMessage responseMessage;

	public RestMessage getResponseMessage() {
		return responseMessage;
	}

	public boolean hasResponseMessage() {
		return responseMessage != null;
	}

	public MappingException(String msg, Throwable t, RestMessage responseMessage) {
		super(msg, t);
		this.responseMessage = responseMessage;
	}
}
