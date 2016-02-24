package it.reply.utils.misc.polling;

import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A poller cycle implementation.
 * 
 * @author l.biava
 * 
 */
public class Poller<PARAMS_TYPE, RESULT_TYPE> {

	protected static Logger LOG = LogManager.getLogger(Poller.class);
	private static final String TAG = "[POLLER] ";

	private PollingBehaviour<PARAMS_TYPE, RESULT_TYPE> pollBehaviour;
	private int pollingInterval, retryInterval, retries;
	private boolean logEnabled = true;

	/**
	 * 
	 * @param delegate
	 * @param pollingInterval
	 *            in ms.
	 * @param retryInterval
	 *            in ms, <b>CURRENTLY UNSUPPORTED</b>.
	 * @param retries
	 *            max consecutive polling failures to retry.
	 */
	public Poller(PollingBehaviour<PARAMS_TYPE, RESULT_TYPE> delegate,
			int pollingInterval, int retryInterval, int retries) {
		super();
		this.pollBehaviour = delegate;
		this.pollingInterval = pollingInterval;
		this.retryInterval = retryInterval;
		this.retries = retries;
	}

	/**
	 * true as default.
	 * @return
	 */
	public boolean isLogEnabled() {
		return logEnabled;
	}

	public void setLogEnabled(boolean logEnabled) {
		this.logEnabled = logEnabled;
	}

	public PollingBehaviour<PARAMS_TYPE, RESULT_TYPE> getDelegate() {
		return pollBehaviour;
	}

	public int getPollingInterval() {
		return pollingInterval;
	}

	public int getRetryInterval() {
		return retryInterval;
	}

	public int getRetries() {
		return retries;
	}

	/**
	 * Does the polling cycle with the given polling behavior and given input
	 * parameters.
	 * 
	 * @param params
	 * @return
	 * 
	 * @throws PollingException
	 */
	public RESULT_TYPE doPoll(PARAMS_TYPE params) throws PollingException {

		boolean done = false;
		int err = 0;
		int polls = 0;
		long startTime = System.currentTimeMillis();
		RESULT_TYPE pollResult = null;

		// Polling
		do {
			done = false;
			err = 0;
			// Retry in case
			do {
				try {
					if (err > 0)
						Thread.sleep(retryInterval);
					else
						Thread.sleep(pollingInterval);

					if (pollBehaviour.timeoutOccurred(polls, err, startTime,
							params, pollResult))
						throw new TimeoutException("Polling timeout occurred.");

					pollResult = pollBehaviour.doPolling(params);

					polls++;

					LOG.debug(TAG + pollBehaviour.getClass()
							+ " - Poll Result: " + pollResult);

					done = true;
				} catch (TimeoutException te) {
					throw new PollingException("Polling timeout occurred.", te);
				} catch (PollingException ex) {
					err++;
					LOG.debug(TAG + pollBehaviour.getClass()
							+ " - Poll Error: " + ex.getMessage());
				} catch (Exception e) {
					LOG.error(TAG + pollBehaviour.getClass()
							+ " - Poll Error: " + e.getMessage());
				}
				if (err >= retries)
					throw new PollingException(new RetriesExceededException(
							"Poll retries exceeed, " + retries + " times."));
			} while (!done);

		} while (!pollBehaviour.pollExit(pollResult));

		if (!pollBehaviour.pollSuccessful(params, pollResult)) {
			PollingException ex = new PollingException("Polling unsuccessful");
			ex.setPollingParams(params);
			ex.setPollingResult(pollResult);
			throw ex;
		}

		return pollResult;
	}

}