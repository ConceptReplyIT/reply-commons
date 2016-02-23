package it.prisma.utils.web.ws.rest.apiclients.prisma;

import it.prisma.utils.web.ws.rest.apiencoding.RestMessage;

/**
 * Exception representing that the API returned an error status. <br/>
 * The original {@link RestMessage} is embedded for inspection.
 * 
 * @author l.biava
 * 
 */
public class APIErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3965412299072282395L;

	private RestMessage responseMessage;
	private it.prisma.domain.dsl.prisma.prismaprotocol.Error APIError;

	public RestMessage getResponseMessage() {
		return responseMessage;
	}

	public boolean hasResponseMessage() {
		return responseMessage != null;
	}

	public APIErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public APIErrorException(String message) {
		super(message);
	}

	public APIErrorException(Throwable cause) {
		super(cause);
	}

	public APIErrorException(String msg, Throwable t,
			RestMessage responseMessage, it.prisma.domain.dsl.prisma.prismaprotocol.Error APIError) {
		super(msg, t);
		this.responseMessage = responseMessage;
		this.APIError = APIError;
	}

	public it.prisma.domain.dsl.prisma.prismaprotocol.Error getAPIError() {
		return APIError;
	}

	@Override
	public String toString() {
		return "APIErrorException [responseMessage=" + responseMessage
				+ ", APIError=" + APIError + "]";
	}
}
