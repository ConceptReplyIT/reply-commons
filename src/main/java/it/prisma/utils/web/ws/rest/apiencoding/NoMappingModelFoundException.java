package it.prisma.utils.web.ws.rest.apiencoding;

/**
 * Exception representing that a Rest Result Decoding Strategy wasn't able to
 * decide what mapping class use for the given Rest message. <br/>
 * The original {@link RestMessage} is embedded for inspection.
 * 
 * @author l.biava
 * 
 */
public class NoMappingModelFoundException extends Exception {

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

	/*public NoMappingModelFoundException(String msg) {
		super(msg);
	}*/

	public NoMappingModelFoundException(RestMessage responseMessage) {
		super();
		this.responseMessage = responseMessage;
	}
}
