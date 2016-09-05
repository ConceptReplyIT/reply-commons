package it.reply.utils.web.ws.rest.restclient.auth;

import it.reply.utils.web.ws.rest.restclient.Request;

import org.jboss.resteasy.specimpl.MultivaluedMapImpl;

import java.util.Objects;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MultivaluedMap;

public class HeaderAuthorizationContext implements AuthorizationContext {

  private static final long serialVersionUID = -640539787204503475L;
  
  protected String headerKey;
  protected String headerValue;

  public HeaderAuthorizationContext(String headerKey, String headerValue) {
    setHeaderKey(headerKey);
    setHeaderValue(headerValue);
  }

  public String getHeaderKey() {
    return headerKey;
  }

  public String getHeaderValue() {
    return headerValue;
  }

  private void setHeaderKey(String headerKey) {
    Objects.requireNonNull(headerKey, "headerKey must not be null");
    this.headerKey = headerKey;
  }

  public void setHeaderValue(String headerValue) {
    Objects.requireNonNull(headerKey, "headerValue must not be null");
    this.headerValue = headerValue;
  }

  @Override
  public void intercept(Request request) {
    MultivaluedMap<String, Object> headers = request.getHeaders();
    if (headers == null) {
      headers = new MultivaluedMapImpl<>();
      request.setHeaders(headers);
    }
    headers.add(getHeaderKey(), getHeaderValue());
  }

}