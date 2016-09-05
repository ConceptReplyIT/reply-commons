package it.reply.utils.web.ws.rest.restclient;

import java.io.Serializable;
import java.util.Objects;

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
}
