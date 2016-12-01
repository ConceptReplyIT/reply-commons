package it.reply.utils.web.ws.rest.restclient.auth;

import com.google.common.io.BaseEncoding;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

import javax.ws.rs.core.HttpHeaders;

public class BasicAuthAuthorizationContext extends HeaderAuthorizationContext {
  
  private static final long serialVersionUID = 7320203981644117617L;
  
  private String username;
  private String password;

  public BasicAuthAuthorizationContext(String username, String password) {
    super(HttpHeaders.AUTHORIZATION, "");
    setUsername(username);
    setPassword(password);
  }
  
  private String generateHeaderValue(String username, String password){
    Objects.requireNonNull(username, "username must not be null");
    Objects.requireNonNull(password, "password must not be null");
    try {
      return BaseEncoding.base64().encode(String.format("%s:%s", username, password).getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      // Shouldn't be possible... regardless, re-throw as RuntimeException
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getHeaderValue() {
    return generateHeaderValue(getUsername(), getPassword());
  }

  @Override
  public void setHeaderValue(String headerValue) {
    throw new UnsupportedOperationException("Header value must be calculated from username and password");
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    Objects.requireNonNull(username, "username must not be null");
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    Objects.requireNonNull(password, "password must not be null");
    this.password = password;
  }
}
