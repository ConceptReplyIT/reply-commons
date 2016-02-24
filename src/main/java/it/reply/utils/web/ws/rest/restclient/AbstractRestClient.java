package it.reply.utils.web.ws.rest.restclient;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.RestMessage;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecodeStrategy;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecoder;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

public abstract class AbstractRestClient<T extends BaseRestResponseResult>
		implements RestClient<T> {

	protected int defaultTimeout = 60000; // in ms

	@Override
	public int getDefaultTimeout() {
		return this.defaultTimeout;
	}

	@Override
	public void setDefaultTimeout(int defaultTimeout) {
		this.defaultTimeout = defaultTimeout;
	}

	/***************************** GET Requests ******************************/

	@Override
	public T getRequest(String URL, MultivaluedMap<String, Object> headers,
			RestResponseDecoder<T> rrd, RestResponseDecodeStrategy strategy)
			throws RestClientException, NoMappingModelFoundException,
			MappingException, ServerErrorResponseException {

		return getRequest(URL, headers, null, rrd, strategy);
	}

	@Override
	public RestMessage getRequest(String URL,
			MultivaluedMap<String, Object> headers) throws RestClientException {

		return getRequest(URL, headers, null);
	}

	/***************************** HEAD Requests ******************************/

	@Override
	public T headRequest(String URL, MultivaluedMap<String, Object> headers,
			RestResponseDecoder<T> rrd, RestResponseDecodeStrategy strategy)
			throws RestClientException, NoMappingModelFoundException,
			MappingException, ServerErrorResponseException {

		return headRequest(URL, headers, null, rrd, strategy);
	}

	@Override
	public RestMessage headRequest(String URL,
			MultivaluedMap<String, Object> headers) throws RestClientException {

		return headRequest(URL, headers, null);
	}

	/***************************** POST Requests ******************************/

	@Override
	public <E> T postRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<E> body,
			MediaType bodyMediaType, RestResponseDecoder<T> rrd,
			RestResponseDecodeStrategy strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException,
			ServerErrorResponseException {

		return postRequest(URL, headers, body, bodyMediaType, null, rrd,
				strategy);

	}

	@Override
	public <E> RestMessage postRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<E> body,
			MediaType bodyMediaType) throws RestClientException {

		return postRequest(URL, headers, body, bodyMediaType, null);
	}

	/***************************** PUT Requests ******************************/

	@Override
	public <E> T putRequest(String URL, MultivaluedMap<String, Object> headers,
			GenericEntity<E> body, MediaType bodyMediaType,
			RestResponseDecoder<T> rrd, RestResponseDecodeStrategy strategy)
			throws RestClientException, NoMappingModelFoundException,
			MappingException, ServerErrorResponseException {

		return putRequest(URL, headers, body, bodyMediaType, null, rrd,
				strategy);
	}

	@Override
	public <E> RestMessage putRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<E> body,
			MediaType bodyMediaType) throws RestClientException {

		return putRequest(URL, headers, body, bodyMediaType, null);
	}

	/***************************** DELETE Requests ******************************/

	@Override
	public <E> T deleteRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<E> body,
			MediaType bodyMediaType, RestResponseDecoder<T> rrd,
			RestResponseDecodeStrategy strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException,
			ServerErrorResponseException {

		return deleteRequest(URL, headers, body, bodyMediaType, null, rrd,
				strategy);
	}

	@Override
	public <E> RestMessage deleteRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<E> body,
			MediaType bodyMediaType) throws RestClientException {

		return deleteRequest(URL, headers, body, bodyMediaType, null);
	}

}
