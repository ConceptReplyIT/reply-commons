package it.reply.utils.web.ws.rest.restclient;

import javax.ws.rs.core.MultivaluedMap;

import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecodeStrategy;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecoder;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

public interface RestClientOld<T extends BaseRestResponseResult>  {

	public T getRequest(String URL, MultivaluedMap<String, Object> headers);
	public T postRequestJSON(String URL, MultivaluedMap<String, Object> headers, String jsonbody, RestResponseDecoder<T> rrd) throws RestClientException;
	public T postRequestJSON(String URL, MultivaluedMap<String, Object> headers, String jsonbody, RestResponseDecoder<T> rrd, RestResponseDecodeStrategy strategy) throws RestClientException;
	public T postRequestForm(String URL, MultivaluedMap<String, Object> headers, String body, RestResponseDecoder<T> rrd);
	
	
}
