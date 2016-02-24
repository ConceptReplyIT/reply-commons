package it.reply.utils.misc.polling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Partial implementation of a basic polling behavior, always succeeding in case
 * of absence of explicit polling error (see
 * {@link PollingBehaviour#pollExit(RESULT_TYPE)}).
 * 
 * @author l.biava
 * 
 */
public abstract class AbstractPollingBehaviour<PARAMS_TYPE, RESULT_TYPE>
		implements PollingBehaviour<PARAMS_TYPE, RESULT_TYPE> {

	private static final long serialVersionUID = 969315240987681439L;

	protected long timeoutThreshold = 60000; // in ms

	protected static final Logger LOG = LogManager.getLogger(AbstractPollingBehaviour.class);

	public AbstractPollingBehaviour() {
	}

	/**
	 * Constructor to specify the timeout interval.
	 * 
	 * @param timeoutThreshold
	 *            the timeout interval in ms.
	 */
	public AbstractPollingBehaviour(long timeoutThreshold) {
		this.timeoutThreshold = timeoutThreshold;
	}

	/**
	 * Default implementation, returning always <tt>true</tt>.
	 * 
	 * @see PollingBehaviour#pollSuccessful(Object, Object)
	 */
	@Override
	public boolean pollSuccessful(PARAMS_TYPE params, RESULT_TYPE pollResult) {
		return true;
	}

	/**
	 * Default implementation, timeout occurs after the specified threshold.
	 * 
	 * @see PollingBehaviour#timeoutOccurred(int, int, long, Object, Object)
	 * @see AbstractPollingBehaviour#setTimeoutThreshold(long)
	 */
	@Override
	public boolean timeoutOccurred(int successfullPolls, int failedPolls,
			long startTime, PARAMS_TYPE params, RESULT_TYPE pollResult) {
		if (System.currentTimeMillis() - startTime > timeoutThreshold) {
			LOG.warn("Polling timeout occurred after " + timeoutThreshold
					+ " ms");
			return true;
		}

		return false;
	}

	public long getTimeoutThreshold() {
		return timeoutThreshold;
	}

	public void setTimeoutThreshold(long timeoutThreshold) {
		this.timeoutThreshold = timeoutThreshold;
	}

}