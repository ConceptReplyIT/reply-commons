package it.prisma.utils.web.ws.rest.apiclients.prisma;

import it.prisma.utils.web.ws.rest.apiencoding.MappingException;
import it.prisma.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.prisma.utils.web.ws.rest.apiencoding.RestMessage;
import it.prisma.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.prisma.utils.web.ws.rest.apiencoding.decode.BaseRestResponseDecoder;
import it.prisma.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;
import it.prisma.utils.web.ws.rest.apiencoding.decode.RestResponseDecodeStrategy;

import java.io.IOException;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.StatusType;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Basic implementation of a Rest response Decoder. This implementation uses
 * Jackson JSON mapping system.
 * 
 * @author l.biava
 * 
 */
public class PrismaRestResponseDecoder<APIResponseType> extends
		BaseRestResponseDecoder {

	public PrismaRestResponseDecoder(JavaType targetClass) {
		super();
		this.defaultDecodeStrategy = new PrismaRRDStrategy<APIResponseType>(targetClass);
	}
	
	public PrismaRestResponseDecoder(Class<APIResponseType> c) {
		super();
		this.defaultDecodeStrategy = new PrismaRRDStrategy<APIResponseType>(c);
	}

	public PrismaRestResponseDecoder(@SuppressWarnings("rawtypes") Class... c) {
		super();
		this.defaultDecodeStrategy = new PrismaRRDStrategy<APIResponseType>(c);
	}

	/**
	 * <b>Supports only default strategy. MUST pass null in strategy field !</b><br/>
	 * {@inheritDoc}
	 */
	@Override
	public BaseRestResponseResult decode(RestMessage msg,
			RestResponseDecodeStrategy strategy)
			throws NoMappingModelFoundException, MappingException,
			ServerErrorResponseException {

		if (strategy != null)
			throw new UnsupportedOperationException(
					"Only default decoding strategy can be used with this decoder.");

		return decode(msg);
	}

	@Override
	public BaseRestResponseResult decode(RestMessage msg)
			throws NoMappingModelFoundException, MappingException,
			ServerErrorResponseException {

		StatusType status = defaultDecodeStrategy.getStatus(msg);
		JavaType mappingClass = defaultDecodeStrategy.getModelClass(msg);

		// Check also content type (application/json)
		if (msg.getHeaders().containsKey(HttpHeaders.CONTENT_TYPE)
				&& !msg.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)
						.equals(MediaType.APPLICATION_JSON))
			throw new MappingException("Not JSON encoded body.", null, msg);

		Object result = null;
		if (msg.getBody() != null)
		{
			try {
				result = new ObjectMapper().readValue((String) msg.getBody(), mappingClass);
			} catch (IOException e) {
				throw new MappingException(e.getMessage(), e, msg);
			}
		}
		
		return new BaseRestResponseResult(status, result, mappingClass, msg);
	}

}
