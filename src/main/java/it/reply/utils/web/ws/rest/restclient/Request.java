package it.reply.utils.web.ws.rest.restclient;

import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.RestMessage;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecodeStrategy;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecoder;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

import java.io.Serializable;
import java.util.Objects;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

public class Request implements Serializable {
  
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
  private GenericEntity<?> body;
  private MediaType bodyMediaType;
  private Request.Options reqOptions;
  
  public Request(RestMethod method, String url) {
    setMethod(method);
    setURL(url);
  }
  
  public Request(RequestBuilder builder, RestMethod method) {
    setMethod(method);
    setURL(builder.getUrl());
    setHeaders(builder.getHeaders());
    setQueryParams(builder.getQueryParams());
    Entity<GenericEntity<?>> body = builder.getBody();
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
  public GenericEntity<?> getBody() {
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
  public void setBody(GenericEntity<?> body) {
    this.body = body;
  }
  public void setBodyMediaType(MediaType bodyMediaType) {
    this.bodyMediaType = bodyMediaType;
  }
  public void setReqOptions(Request.Options reqOptions) {
    this.reqOptions = reqOptions;
  }
  
  public static class RequestBuilder {
    private RestClient restClient;
    private String url;
    private MultivaluedMap<String, Object> headers;
    private MultivaluedMap<String, Object> queryParams; 
    private Entity<GenericEntity<?>> body;
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

    public Entity<GenericEntity<?>> getBody() {
      return body;
    }

    public Request.Options getReqOptions() {
      return reqOptions;
    }

    public RequestBuilder withHeaders(MultivaluedMap<String, Object> headers) {
      this.headers = headers;
      return this;
    }

    public RequestBuilder withQueryParams(MultivaluedMap<String, Object> queryParams) {
      this.queryParams = queryParams;
      return this;
    }

    public RequestBuilder withBody(Entity<GenericEntity<?>> body) {
      this.body = body;
      return this;
    }

    public RequestBuilder withReqOptions(Request.Options reqOptions) {
      this.reqOptions = reqOptions;
      return this;
    }
    
    public <R> RestMessage<R> get(Class<R> entityClass) throws RestClientException {
      return getRestClient().doRequest(new Request(this, RestMethod.GET), entityClass);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<String>> RestResponseResultType get(RestResponseDecoder<RestResponseResultType, String> rrd,
        RestResponseDecodeStrategy strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return getRestClient().doRequest(new Request(this, RestMethod.GET), rrd, strategy);
    }
    
    public <R> RestMessage<R> post(Class<R> entityClass) throws RestClientException {
      return getRestClient().doRequest(new Request(this, RestMethod.POST), entityClass);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<String>> RestResponseResultType post(RestResponseDecoder<RestResponseResultType, String> rrd,
        RestResponseDecodeStrategy strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return getRestClient().doRequest(new Request(this, RestMethod.POST), rrd, strategy);
    }
    
    public <R> RestMessage<R> put(Class<R> entityClass) throws RestClientException {
      return getRestClient().doRequest(new Request(this, RestMethod.PUT), entityClass);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<String>> RestResponseResultType put(RestResponseDecoder<RestResponseResultType, String> rrd,
        RestResponseDecodeStrategy strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return getRestClient().doRequest(new Request(this, RestMethod.PUT), rrd, strategy);
    }
    
    public RestMessage<Void> head() throws RestClientException {
      return getRestClient().doRequest(new Request(this, RestMethod.HEAD), Void.class);
    }
    
    public <R> RestMessage<R> delete(Class<R> entityClass) throws RestClientException {
      return getRestClient().doRequest(new Request(this, RestMethod.DELETE), entityClass);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<String>> RestResponseResultType delete(RestResponseDecoder<RestResponseResultType, String> rrd,
        RestResponseDecodeStrategy strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return getRestClient().doRequest(new Request(this, RestMethod.DELETE), rrd, strategy);
    }
    
    public <R> RestMessage<R> execute(RestMethod restMethod, Class<R> entityClass) throws RestClientException {
      return getRestClient().doRequest(new Request(this, restMethod), entityClass);
    }
    
    public <RestResponseResultType extends BaseRestResponseResult<String>> RestResponseResultType execute(RestMethod restMethod, RestResponseDecoder<RestResponseResultType, String> rrd,
        RestResponseDecodeStrategy strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
      return getRestClient().doRequest(new Request(this, restMethod), rrd, strategy);
    }
  }
}
