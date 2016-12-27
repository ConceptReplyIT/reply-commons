package it.reply.utils.web.ws.rest.restclient;

/**
 * Interceptor that allows to execute some logic on a {@link Request} before its actual execution
 *
 */
public interface RequestInterceptor {

  /**
   * Execute the interceptor logic
   * 
   * @param request
   *          the intercepted request
   */
  public void intercept(Request<?> request);
  
}
