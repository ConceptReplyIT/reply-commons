package it.reply.utils.web.ws.rest.restclient;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.google.common.collect.Lists;

import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.RestMessage;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecodeStrategy;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecoder;
import it.reply.utils.web.ws.rest.restclient.Request.RequestBuilder;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

import java.util.List;
import java.util.Objects;

public abstract class AbstractRestClient
		implements RestClient {

	protected int defaultTimeout = 60000; // in ms
	
	protected List<RequestInterceptor> interceptors = Lists.newArrayList();

	@Override
	public int getDefaultTimeout() {
		return this.defaultTimeout;
	}

	@Override
	public void setDefaultTimeout(int defaultTimeout) {
		this.defaultTimeout = defaultTimeout;
	}

	@Override
	public void setRequestInserceptors(List<RequestInterceptor> interceptors) {
	  Objects.requireNonNull(interceptors, "interceptors must not be null");
	  this.interceptors = interceptors;
	}
	
	@Override
	public List<RequestInterceptor> getRequestInserceptors() {
    return interceptors;
	}
	
	@Override
	public RequestBuilder<?> request(String url) {
	  return new RequestBuilder<>(this, url); 
	}
	
	/***************************** GET Requests ******************************/

	@Override
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, O> RestResponseResultType getRequest(String URL, MultivaluedMap<String, Object> headers,
			RestResponseDecoder<RestResponseResultType, R, O> rrd, RestResponseDecodeStrategy<O> strategy)
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
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, O> RestResponseResultType getRequest(String URL, MultivaluedMap<String, Object> headers,
	    Request.Options reqOptions, RestResponseDecoder<RestResponseResultType, R, O> rrd,
	    RestResponseDecodeStrategy<O> strategy) throws RestClientException, NoMappingModelFoundException,
	    MappingException, ServerErrorResponseException {

	  RestMessage<O> msg = doRequest(Request.RestMethod.GET, URL, headers, null, null, null, reqOptions, rrd.getDecodableClass());

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
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, B, O> RestResponseResultType postRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<B> body,
			MediaType bodyMediaType, RestResponseDecoder<RestResponseResultType, R, O> rrd,
			RestResponseDecodeStrategy<O> strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException,
			ServerErrorResponseException {

		return postRequest(URL, headers, body, bodyMediaType, null, rrd,
				strategy);

	}

	@Override
	public <T> RestMessage<String> postRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<T> body,
			MediaType bodyMediaType) throws RestClientException {

		return postRequest(URL, headers, body, bodyMediaType, null);
	}

	@Override
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, B, O> RestResponseResultType postRequest(String URL, MultivaluedMap<String, Object> headers, GenericEntity<B> body,
	    MediaType bodyMediaType, Request.Options reqOptions,
	    RestResponseDecoder<RestResponseResultType, R, O> rrd, RestResponseDecodeStrategy<O> strategy) throws RestClientException,
	    NoMappingModelFoundException, MappingException, ServerErrorResponseException {

	  RestMessage<O> msg = doRequest(Request.RestMethod.POST, URL, headers, null, body, bodyMediaType, reqOptions, rrd.getDecodableClass());
	  // System.out.println(msg.getBody().toString());
	  return rrd.decode(msg, strategy);
	}

	@Override
	public <T> RestMessage<String> postRequest(String URL, MultivaluedMap<String, Object> headers, GenericEntity<T> body,
	    MediaType bodyMediaType, Request.Options reqOptions)
	    throws RestClientException {

	  return doRequest(Request.RestMethod.POST, URL, headers, null, body, bodyMediaType, reqOptions);
	}
	
	/***************************** PUT Requests ******************************/

	@Override
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, B, O> RestResponseResultType putRequest(String URL, MultivaluedMap<String, Object> headers,
			GenericEntity<B> body, MediaType bodyMediaType,
			RestResponseDecoder<RestResponseResultType, R, O> rrd, RestResponseDecodeStrategy<O> strategy)
			throws RestClientException, NoMappingModelFoundException,
			MappingException, ServerErrorResponseException {

		return putRequest(URL, headers, body, bodyMediaType, null, rrd,
				strategy);
	}

	@Override
	public <T> RestMessage<String> putRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<T> body,
			MediaType bodyMediaType) throws RestClientException {

		return putRequest(URL, headers, body, bodyMediaType, null);
	}

	@Override
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, B, O> RestResponseResultType putRequest(String URL, MultivaluedMap<String, Object> headers, GenericEntity<B> body,
	    MediaType bodyMediaType, Request.Options reqOptions,
	    RestResponseDecoder<RestResponseResultType, R, O> rrd, RestResponseDecodeStrategy<O> strategy) throws RestClientException,
	    NoMappingModelFoundException, MappingException, ServerErrorResponseException {

	  RestMessage<O> msg = doRequest(Request.RestMethod.PUT, URL, headers, null, body, bodyMediaType, reqOptions, rrd.getDecodableClass());
	  return rrd.decode(msg, strategy);
	}

	@Override
	public <T> RestMessage<String> putRequest(String URL, MultivaluedMap<String, Object> headers, GenericEntity<T> body,
	    MediaType bodyMediaType, Request.Options reqOptions)
	    throws RestClientException {

	  return doRequest(Request.RestMethod.PUT, URL, headers, null, body, bodyMediaType, reqOptions);
	}
	
	/***************************** DELETE Requests ******************************/

	@Override
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, O> RestResponseResultType deleteRequest(String URL,
			MultivaluedMap<String, Object> headers, RestResponseDecoder<RestResponseResultType, R, O> rrd,
			RestResponseDecodeStrategy<O> strategy) throws RestClientException,
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
  public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, O> RestResponseResultType deleteRequest(String URL, MultivaluedMap<String, Object> headers, Request.Options reqOptions,
      RestResponseDecoder<RestResponseResultType, R, O> rrd, RestResponseDecodeStrategy<O> strategy) throws RestClientException,
      NoMappingModelFoundException, MappingException, ServerErrorResponseException {
  
    RestMessage<O> msg = doRequest(Request.RestMethod.DELETE, URL, headers, null, null, null, reqOptions, rrd.getDecodableClass());
  
    return rrd.decode(msg, strategy);
  }
  
  @Override
  public RestMessage<String> deleteRequest(String URL, MultivaluedMap<String, Object> headers, 
      Request.Options reqOptions) throws RestClientException {
  
    return doRequest(Request.RestMethod.DELETE, URL, headers, null, null, null, reqOptions);
  }
  
  /***************************** Generic Requests ******************************/

  @Override
  public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, B, O> RestResponseResultType doRequest(Request.RestMethod method, String URL,
      MultivaluedMap<String, Object> headers, MultivaluedMap<String, Object> queryParams, GenericEntity<B> body,
      MediaType bodyMediaType, Request.Options reqOptions,
      RestResponseDecoder<RestResponseResultType, R, O> rrd, RestResponseDecodeStrategy<O> strategy) throws RestClientException,
      NoMappingModelFoundException, MappingException, ServerErrorResponseException {

    RestMessage<O> msg = doRequest(method, URL, headers, queryParams, body, bodyMediaType, reqOptions, rrd.getDecodableClass());
    return rrd.decode(msg, strategy);
  }

  @Override
  public <T> RestMessage<String> doRequest(Request.RestMethod method, String URL, MultivaluedMap<String, Object> headers,
      MultivaluedMap<String, Object> queryParams, GenericEntity<T> body, MediaType bodyMediaType,
      Request.Options reqOptions) throws RestClientException {

    return doRequest(method, URL, headers, queryParams, body, bodyMediaType, reqOptions, String.class);

  }

  @Override
  public <B, E> RestMessage<E> doRequest(Request.RestMethod method, String URL,
      MultivaluedMap<String, Object> headers, MultivaluedMap<String, Object> queryParams, GenericEntity<B> body,
      MediaType bodyMediaType, Request.Options reqOptions,
      Class<E> entityClass) throws RestClientException {
    
    Request<B> request = new Request<B>(method, URL);
    request.setHeaders(headers);
    request.setQueryParams(queryParams);
    request.setBody(body);
    request.setBodyMediaType(bodyMediaType);
    request.setReqOptions(reqOptions);
    
    return doRequest(request, entityClass);
  }
  
  @Override
  public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, B, O> RestResponseResultType doRequest(Request<B> request, RestResponseDecoder<RestResponseResultType, R, O> rrd,
      RestResponseDecodeStrategy<O> strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
    RestMessage<O> msg = doRequest(request, rrd.getDecodableClass());
    return rrd.decode(msg, strategy);
  }
}
