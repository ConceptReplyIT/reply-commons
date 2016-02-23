package it.prisma.utils.misc.polling;

/**
 * Represents that a polling exception happened..
 * 
 * @author l.biava
 *
 */
public class PollingException extends RuntimeException {

	private static final long serialVersionUID = -3173005023061236853L;

	protected Object pollingResult;
	protected Object pollingParams;

	public PollingException() {
		super();
	}

	public PollingException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PollingException(String message, Throwable cause) {
		super(message, cause);
	}

	public PollingException(String message) {
		super(message);
	}

	public PollingException(Throwable cause) {
		super(cause);
	}

	public Object getPollingResult() {
		return pollingResult;
	}

	public void setPollingResult(Object pollingResult) {
		this.pollingResult = pollingResult;
	}

	public Object getPollingParams() {
		return pollingParams;
	}

	public void setPollingParams(Object pollingParams) {
		this.pollingParams = pollingParams;
	}

	@Override
	public String toString() {
		return "PollingException [pollingResult=" + pollingResult
				+ ", pollingParams=" + pollingParams + "] " + super.toString();
	}

}