package it.reply.utils.web.ws.rest.restclient.auth;

import it.reply.utils.web.ws.rest.restclient.Request;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class Oauth2AuthorizationContext implements AuthorizationContext {

  private static final long serialVersionUID = -6960501960849369263L;

  public enum GRANT_TYPE {
    AUTHORIZATION_CODE("authorization_code"),
    CLIENT_CREDENTIALS("client_credentials"),
    RESOURCE_OWNER_PASSWORD("password"),
    IMPLICIT("implicit");
    
    private String grantType;
    
    public String grantType() {
      return grantType;
    }
    
    GRANT_TYPE(String grantType) {
      this.grantType = grantType;
    }
  }
  
  // Grant type for refreshing access token, must be kept aside from the others
  public final static String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
  
  public enum TOKEN_TYPE {
    BEARER("bearer");
    
    private String tokenType;
    
    public String getTokenType() {
      return tokenType;
    }
    
    TOKEN_TYPE(String tokenType) {
      this.tokenType = tokenType;
    }
  }
  
  public Oauth2AuthorizationContext(GRANT_TYPE grantType) {
    setGrantType(grantType);
  }

  private GRANT_TYPE grantType;
  
  private String clientId;
  
  private String clientSecret;
  
  private String accessToken;
  
  private TOKEN_TYPE tokenType = TOKEN_TYPE.BEARER;
  
  private Set<String> scopes = new HashSet<>();
  
  private String refreshToken;
  
  private String tokenEndpoint;
  
  public GRANT_TYPE getGrantType() {
    return grantType;
  }

  public String getClientId() {
    return clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public TOKEN_TYPE getTokenType() {
    return tokenType;
  }
  
  public Set<String> getScopes() {
    return scopes;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public String getTokenEndpoint() {
    return tokenEndpoint;
  }

  private void setGrantType(GRANT_TYPE grantType) {
    Objects.requireNonNull(grantType, "grantType must not be null");
    this.grantType = grantType;
  }
  
  public void setClientId(String clientId) {
    Objects.requireNonNull(clientId, "clientId must not be null");
    this.clientId = clientId;
  }

  public void setClientSecret(String clientSecret) {
    Objects.requireNonNull(clientSecret, "clientSecret must not be null");
    this.clientSecret = clientSecret;
  }

  public void setAccessToken(String accessToken) {
    Objects.requireNonNull(accessToken, "accessToken must not be null");
    this.accessToken = accessToken;
  }

  public void setTokenType(TOKEN_TYPE tokenType) {
    Objects.requireNonNull(tokenType, "tokenType must not be null");
    this.tokenType = tokenType;
  }
  
  public void setScopes(Set<String> scopes) {
    Objects.requireNonNull(scopes, "scopes must not be null");
    this.scopes = scopes;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public void setTokenEndpoint(String tokenEndpoint) {
    this.tokenEndpoint = tokenEndpoint;
  }

  @Override
  public void intercept(Request<?> request) {
    MultivaluedMap<String, Object> headers = request.getHeaders();
    if (headers == null) {
      headers = new MultivaluedHashMap<>();
      request.setHeaders(headers);
    }
    request.getHeaders().add(HttpHeaders.AUTHORIZATION, String.format("%s %s", tokenType.getTokenType(), getAccessToken()));
  }


}