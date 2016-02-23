package it.prisma.utils.web.ws.rest.apiencoding.decode;

import it.prisma.utils.web.ws.rest.apiencoding.MappingException;
import it.prisma.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.prisma.utils.web.ws.rest.apiencoding.RestMessage;
import it.prisma.utils.web.ws.rest.apiencoding.ServerErrorResponseException;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * System to decode Rest Result from the JSON mapping to POJOs. <br/>
 * This system is meant to use a {@link RestResponseDecodeStrategy} to choose
 * which mapping class to use for any given Rest message. <br/>
 * <b>NOTE:</b>The decoder implementation MUST use a default decoding strategy
 * for the second method !. <br/>
 * <br/>
 * The result must be a class extending {@link BaseRestResponseResult} or that
 * class itself.
 * 
 * @author l.biava
 * 
 * @param <T>
 */
public interface RestResponseDecoder<T extends BaseRestResponseResult> {

	/**
	 * Decodes the given Rest Message body from JSON to a specific Mapping Class
	 * given by the decoding strategy (or the default strategy in the decoder
	 * itself).
	 * 
	 * @param msg
	 *            The Rest message to decode.
	 * @param strategy
	 *            The strategy to ovverride default decoder strategy. If the
	 *            strategy is null the default will be used.
	 * @return The result of the decoding.
	 * @throws NoMappingModelFoundException
	 *             when no mapping class is found.
	 * @throws JsonParseException
	 *             when the Json parsing fails.
	 * @throws JsonMappingException
	 *             when the Json parsing fails.
	 * @throws IOException
	 *             when the Json parsing fails.
	 * @throws ServerErrorResponseException
	 *             if the server responded with a custom error not using the
	 *             application protocolo (ie. 404)
	 * @throws MappingException
	 *             if a mapping exception has occurred (ie Jackson Json parsing
	 *             exception)
	 */
	public T decode(RestMessage msg, RestResponseDecodeStrategy strategy)
			throws NoMappingModelFoundException, MappingException,
			ServerErrorResponseException;

	/**
	 * Decodes the given Rest Message body from JSON to a specific Mapping Class
	 * given by the default strategy in the decoder itself.
	 * 
	 * @param msg
	 *            The Rest message to decode.
	 * @return The result of the decoding.
	 * @throws NoMappingModelFoundException
	 *             when no mapping class is found.
	 * @throws JsonParseException
	 *             when the Json parsing fails.
	 * @throws JsonMappingException
	 *             when the Json parsing fails.
	 * @throws IOException
	 *             when the Json parsing fails.
	 * @throws ServerErrorResponseException
	 *             if the server responded with a custom error not using the
	 *             application protocolo (ie. 404)
	 * @throws MappingException
	 *             if a mapping exception has occurred (ie Jackson Json parsing
	 *             exception)
	 */
	public T decode(RestMessage msg) throws NoMappingModelFoundException,
			MappingException, ServerErrorResponseException;

}
