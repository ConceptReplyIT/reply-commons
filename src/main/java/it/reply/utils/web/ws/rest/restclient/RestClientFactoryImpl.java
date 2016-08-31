package it.reply.utils.web.ws.rest.restclient;

/**
 * Factory to get {@link RestClient} implementations.
 * Currently only the default one is supported ({@link RestClientImpl}). 
 * @author l.biava
 *
 */
public class RestClientFactoryImpl extends RestClientFactory {
	
	public RestClient getRestClient(String criteria) {
		//TODO: future criteria implementation
		if(BASE_REST_CLIENT_CRITERIA.equals(criteria))
			return new RestClientImpl ();
		else
			return getRestClient();
	}
	
	public RestClient getRestClient() {
		return new RestClientImpl ();
	}
}
