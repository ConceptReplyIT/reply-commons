package it.reply.utils.misc.polling;

/**
 * Represents that the polling retries in case of error exceeded the max value.
 * 
 * @author l.biava
 *
 */
public class RetriesExceededException extends RuntimeException {

	private static final long serialVersionUID = -3173005023061236853L;

	public RetriesExceededException() {
		super();
	}

	public RetriesExceededException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RetriesExceededException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetriesExceededException(String message) {
		super(message);
	}

	public RetriesExceededException(Throwable cause) {
		super(cause);
	}
}
