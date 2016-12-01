package it.reply.utils.web.ws.rest.restclient;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.RestMessage;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecodeStrategy;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecoder;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class Request<T> implements Serializable {
  
  private static final long serialVersionUID = -7369063656963527676L;
  
  public enum HttpMethod {
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
  
  private HttpMethod method;
  private String URL;
  private MultivaluedMap<String, Object> headers;
  private MultivaluedMap<String, Object> queryParams; 
  private GenericEntity<T> body;
  private MediaType bodyMediaType;
  private Request.Options reqOptions;
  
  public Request(HttpMethod method, String url) {
    setMethod(method);
    setURL(url);
  }
  
  public Request(RequestBuilder<?> builder, HttpMethod method) {
    setMethod(method);
    setURL(builder.getUrl());
    setHeaders(builder.getHeaders());
    setQueryParams(builder.getQueryParams());
    setReqOptions(builder.getReqOptions());
  }
  
  public Request(RequestWithBodyBuilder<T> builder, HttpMethod method) {
    this((RequestBuilder<?>)builder, method);
    Entity<GenericEntity<T>> body = builder.getBody();
    if (body != null) {
      setBody(body.getEntity());
      setBodyMediaType(body.getMediaType());
    }
  }
  
  public HttpMethod getMethod() {
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
  public void setMethod(HttpMethod method) {
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
  
  public static class RequestBuilder<P extends RequestBuilder<P>> {
    private RestClient restClient;
    private String url;
    private MultivaluedMap<String, Object> headers;
    private MultivaluedMap<String, Object> queryParams; 
    private Request.Options reqOptions;
    
    public RequestBuilder(RestClient restClient, String url) {
      Objects.requireNonNull(restClient, "restClient must not be null");
      Objects.requireNonNull(url, "url must not be null");
      this.restClient = restClient;
      this.url = url;
    }
    
    public RequestBuilder(RequestBuilder<?> otherBuilder) {
      this.restClient = otherBuilder.restClient;
      this.url = otherBuilder.url;
      this.headers = otherBuilder.headers;
      this.queryParams = otherBuilder.queryParams; 
      this.reqOptions = otherBuilder.reqOptions;
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

    public Request.Options getReqOptions() {
      return reqOptions;
    }

    @SuppressWarnings("unchecked")
    public P withHeaders(MultivaluedMap<String, Object> headers) {
      this.headers = headers;
      return (P) this;
    }
    
    @SuppressWarnings("unchecked")
    public P withHeader(String key, Object value) {
      if (headers == null) {
        headers = new MultivaluedHashMap<>();
      }
      headers.add(key, value);
      return (P) this;
    }
    
    @SuppressWarnings("unchecked")
    public P withQueryParams(MultivaluedMap<String, Object> queryParams) {
      this.queryParams = queryParams;
      return (P) this;
    }

    @SuppressWarnings("unchecked")
    public P withQueryParam(String key, Object value) {
      if (queryParams == null) {
        queryParams = new MultivaluedHashMap<>();
      }
      queryParams.add(key, value);
      return (P) this;
    }
    
    public <T> RequestWithBodyBuilder<T> withBody(Entity<GenericEntity<T>> body) {
      return new RequestWithBodyBuilder<T>(this, body);
    }
    
    public <T> RequestWithBodyBuilder<T> withBody(GenericEntity<T> body, MediaType mediaType) {
      return this.withBody(Entity.entity(body, mediaType));
    }
    
    public RequestWithBodyBuilder<String> withJsonBody(Object body) throws JsonParseException, JsonMappingException, IOException {
      GenericEntity<String> serializedBody = new RestClientHelper.JsonEntityBuilder().build(body);
      return this.withBody(Entity.json(serializedBody));
    }

    @SuppressWarnings("unchecked")
    public P withReqOptions(Request.Options reqOptions) {
      this.reqOptions = reqOptions;
      return (P) this;
    }
    
    public <R> RestMessage<R> get(Class<R> entityClass) throws RestClientException {
      return execute(HttpMethod.GET, entityClass);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<E, R>, E, R> RestResponseResultType get(RestResponseDecoder<RestResponseResultType, E, R> rrd,
        RestResponseDecodeStrategy<R> strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return execute(HttpMethod.GET, rrd, strategy);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<E, R>, E, R> RestResponseResultType get(RestResponseDecoder<RestResponseResultType, E, R> rrd)
        throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return execute(HttpMethod.GET, rrd);
    }
    
    public <R> RestMessage<R> post(Class<R> entityClass) throws RestClientException {
      return execute(HttpMethod.POST, entityClass);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<E, R>, E, R> RestResponseResultType post(RestResponseDecoder<RestResponseResultType, E, R> rrd,
        RestResponseDecodeStrategy<R> strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return execute(HttpMethod.POST, rrd, strategy);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<E, R>, E, R> RestResponseResultType post(RestResponseDecoder<RestResponseResultType, E, R> rrd)
        throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return post(rrd, null);
    }
    
    public <R> RestMessage<R> put(Class<R> entityClass) throws RestClientException {
      return execute(HttpMethod.PUT, entityClass);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<E, R>, E, R> RestResponseResultType put(RestResponseDecoder<RestResponseResultType, E, R> rrd,
        RestResponseDecodeStrategy<R> strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return execute(HttpMethod.PUT, rrd, strategy);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<E, R>, E, R> RestResponseResultType put(RestResponseDecoder<RestResponseResultType, E, R> rrd)
        throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return put(rrd, null);
    }
    
    public RestMessage<Void> head() throws RestClientException {
      return execute(HttpMethod.HEAD, Void.class);
    }
    
    public <R> RestMessage<R> delete(Class<R> entityClass) throws RestClientException {
      return execute(HttpMethod.DELETE, entityClass);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<E, R>, E, R> RestResponseResultType delete(RestResponseDecoder<RestResponseResultType, E, R> rrd,
        RestResponseDecodeStrategy<R> strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return execute(HttpMethod.DELETE, rrd, strategy);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<E, R>, E, R> RestResponseResultType delete(RestResponseDecoder<RestResponseResultType, E, R> rrd)
        throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return delete(rrd, null);
    }
    
    public <R> RestMessage<R> execute(HttpMethod restMethod, Class<R> entityClass) throws RestClientException {
      return getRestClient().doRequest(generateRequest(restMethod), entityClass);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<E, R>, E, R> RestResponseResultType execute(HttpMethod restMethod, RestResponseDecoder<RestResponseResultType, E, R> rrd)
        throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return execute(restMethod, rrd, null);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<E, R>, E, R> RestResponseResultType execute(HttpMethod restMethod, RestResponseDecoder<RestResponseResultType, E, R> rrd,
        RestResponseDecodeStrategy<R> strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return getRestClient().doRequest(generateRequest(restMethod), rrd, strategy);
    }
    
    protected Request<?> generateRequest(HttpMethod restMethod) {
      return new Request<Void>(this, restMethod); 
    }
  }
  
  public static class RequestWithBodyBuilder<T> extends RequestBuilder<RequestWithBodyBuilder<T>> {
    
    private Entity<GenericEntity<T>> body;
    
    public RequestWithBodyBuilder(RestClient restClient, String url, Entity<GenericEntity<T>> body) {
      super(restClient, url);
      this.body = body;
    }
    
    public RequestWithBodyBuilder(RequestBuilder<?> otherBuilder, Entity<GenericEntity<T>> body) {
      super(otherBuilder);
      this.body = body;
    }
    
    public RequestWithBodyBuilder(RequestWithBodyBuilder<T> otherBuilder) {
      super(otherBuilder);
      this.body = otherBuilder.body;
    }
    
    public Entity<GenericEntity<T>> getBody() {
      return body;
    }
       
    @Override
    protected Request<?> generateRequest(HttpMethod restMethod) {
      return new Request<T>(this, restMethod); 
    }
  }
}
