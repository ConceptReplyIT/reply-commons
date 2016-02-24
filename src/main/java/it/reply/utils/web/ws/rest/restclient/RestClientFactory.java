package it.reply.utils.web.ws.rest.restclient;

import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;

/**
 * Factory interface to get {@link RestClient} implementations.
 * 
 * @author l.biava
 * 
 */
public abstract class RestClientFactory {

	public static final String BASE_REST_CLIENT_CRITERIA = "base";
	public static final String AUTHENTICATION_REST_CLIENT_CRITERIA = "auth";
	public static final String SECURE_REST_CLIENT_CRITERIA = "secure";
	
	public abstract  <T extends BaseRestResponseResult> RestClient<T> getRestClient(
			String criteria);

	public abstract <T extends BaseRestResponseResult> RestClient<T> getRestClient();
}
