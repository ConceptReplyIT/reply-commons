package it.reply.utils.web.ws.rest.restclient;

import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.RestMessage;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecodeStrategy;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecoder;
import it.reply.utils.web.ws.rest.apiencoding.decode.SimpleRestResponseDecoder;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

import java.io.Serializable;
import java.util.Objects;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class Request<T> implements Serializable {
  
  private static final long serialVersionUID = -7369063656963527676L;
  
  public enum RestMethod {
    GET, POST, PUT, DELETE, HEAD;
  }
  
  public static class Proxy implements Serializable {

    private static final long serialVersionUID = -2973674497161153027L;
    
    private String hostname;
    private int port;
    private String protocol;

    public Proxy(String hostname, int port, String protocol) {
      this.hostname = hostname;
      this.port = port;
      this.protocol = protocol;
    }

    public String getHostname() {
      return hostname;
    }

    public void setHostname(String hostname) {
      this.hostname = hostname;
    }

    public int getPort() {
      return port;
    }

    public void setPort(int port) {
      this.port = port;
    }

    public String getProtocol() {
      return protocol;
    }

    public void setProtocol(String protocol) {
      this.protocol = protocol;
    }
  }

  public static class Options implements Serializable {

    private static final long serialVersionUID = 2597934364446936039L;
    
    private int timeout;
    private Proxy proxy;

    // TODO Other useful fields like HTTPS
    // Authentication/Keystore/Truststore etc
    public int getTimeout() {
      return timeout;
    }

    public void setTimeout(int timeout) {
      this.timeout = timeout;
    }

    public Proxy getProxy() {
      return proxy;
    }

    public void setProxy(Proxy proxy) {
      this.proxy = proxy;
    }
  }
  
  private RestMethod method;
  private String URL;
  private MultivaluedMap<String, Object> headers;
  private MultivaluedMap<String, Object> queryParams; 
  private GenericEntity<T> body;
  private MediaType bodyMediaType;
  private Request.Options reqOptions;
  
  public Request(RestMethod method, String url) {
    setMethod(method);
    setURL(url);
  }
  
  public Request(RequestBuilder<T> builder, RestMethod method) {
    setMethod(method);
    setURL(builder.getUrl());
    setHeaders(builder.getHeaders());
    setQueryParams(builder.getQueryParams());
    Entity<GenericEntity<T>> body = builder.getBody();
    if (body != null) {
      setBody(body.getEntity());
      setBodyMediaType(body.getMediaType());
    }
    setReqOptions(builder.getReqOptions());
  }
  
  public RestMethod getMethod() {
    return method;
  }
  public String getURL() {
    return URL;
  }
  public MultivaluedMap<String, Object> getHeaders() {
    return headers;
  }
  public MultivaluedMap<String, Object> getQueryParams() {
    return queryParams;
  }
  public GenericEntity<T> getBody() {
    return body;
  }
  public MediaType getBodyMediaType() {
    return bodyMediaType;
  }
  public Request.Options getReqOptions() {
    return reqOptions;
  }
  public void setMethod(RestMethod method) {
    Objects.requireNonNull(method, "method must not be null");
    this.method = method;
  }
  public void setURL(String url) {
    Objects.requireNonNull(url, "url must not be null");
    URL = url;
  }
  public void setHeaders(MultivaluedMap<String, Object> headers) {
    this.headers = headers;
  }
  public void setQueryParams(MultivaluedMap<String, Object> queryParams) {
    this.queryParams = queryParams;
  }
  public void setBody(GenericEntity<T> body) {
    this.body = body;
  }
  public void setBodyMediaType(MediaType bodyMediaType) {
    this.bodyMediaType = bodyMediaType;
  }
  public void setReqOptions(Request.Options reqOptions) {
    this.reqOptions = reqOptions;
  }
  
  public static class RequestBuilder<T> {
    private RestClient restClient;
    private String url;
    private MultivaluedMap<String, Object> headers;
    private MultivaluedMap<String, Object> queryParams; 
    private Entity<GenericEntity<T>> body;
    private Request.Options reqOptions;
    
    public RequestBuilder(RestClient restClient, String url) {
      Objects.requireNonNull(restClient, "restClient must not be null");
      Objects.requireNonNull(url, "url must not be null");
      this.restClient = restClient;
      this.url = url;
    }

    public RestClient getRestClient() {
      return restClient;
    }

    public String getUrl() {
      return url;
    }

    public MultivaluedMap<String, Object> getHeaders() {
      return headers;
    }

    public MultivaluedMap<String, Object> getQueryParams() {
      return queryParams;
    }

    public Entity<GenericEntity<T>> getBody() {
      return body;
    }

    public Request.Options getReqOptions() {
      return reqOptions;
    }

    public RequestBuilder<T> withHeaders(MultivaluedMap<String, Object> headers) {
      this.headers = headers;
      return this;
    }
    
    public RequestBuilder<T> withHeader(String key, Object value) {
      if (headers == null) {
        headers = new MultivaluedHashMap<>();
      }
      headers.add(key, value);
      return this;
    }
    
    public RequestBuilder<T> withQueryParams(MultivaluedMap<String, Object> queryParams) {
      this.queryParams = queryParams;
      return this;
    }

    public RequestBuilder<T> withQueryParam(String key, Object value) {
      if (queryParams == null) {
        queryParams = new MultivaluedHashMap<>();
      }
      queryParams.add(key, value);
      return this;
    }
    
    public RequestBuilder<T> withBody(Entity<GenericEntity<T>> body) {
      this.body = body;
      return this;
    }

    public RequestBuilder<T> withReqOptions(Request.Options reqOptions) {
      this.reqOptions = reqOptions;
      return this;
    }
    
    public <R> RestMessage<R> get(Class<R> entityClass) throws RestClientException {
      return execute(RestMethod.GET, entityClass);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<E, String>, E> RestResponseResultType get(RestResponseDecoder<RestResponseResultType, E, String> rrd,
        RestResponseDecodeStrategy strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return execute(RestMethod.GET, rrd, strategy);
    }
    
    public <E> BaseRestResponseResult<E, String> get(SimpleRestResponseDecoder<E> rrd)
        throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return execute(RestMethod.GET, rrd);
    }
    
    public <R> RestMessage<R> post(Class<R> entityClass) throws RestClientException {
      return execute(RestMethod.POST, entityClass);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<E, String>, E> RestResponseResultType post(RestResponseDecoder<RestResponseResultType, E, String> rrd,
        RestResponseDecodeStrategy strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return execute(RestMethod.POST, rrd, strategy);
    }
    
    public <E> BaseRestResponseResult<E, String> post(SimpleRestResponseDecoder<E> rrd)
        throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return execute(RestMethod.POST, rrd);
    }
    
    public <R> RestMessage<R> put(Class<R> entityClass) throws RestClientException {
      return execute(RestMethod.PUT, entityClass);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<E, String>, E> RestResponseResultType put(RestResponseDecoder<RestResponseResultType, E, String> rrd,
        RestResponseDecodeStrategy strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return execute(RestMethod.PUT, rrd, strategy);
    }
    
    public <E> BaseRestResponseResult<E, String> put(SimpleRestResponseDecoder<E> rrd)
        throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return execute(RestMethod.PUT, rrd);
    }
    
    public RestMessage<Void> head() throws RestClientException {
      return execute(RestMethod.HEAD, Void.class);
    }
    
    public <R> RestMessage<R> delete(Class<R> entityClass) throws RestClientException {
      return execute(RestMethod.DELETE, entityClass);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<E, String>, E> RestResponseResultType delete(RestResponseDecoder<RestResponseResultType, E, String> rrd,
        RestResponseDecodeStrategy strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return execute(RestMethod.DELETE, rrd, strategy);
    }
    
    public <E> BaseRestResponseResult<E, String> delete(SimpleRestResponseDecoder<E> rrd)
        throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return execute(RestMethod.DELETE, rrd);
    }
    
    public <R> RestMessage<R> execute(RestMethod restMethod, Class<R> entityClass) throws RestClientException {
      return getRestClient().doRequest(new Request<T>(this, restMethod), entityClass);
    }
    
    public <E> BaseRestResponseResult<E, String> execute(RestMethod restMethod, RestResponseDecoder<BaseRestResponseResult<E, String>, E, String> rrd)
        throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return execute(restMethod, rrd, null);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<E, String>, E> RestResponseResultType execute(RestMethod restMethod, RestResponseDecoder<RestResponseResultType, E, String> rrd,
        RestResponseDecodeStrategy strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return getRestClient().doRequest(new Request<T>(this, restMethod), rrd, strategy);
    }
  }
}
