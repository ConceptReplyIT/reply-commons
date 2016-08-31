package it.reply.utils.web.ws.rest.apiencoding.decode;

import java.io.IOException;

import javax.ws.rs.core.Response.StatusType;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.RestMessage;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;

/**
 * Basic implementation of a Rest response Decoder. This implementation uses
 * Jackson JSON mapping system.
 * 
 * @author l.biava
 * 
 */
public class BaseRestResponseDecoder implements
		RestResponseDecoder<BaseRestResponseResult<String>, String> {

	protected RestResponseDecodeStrategy defaultDecodeStrategy;

	public BaseRestResponseDecoder() {
		super();
	}

	public BaseRestResponseDecoder(
			RestResponseDecodeStrategy defaultDecodeStrategy) {
		super();
		this.defaultDecodeStrategy = defaultDecodeStrategy;
	}

	public RestResponseDecodeStrategy getDefaultDecodeStrategy() {
		return defaultDecodeStrategy;
	}

	public void setDefaultDecodeStrategy(
			RestResponseDecodeStrategy defaultDecodeStrategy) {
		this.defaultDecodeStrategy = defaultDecodeStrategy;
	}

	@Override
	public BaseRestResponseResult<String> decode(RestMessage<String> msg,
			RestResponseDecodeStrategy strategy)
			throws NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		StatusType status = strategy.getStatus(msg);
		JavaType mappingClass = strategy.getModelClass(msg);

		//TODO:Check also content type (application/json)	
		//FIX for Cloudify not responding with Content-type if JSON body !
		if(msg.getHeaders().containsKey("Content-Type") & !"application/json".equals(msg.getHeaders().getFirst("Content-Type")))
			throw new MappingException("Not JSON encoded body.", null, msg);
		
		// TODO: Jackson Mapping
		/*
		 * Object result = (Object) JsonUtility.deserializeJson(msg.getBody(),
		 * mappingClass);
		 */
		Object result;
		try {
			result = new ObjectMapper().readValue(msg.getBody(),
					mappingClass);
		} catch (IOException e) {
			throw new MappingException(e.getMessage(), e, msg);
		}

		return new BaseRestResponseResult<String>(status, result, mappingClass, msg);
	}

	@Override
	public BaseRestResponseResult<String> decode(RestMessage<String> msg)
			throws NoMappingModelFoundException, MappingException, ServerErrorResponseException {
		if (defaultDecodeStrategy == null)
			throw new IllegalStateException(
					"No default decoding strategy specified.");

		return decode(msg, defaultDecodeStrategy);
	}

}
