package it.reply.utils.web.ws.rest.restclient.auth;

import it.reply.utils.web.ws.rest.restclient.Request;
import it.reply.utils.web.ws.rest.restclient.RequestInterceptor;

import java.io.Serializable;

/**
 * {@link RequestInterceptor} that holds an authorization context and allows its usage during a
 * {@link it.reply.utils.web.ws.rest.restclient.RestClient RestClient} {@link it.reply.utils.web.ws.rest.restclient.Request Request}
 *
 */
public interface AuthorizationContext extends RequestInterceptor, Serializable {

  /** 
   * Apply the authorization context on the request
   */
  @Override
  void intercept(Request<?> request);
  
}
