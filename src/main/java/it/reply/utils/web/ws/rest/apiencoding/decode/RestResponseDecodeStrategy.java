package it.reply.utils.web.ws.rest.apiencoding.decode;

import javax.ws.rs.core.Response.StatusType;

import com.fasterxml.jackson.databind.JavaType;

import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.RestMessage;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;

/**
 * This is a strategy to decide if a given Rest message is an Error message or a
 * Result message. <br/>
 * The implementation can decide the way it wants what type of message it is and
 * what is the JSON mapping class.
 * 
 * @author l.biava
 * 
 */
public interface RestResponseDecodeStrategy {
	/**
	 * @param msg
	 *            The rest message.
	 * @return The mapping class for this message.
	 * @throws NoMappingModelFoundException
	 *             if the strategy isn't able to decide, and the no mapping
	 *             class was found.
	 * @throws ServerErrorResponseException
	 *             if the server responded with a custom error not using the
	 *             application protocolo (ie. 404)
	 */
	public JavaType getModelClass(RestMessage msg)
			throws NoMappingModelFoundException, ServerErrorResponseException;

	/**
	 * @param msg
	 *            The rest message.
	 * @return The status of this message (OK, ERROR).
	 * @throws NoMappingModelFoundException
	 *             if the strategy isn't able to decide.
	 * @throws ServerErrorResponseException
	 *             if the server responded with a custom error not using the
	 *             application protocolo (ie. 404)
	 */
	public StatusType getStatus(RestMessage msg)
			throws NoMappingModelFoundException, ServerErrorResponseException;
}
