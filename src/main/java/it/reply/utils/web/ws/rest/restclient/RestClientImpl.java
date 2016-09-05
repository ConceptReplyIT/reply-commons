package it.reply.utils.web.ws.rest.restclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.RestMessage;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecodeStrategy;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecoder;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

public class RestClientImpl extends AbstractRestClient {

	/***************************** GET Requests ******************************/

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
	public RestMessage<Void> headRequest(String URL, MultivaluedMap<String, Object> headers,
	    Request.Options reqOptions) throws RestClientException {

		return doRequest(Request.RestMethod.HEAD, URL, headers, null, null, null, reqOptions, Void.class);
	}

	/***************************** POST Requests ******************************/

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

	// /***************************** PRIVATE methods
	// ******************************/

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
	  
	  return doRequest(request, reqOptions, entityClass);
	}

	@Override
  public <R> RestMessage<R> doRequest(Request request, Request.Options reqOptions,
      Class<R> entityClass) throws RestClientException {

    ResteasyClientBuilder cb = new ResteasyClientBuilder();

    // Socket Timeout
    if (reqOptions != null && reqOptions.getTimeout() > 0)
      cb.socketTimeout(reqOptions.getTimeout(), TimeUnit.MILLISECONDS);
    else
      cb.socketTimeout(this.defaultTimeout, TimeUnit.MILLISECONDS);

    // Proxy
    if (reqOptions != null && reqOptions.getProxy() != null) {
      cb.defaultProxy(reqOptions.getProxy().getHostname(), reqOptions.getProxy().getPort(), reqOptions.getProxy()
          .getProtocol());
    } else {
      if (System.getProperty("java.net.useSystemProxies") != null
          && System.getProperty("java.net.useSystemProxies").equals("true")) {
        try {
          URL url = new URL(request.getURL());
          if (!url.getAuthority().contains("localhost") && !url.getAuthority().contains("127.0.0.1")) {
            cb.defaultProxy("proxy.reply.it", 8080, "HTTP");
          }
        } catch (MalformedURLException e) {
          cb.defaultProxy("proxy.reply.it", 8080, "HTTP");
        }
        // TODO Fix using system properties
        // try {
        // List l=ProxySelector.getDefault().select(new
        // URI("http://foo/bar"));
        // for (Iterator iter = l.iterator(); iter.hasNext();) {
        // java.net.Proxy proxy = (java.net.Proxy) iter.next();
        // System.out.println("proxy hostname : " + proxy.type());
        //
        // InetSocketAddress addr = (InetSocketAddress) proxy.address();
        //
        // if (addr == null) {
        // System.out.println("No Proxy");
        // } else {
        // System.out.println("proxy hostname : " + addr.getHostName());
        // System.setProperty("http.proxyHost", addr.getHostName());
        // System.out.println("proxy port : " + addr.getPort());
        // System.setProperty("http.proxyPort",
        // Integer.toString(addr.getPort()));
        //
        // cb.defaultProxy(addr.getHostName(), addr.getPort());
        // }
        // }
        // } catch (URISyntaxException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
      }
    }
    Client client = null;
    try {
      client = cb.build();
      Response response = null;
      try {
        WebTarget target = client.target(request.getURL());
  
        // Add Query Params
        if (request.getQueryParams() != null) {
          for (MultivaluedMap.Entry<String, List<Object>> entry : request.getQueryParams().entrySet()) {
            Object[] params = null;
            if (entry.getValue() != null) {
              params = entry.getValue().toArray();
            }
            target.queryParam(entry.getKey(), params);
          }
        }
  
        Builder reqBuilder = target.request();
  
        if (request.getHeaders() != null) {
          reqBuilder = reqBuilder.headers(request.getHeaders());
        }
  
        switch (request.getMethod()) {
        case GET:
          if (request.getBody() == null)
            response = reqBuilder.get();
          else
            response = reqBuilder.method("GET", Entity.entity(request.getBody(), request.getBodyMediaType()));
          break;
        case HEAD:
          if (request.getBody() == null)
            response = reqBuilder.head();
          else
            response = reqBuilder.method("HEAD", Entity.entity(request.getBody(), request.getBodyMediaType()));
          break;
        case POST:
          if (request.getBody() == null) {
            response = reqBuilder.method("POST");
          } else
            response = reqBuilder.post(Entity.entity(request.getBody(), request.getBodyMediaType()));
          break;
        case PUT:
          if (request.getBody() == null) {
            response = reqBuilder.method("PUT");
          } else {
            response = reqBuilder.put(Entity.entity(request.getBody(), request.getBodyMediaType()));
          }
          break;
        case DELETE:
          if (request.getBody() == null)
            response = reqBuilder.delete();
          else
            response = reqBuilder.method("DELETE", Entity.entity(request.getBody(), request.getBodyMediaType()));
          break;
        }
  
        RestMessage<R> msg;
        try {
          msg = new RestMessage<R>(response.getHeaders(), response.readEntity(entityClass), response.getStatus());
        } catch (Exception e) {
          msg = new RestMessage<R>(response.getHeaders(), null, response.getStatus());
        }
  
        return msg;
  
      } catch (Exception e) {
        throw new RestClientException(e.getMessage(), e);
      } finally {
        if (response != null)
          response.close();
      }
    } finally {
      if (client != null) {
        client.close();
      }
    }
  }
}
