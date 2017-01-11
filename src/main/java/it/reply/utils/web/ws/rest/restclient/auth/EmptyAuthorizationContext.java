package it.reply.utils.web.ws.rest.restclient.auth;

import it.reply.utils.web.ws.rest.restclient.Request;

public final class EmptyAuthorizationContext implements AuthorizationContext {

  private static final long serialVersionUID = 3504272089163689261L;

  @Override
  public void intercept(Request<?> request) {
    // DO NOTHING
  }

}
