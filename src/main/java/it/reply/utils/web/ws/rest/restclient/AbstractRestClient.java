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

public abstract class AbstractRestClient
		implements RestClient {

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
	public <T extends BaseRestResponseResult<String>> T getRequest(String URL, MultivaluedMap<String, Object> headers,
			RestResponseDecoder<T, String> rrd, RestResponseDecodeStrategy strategy)
			throws RestClientException, NoMappingModelFoundException,
			MappingException, ServerErrorResponseException {

		return getRequest(URL, headers, null, rrd, strategy);
	}

	@Override
	public RestMessage<String> getRequest(String URL,
			MultivaluedMap<String, Object> headers) throws RestClientException {

		return getRequest(URL, headers, null);
	}

	@Override
	public <T extends BaseRestResponseResult<String>> T getRequest(String URL, MultivaluedMap<String, Object> headers,
	    Request.Options reqOptions, RestResponseDecoder<T, String> rrd,
	    RestResponseDecodeStrategy strategy) throws RestClientException, NoMappingModelFoundException,
	    MappingException, ServerErrorResponseException {

	  RestMessage<String> msg = doRequest(Request.RestMethod.GET, URL, headers, null, null, null, reqOptions);

	  return rrd.decode(msg, strategy);
	}

	@Override
	public RestMessage<String> getRequest(String URL, MultivaluedMap<String, Object> headers,
	    Request.Options reqOptions) throws RestClientException {

	  return doRequest(Request.RestMethod.GET, URL, headers, null, null, null, reqOptions);
	}
	
	/***************************** HEAD Requests ******************************/

	@Override
	public RestMessage<Void> headRequest(String URL,
			MultivaluedMap<String, Object> headers) throws RestClientException {

		return headRequest(URL, headers, null);
	}

	@Override
	public RestMessage<Void> headRequest(String URL, MultivaluedMap<String, Object> headers,
	    Request.Options reqOptions) throws RestClientException {

	  return doRequest(Request.RestMethod.HEAD, URL, headers, null, null, null, reqOptions, Void.class);
	}
	
	/***************************** POST Requests ******************************/

	@Override
	public <T extends BaseRestResponseResult<String>> T postRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<?> body,
			MediaType bodyMediaType, RestResponseDecoder<T, String> rrd,
			RestResponseDecodeStrategy strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException,
			ServerErrorResponseException {

		return postRequest(URL, headers, body, bodyMediaType, null, rrd,
				strategy);

	}

	@Override
	public RestMessage<String> postRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<?> body,
			MediaType bodyMediaType) throws RestClientException {

		return postRequest(URL, headers, body, bodyMediaType, null);
	}

	@Override
	public <T extends BaseRestResponseResult<String>> T postRequest(String URL, MultivaluedMap<String, Object> headers, GenericEntity<?> body,
	    MediaType bodyMediaType, Request.Options reqOptions,
	    RestResponseDecoder<T, String> rrd, RestResponseDecodeStrategy strategy) throws RestClientException,
	    NoMappingModelFoundException, MappingException, ServerErrorResponseException {

	  RestMessage<String> msg = doRequest(Request.RestMethod.POST, URL, headers, null, body, bodyMediaType, reqOptions);
	  // System.out.println(msg.getBody().toString());
	  return rrd.decode(msg, strategy);
	}

	@Override
	public RestMessage<String> postRequest(String URL, MultivaluedMap<String, Object> headers, GenericEntity<?> body,
	    MediaType bodyMediaType, Request.Options reqOptions)
	    throws RestClientException {

	  return doRequest(Request.RestMethod.POST, URL, headers, null, body, bodyMediaType, reqOptions);
	}
	
	/***************************** PUT Requests ******************************/

	@Override
	public <T extends BaseRestResponseResult<String>> T putRequest(String URL, MultivaluedMap<String, Object> headers,
			GenericEntity<?> body, MediaType bodyMediaType,
			RestResponseDecoder<T, String> rrd, RestResponseDecodeStrategy strategy)
			throws RestClientException, NoMappingModelFoundException,
			MappingException, ServerErrorResponseException {

		return putRequest(URL, headers, body, bodyMediaType, null, rrd,
				strategy);
	}

	@Override
	public RestMessage<String> putRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<?> body,
			MediaType bodyMediaType) throws RestClientException {

		return putRequest(URL, headers, body, bodyMediaType, null);
	}

	@Override
	public <T extends BaseRestResponseResult<String>> T putRequest(String URL, MultivaluedMap<String, Object> headers, GenericEntity<?> body,
	    MediaType bodyMediaType, Request.Options reqOptions,
	    RestResponseDecoder<T, String> rrd, RestResponseDecodeStrategy strategy) throws RestClientException,
	    NoMappingModelFoundException, MappingException, ServerErrorResponseException {

	  RestMessage<String> msg = doRequest(Request.RestMethod.PUT, URL, headers, null, body, bodyMediaType, reqOptions);
	  return rrd.decode(msg, strategy);
	}

	@Override
	public RestMessage<String> putRequest(String URL, MultivaluedMap<String, Object> headers, GenericEntity<?> body,
	    MediaType bodyMediaType, Request.Options reqOptions)
	    throws RestClientException {

	  return doRequest(Request.RestMethod.PUT, URL, headers, null, body, bodyMediaType, reqOptions);
	}
	
	/***************************** DELETE Requests ******************************/

	@Override
	public <T extends BaseRestResponseResult<String>> T deleteRequest(String URL,
			MultivaluedMap<String, Object> headers, RestResponseDecoder<T, String> rrd,
			RestResponseDecodeStrategy strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException,
			ServerErrorResponseException {

		return deleteRequest(URL, headers, null, rrd,
				strategy);
	}

	@Override
	public RestMessage<String> deleteRequest(String URL,
			MultivaluedMap<String, Object> headers) throws RestClientException {

		return deleteRequest(URL, headers, null);
	}

  @Override
  public <T extends BaseRestResponseResult<String>> T deleteRequest(String URL, MultivaluedMap<String, Object> headers, Request.Options reqOptions,
      RestResponseDecoder<T, String> rrd, RestResponseDecodeStrategy strategy) throws RestClientException,
      NoMappingModelFoundException, MappingException, ServerErrorResponseException {
  
    RestMessage<String> msg = doRequest(Request.RestMethod.DELETE, URL, headers, null, null, null, reqOptions);
  
    return rrd.decode(msg, strategy);
  }
  
  @Override
  public RestMessage<String> deleteRequest(String URL, MultivaluedMap<String, Object> headers, 
      Request.Options reqOptions) throws RestClientException {
  
    return doRequest(Request.RestMethod.DELETE, URL, headers, null, null, null, reqOptions);
  }
  
  /***************************** Generic Requests ******************************/

  @Override
  public <T extends BaseRestResponseResult<String>> T doRequest(Request.RestMethod method, String URL,
      MultivaluedMap<String, Object> headers, MultivaluedMap<String, Object> queryParams, GenericEntity<?> body,
      MediaType bodyMediaType, Request.Options reqOptions,
      RestResponseDecoder<T, String> rrd, RestResponseDecodeStrategy strategy) throws RestClientException,
      NoMappingModelFoundException, MappingException, ServerErrorResponseException {

    RestMessage<String> msg = doRequest(method, URL, headers, queryParams, body, bodyMediaType, reqOptions);
    return rrd.decode(msg, strategy);
  }

  @Override
  public RestMessage<String> doRequest(Request.RestMethod method, String URL, MultivaluedMap<String, Object> headers,
      MultivaluedMap<String, Object> queryParams, GenericEntity<?> body, MediaType bodyMediaType,
      Request.Options reqOptions) throws RestClientException {

    return doRequest(method, URL, headers, queryParams, body, bodyMediaType, reqOptions, String.class);

  }

  @Override
  public <R> RestMessage<R> doRequest(Request.RestMethod method, String URL,
      MultivaluedMap<String, Object> headers, MultivaluedMap<String, Object> queryParams, GenericEntity<?> body,
      MediaType bodyMediaType, Request.Options reqOptions,
      Class<R> entityClass) throws RestClientException {
    
    Request request = new Request(method, URL);
    request.setHeaders(headers);
    request.setQueryParams(queryParams);
    request.setBody(body);
    request.setBodyMediaType(bodyMediaType);
    request.setReqOptions(reqOptions);
    
    return doRequest(request, entityClass);
  }
  
}
