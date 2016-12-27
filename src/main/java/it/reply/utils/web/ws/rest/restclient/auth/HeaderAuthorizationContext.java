package it.reply.utils.web.ws.rest.restclient.auth;

import it.reply.utils.web.ws.rest.restclient.Request;

import java.util.Objects;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

/**
 * AuthorizationContext using a generic HTTP header as authentication method
 *
 */
public class HeaderAuthorizationContext implements AuthorizationContext {

  private static final long serialVersionUID = -640539787204503475L;
  
  protected String headerKey;
  protected String headerValue;

  public HeaderAuthorizationContext(String headerKey, String headerValue) {
    setHeaderKey(headerKey);
    setHeaderValue(headerValue);
  }

  /**
   * Get the key of the HTTP header
   * 
   * @return the key of the HTTP header
   */
  public String getHeaderKey() {
    return headerKey;
  }

  /**
   * Get the value of the HTTP header
   * 
   * @return the value of the HTTP header
   */
  public String getHeaderValue() {
    return headerValue;
  }

  /**
   * Set the key of the HTTP header
   * 
   * @param headerKey
   *          the key of the HTTP header
   */
  protected void setHeaderKey(String headerKey) {
    Objects.requireNonNull(headerKey, "headerKey must not be null");
    this.headerKey = headerKey;
  }

  /**
   * Set the value of the HTTP header
   * 
   * @param headerValue
   *          the value of the HTTP header
   */
  public void setHeaderValue(String headerValue) {
    Objects.requireNonNull(headerKey, "headerValue must not be null");
    this.headerValue = headerValue;
  }

  @Override
  public void intercept(Request<?> request) {
    MultivaluedMap<String, Object> headers = request.getHeaders();
    if (headers == null) {
      headers = new MultivaluedHashMap<>();
      request.setHeaders(headers);
    }
    headers.add(getHeaderKey(), getHeaderValue());
  }

}
