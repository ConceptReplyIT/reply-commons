package it.reply.utils.web.ws.rest.apiclients;

import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;
import it.reply.utils.web.ws.rest.restclient.RestClient;
import it.reply.utils.web.ws.rest.restclient.RestClientFactory;
import it.reply.utils.web.ws.rest.restclient.RestClientFactoryImpl;

/**
 * This class represent abstract API Client.
 * 
 * @author l.biava
 * 
 */
public abstract class AbstractAPIClient {

    protected String baseWSUrl;
    protected RestClientFactory restClientFactory;
    protected RestClient<BaseRestResponseResult> restClient;

    /**
     * Creates a {@link AbstractAPIClient} using the default
     * {@link RestClientFactoryImpl}.
     * 
     * @param baseWSUrl
     *            The base URL of the WebService.
     */
    public AbstractAPIClient(String baseWSUrl) {
	this(baseWSUrl, new RestClientFactoryImpl());
    }

    /**
     * Creates a {@link AbstractAPIClient} with the given
     * {@link RestClientFactory}.
     * 
     * @param baseWSUrl
     *            The base URL of the WebService.
     * @param restClientFactory
     *            The custom factory for the {@link RestClient}. If null then
     *            the default one will be used {@link RestClientFactoryImpl}.
     */
    public AbstractAPIClient(String baseWSUrl, RestClientFactory restClientFactory) {
	super();
	if (restClientFactory == null) {
	    restClientFactory = new RestClientFactoryImpl();
	}
	this.baseWSUrl = baseWSUrl;
	this.restClientFactory = restClientFactory;
	this.restClient = restClientFactory.getRestClient();
    }

    public String getBaseWSUrl() {
	return baseWSUrl;
    }

    public RestClientFactory getRestClientFactory() {
	return restClientFactory;
    }

    public RestClient<BaseRestResponseResult> getRestClient() {
	return restClient;
    }

}
