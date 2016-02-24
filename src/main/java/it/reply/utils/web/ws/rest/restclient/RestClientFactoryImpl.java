package it.reply.utils.web.ws.rest.restclient;

import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;

/**
 * Factory to get {@link RestClient} implementations.
 * Currently only the default one is supported ({@link RestClientImpl}). 
 * @author l.biava
 *
 */
public class RestClientFactoryImpl extends RestClientFactory {
	
	public <T extends BaseRestResponseResult> RestClient<T> getRestClient(String criteria) {
		//TODO: future criteria implementation
		if(BASE_REST_CLIENT_CRITERIA.equals(criteria))
			return new RestClientImpl<T> ();
		else
			return getRestClient();
	}
	
	public <T extends BaseRestResponseResult> RestClient<T> getRestClient() {
		return new RestClientImpl<T> ();
	}
}
