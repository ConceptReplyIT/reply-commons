package it.reply.utils.misc.polling;

import java.io.Serializable;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A poller implementation controlled externally by invoking a single poll cycle at a time. This
 * class preserves the status of the polling and checks whether the polling must be repeated
 * (waiting for exit condition or to retry in case of an error) or if it has timed out.
 * 
 * @author l.biava
 * 
 */
public class ExternallyControlledPoller<PARAMS_TYPE, RESULT_TYPE> implements Serializable {

  public enum PollingStatus {
    IDLE, RUNNING, ERROR_RETRYING, ENDED,
  }

  private static final long serialVersionUID = -9095947171133859249L;

  protected static final Logger LOG = LoggerFactory.getLogger(ExternallyControlledPoller.class);
  private static final String TAG = "[POLLER] ";

  private PollingBehaviour<PARAMS_TYPE, RESULT_TYPE> pollBehaviour;
  private int retries;
  private boolean logEnabled = true;

  // Polling status variables
  private long startTime;
  int errors = 0;
  int polls = 0;
  boolean pollWithoutError = false;
  boolean initialised = false;
  PollingStatus pollStatus = PollingStatus.IDLE;
  private PollingException lastException;

  /**
   * Initializes polling status.
   */
  protected void initialise() {
    pollStatus = PollingStatus.IDLE;
    pollWithoutError = false;
    errors = 0;
    polls = 0;
    startTime = System.currentTimeMillis();
    initialised = true;
  }

  /**
   * 
   * @param delegate
   * @param pollingInterval
   *          in ms.
   * @param retryInterval
   *          in ms, <b>CURRENTLY UNSUPPORTED</b>.
   * @param retries
   *          max consecutive polling failures to retry.
   */
  public ExternallyControlledPoller(PollingBehaviour<PARAMS_TYPE, RESULT_TYPE> delegate,
      int retries) {
    super();
    this.pollBehaviour = delegate;
    this.retries = retries;
  }

  /**
   * true as default.
   * 
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

  public int getRetries() {
    return retries;
  }

  public Exception getLastException() {
    return lastException;
  }

  /**
   * Resets internal polling data for a new poll.
   */
  public void reset() {
    initialise();
  }

  public boolean isInitialised() {
    return initialised;
  }

  /**
   * 
   * @return the {@link PollingStatus} of the polling.
   */
  public PollingStatus getPollStatus() {
    return pollStatus;
  }

  /**
   * Does one cycle with the given polling behavior and given input parameters.
   * 
   * @param params
   *          the input parameters for the polling behavior.
   * @return the result of the polling behavior in case polling has ended, <tt>null</tt> otherwise
   *         (check polling status with {@link ExternallyControlledPoller#isPollEnded()})
   * 
   * @throws PollingException
   *           if:
   *           <ul>
   *           <li>a timeout occurred</li>
   *           <li>retries exceeded</li>
   *           <li>the polling has ended unsuccessfully ({@link PollingBehaviour#pollExit()}
   *           <tt>=true &&</tt> {@link PollingBehaviour#pollSuccessful()} <tt>=false</tt>)</li>
   *           </ul>
   */
  public RESULT_TYPE doPollEvent(PARAMS_TYPE params) throws PollingException {

    if (!initialised) {
      initialise();
      pollStatus = PollingStatus.RUNNING;
    }

    RESULT_TYPE pollResult = null;

    try {
      // Check if timeout has occurred
      if (pollBehaviour.timeoutOccurred(polls, errors, startTime, params, pollResult))
        throw new TimeoutException("Timeout occurred.");

      pollResult = pollBehaviour.doPolling(params);

      polls++;

      LOG.debug(TAG + pollBehaviour.getClass() + " - Poll Result: " + pollResult.toString());

      pollStatus = PollingStatus.RUNNING;
      errors = 0;

      // } catch (PollingException ex) {
      // // Polling error occurred
      // errors++;
      // pollStatus=PollingStatus.ERROR_RETRYING;
      // LOG.debug(TAG + pollBehaviour.getClass() + " - Poll Error: "
      // + ex.getMessage());
    } catch (TimeoutException te) {
      throw new PollingException("Timeout occurred.", te);
    } catch (Exception e) {
      // Polling error occurred
      errors++;
      pollStatus = PollingStatus.ERROR_RETRYING;
      LOG.error(TAG + pollBehaviour.getClass() + " - Poll Error: " + e.getMessage());
      if (!(e instanceof PollingException)) {
        e = new PollingException(e);
      }
      lastException = (PollingException) e;
    }

    // Retries exceeded
    if (errors >= retries)
      throw new PollingException(
          new RetriesExceededException("Poll retries exceeed, " + retries + " times."));

    // Polling error occurred or Polling not ended yet
    if (pollStatus == PollingStatus.ERROR_RETRYING || !pollBehaviour.pollExit(pollResult))
      return null;

    // Polling ended
    pollStatus = PollingStatus.ENDED;
    if (!pollBehaviour.pollSuccessful(params, pollResult)) {
      PollingException ex = new PollingException("Polling unsuccessful");
      ex.setPollingParams(params);
      ex.setPollingResult(pollResult);
      throw ex;
    }

    return pollResult;
  }

}