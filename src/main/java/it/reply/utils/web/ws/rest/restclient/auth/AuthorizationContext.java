package it.reply.utils.web.ws.rest.restclient.auth;

import it.reply.utils.web.ws.rest.restclient.RequestInterceptor;

import java.io.Serializable;

import javax.ws.rs.client.Invocation;

public interface AuthorizationContext extends RequestInterceptor, Serializable {
  
}
