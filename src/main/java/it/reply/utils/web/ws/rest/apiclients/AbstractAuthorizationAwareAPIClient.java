package it.reply.utils.web.ws.rest.apiclients;

import com.google.common.collect.Lists;

import it.reply.utils.web.ws.rest.restclient.RequestInterceptor;
import it.reply.utils.web.ws.rest.restclient.RestClient;
import it.reply.utils.web.ws.rest.restclient.RestClientFactory;
import it.reply.utils.web.ws.rest.restclient.RestClientFactoryImpl;
import it.reply.utils.web.ws.rest.restclient.auth.AuthorizationContext;

/**
 * This class represent abstract API Client.
 * 
 * @author l.biava
 * 
 */
public abstract class AbstractAuthorizationAwareAPIClient extends AbstractAPIClient{

    protected AuthorizationContext context;

    /**
     * Creates a {@link AbstractAuthorizationAwareAPIClient} using the default
     * {@link RestClientFactoryImpl}.
     * 
     * @param baseWSUrl
     *            The base URL of the WebService.
     */
    public AbstractAuthorizationAwareAPIClient(String baseWSUrl, AuthorizationContext context) {
      this(baseWSUrl, context, new RestClientFactoryImpl());
    }

    /**
     * Creates a {@link AbstractAuthorizationAwareAPIClient} with the given
     * {@link RestClientFactory}.
     * 
     * @param baseWSUrl
     *            The base URL of the WebService.
     * @param restClientFactory
     *            The custom factory for the {@link RestClient}. If null then
     *            the default one will be used {@link RestClientFactoryImpl}.
     */
    public AbstractAuthorizationAwareAPIClient(String baseWSUrl, AuthorizationContext context, RestClientFactory restClientFactory) {
      super(baseWSUrl, restClientFactory);
      this.context = context;
    }


    @Override
    public RestClient getRestClient() {
      RestClient client = restClientFactory.getRestClient();
      client.setRequestInserceptors(Lists.<RequestInterceptor>newArrayList(context));
      return client; 
    }

}
