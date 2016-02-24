package it.reply.utils.misc.polling;

import java.io.Serializable;

/**
 * Defines a polling behavior.
 * 
 * @author l.biava
 *
 */
public interface PollingBehaviour<PARAMS_TYPE, RESULT_TYPE> extends Serializable {

	/**
	 * The polling logic, accepting <tt>params</tt> in input and returning a result. <br/>
	 * In case of polling failed MUST throw an Exception, in case of polling
	 * success MUST return the result, which will be evaluated
	 * for exit condition by {@link #pollExit(Object)}
	 * 
	 * @param params
	 *            the input parameters.
	 * @return the polling result.
	 */
	RESULT_TYPE doPolling(PARAMS_TYPE params) throws PollingException;

	/**
	 * Checks whether the polling cycle must exit, given the polling result.
	 * 
	 * @param pollResult
	 *            the polling result.
	 * @return <tt>true</tt> if the polling cycle must exist,
	 *         <tt>false<tt> otherwise.
	 */
	boolean pollExit(RESULT_TYPE pollResult);

	/**
	 * Checks whether the polling was successful.
	 * @param params
	 *            the polling invocation input parameters.
	 * @param pollResult pollResult
	 *            the polling result
	 * @return <tt>true</tt> if the polling was successful,
	 *         <tt>false<tt> otherwise.
	 */
	boolean pollSuccessful(PARAMS_TYPE params, RESULT_TYPE pollResult);

	/**
	 * Should decide whether a timeout occurred.
	 * @param successfullPolls
	 * @param failedPolls
	 * @param startTime
	 * @param params
	 * @param pollResult
	 * @return returns <tt>true</tt> in case timeout occurred, <tt>false</tt> otherwise.
	 */
	boolean timeoutOccurred(int successfullPolls, int failedPolls, long startTime, PARAMS_TYPE params, RESULT_TYPE pollResult);
}