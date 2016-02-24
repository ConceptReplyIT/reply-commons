package it.reply.utils.web.ws.rest.restclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.RestMessage;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecodeStrategy;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecoder;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

public class RestClientImpl<T extends BaseRestResponseResult> extends AbstractRestClient<T> {

	/***************************** GET Requests ******************************/

	@Override
	public T getRequest(String URL, MultivaluedMap<String, Object> headers,
			it.reply.utils.web.ws.rest.restclient.RestClient.RequestOptions reqOptions, RestResponseDecoder<T> rrd,
			RestResponseDecodeStrategy strategy) throws RestClientException, NoMappingModelFoundException,
			MappingException, ServerErrorResponseException {

		RestMessage msg = doRequest(RestMethod.GET, URL, headers, null, null, null, reqOptions);

		return rrd.decode(msg, strategy);
	}

	@Override
	public RestMessage getRequest(String URL, MultivaluedMap<String, Object> headers,
			it.reply.utils.web.ws.rest.restclient.RestClient.RequestOptions reqOptions) throws RestClientException {

		return doRequest(RestMethod.GET, URL, headers, null, null, null, reqOptions);
	}

	/***************************** HEAD Requests ******************************/

	@Override
	public T headRequest(String URL, MultivaluedMap<String, Object> headers,
			it.reply.utils.web.ws.rest.restclient.RestClient.RequestOptions reqOptions, RestResponseDecoder<T> rrd,
			RestResponseDecodeStrategy strategy) throws RestClientException, NoMappingModelFoundException,
			MappingException, ServerErrorResponseException {

		RestMessage msg = doRequest(RestMethod.HEAD, URL, headers, null, null, null, reqOptions);

		return rrd.decode(msg, strategy);
	}

	@Override
	public RestMessage headRequest(String URL, MultivaluedMap<String, Object> headers,
			it.reply.utils.web.ws.rest.restclient.RestClient.RequestOptions reqOptions) throws RestClientException {

		return doRequest(RestMethod.HEAD, URL, headers, null, null, null, reqOptions);
	}

	/***************************** POST Requests ******************************/

	@Override
	public <E> T postRequest(String URL, MultivaluedMap<String, Object> headers, GenericEntity<E> body,
			MediaType bodyMediaType, it.reply.utils.web.ws.rest.restclient.RestClient.RequestOptions reqOptions,
			RestResponseDecoder<T> rrd, RestResponseDecodeStrategy strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		RestMessage msg = doRequest(RestMethod.POST, URL, headers, null, body, bodyMediaType, reqOptions);
		// System.out.println(msg.getBody().toString());
		return rrd.decode(msg, strategy);
	}

	@Override
	public <E> RestMessage postRequest(String URL, MultivaluedMap<String, Object> headers, GenericEntity<E> body,
			MediaType bodyMediaType, it.reply.utils.web.ws.rest.restclient.RestClient.RequestOptions reqOptions)
			throws RestClientException {

		return doRequest(RestMethod.POST, URL, headers, null, body, bodyMediaType, reqOptions);
	}

	/***************************** PUT Requests ******************************/

	@Override
	public <E> T putRequest(String URL, MultivaluedMap<String, Object> headers, GenericEntity<E> body,
			MediaType bodyMediaType, it.reply.utils.web.ws.rest.restclient.RestClient.RequestOptions reqOptions,
			RestResponseDecoder<T> rrd, RestResponseDecodeStrategy strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		RestMessage msg = doRequest(RestMethod.PUT, URL, headers, null, body, bodyMediaType, reqOptions);
		return rrd.decode(msg, strategy);
	}

	@Override
	public <E> RestMessage putRequest(String URL, MultivaluedMap<String, Object> headers, GenericEntity<E> body,
			MediaType bodyMediaType, it.reply.utils.web.ws.rest.restclient.RestClient.RequestOptions reqOptions)
			throws RestClientException {

		return doRequest(RestMethod.PUT, URL, headers, null, body, bodyMediaType, reqOptions);
	}

	/***************************** DELETE Requests ******************************/

	@Override
	public <E> T deleteRequest(String URL, MultivaluedMap<String, Object> headers, GenericEntity<E> body,
			MediaType bodyMediaType, it.reply.utils.web.ws.rest.restclient.RestClient.RequestOptions reqOptions,
			RestResponseDecoder<T> rrd, RestResponseDecodeStrategy strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		RestMessage msg = doRequest(RestMethod.DELETE, URL, headers, null, body, bodyMediaType, reqOptions);

		return rrd.decode(msg, strategy);
	}

	@Override
	public <E> RestMessage deleteRequest(String URL, MultivaluedMap<String, Object> headers, GenericEntity<E> body,
			MediaType bodyMediaType, it.reply.utils.web.ws.rest.restclient.RestClient.RequestOptions reqOptions)
			throws RestClientException {

		return doRequest(RestMethod.DELETE, URL, headers, null, body, bodyMediaType, reqOptions);
	}

	/***************************** Generic Requests ******************************/

	@Override
	public <E> T doRequest(it.reply.utils.web.ws.rest.restclient.RestClient.RestMethod method, String URL,
			MultivaluedMap<String, Object> headers, MultivaluedMap<String, Object> queryParams, GenericEntity<E> body,
			MediaType bodyMediaType, it.reply.utils.web.ws.rest.restclient.RestClient.RequestOptions reqOptions,
			RestResponseDecoder<T> rrd, RestResponseDecodeStrategy strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		RestMessage msg = doRequest(method, URL, headers, queryParams, body, bodyMediaType, reqOptions);
		return rrd.decode(msg, strategy);
	}

	// /***************************** PRIVATE methods
	// ******************************/

	@Override
	public <E> RestMessage doRequest(RestMethod method, String URL, MultivaluedMap<String, Object> headers,
			MultivaluedMap<String, Object> queryParams, GenericEntity<E> body, MediaType bodyMediaType,
			RequestOptions reqOptions) throws RestClientException {

		return doRequest(method, URL, headers, queryParams, body, bodyMediaType, reqOptions, String.class);

	}

	@Override
	public <E> RestMessage doRequest(it.reply.utils.web.ws.rest.restclient.RestClient.RestMethod method, String URL,
			MultivaluedMap<String, Object> headers, MultivaluedMap<String, Object> queryParams, GenericEntity<E> body,
			MediaType bodyMediaType, it.reply.utils.web.ws.rest.restclient.RestClient.RequestOptions reqOptions,
			Class<?> entityClass) throws RestClientException {

		ResteasyClientBuilder cb = new ResteasyClientBuilder();

		// Socket Timeout
		if (reqOptions != null && reqOptions.getTimeout() > 0)
			cb.socketTimeout(reqOptions.getTimeout(), TimeUnit.MILLISECONDS);
		else
			cb.socketTimeout(this.defaultTimeout, TimeUnit.MILLISECONDS);

		// Proxy
		if (reqOptions != null && reqOptions.getProxy() != null) {
			cb.defaultProxy(reqOptions.getProxy().getHostname(), reqOptions.getProxy().getPort(), reqOptions.getProxy()
					.getProtocol());
		} else {
			if (System.getProperty("java.net.useSystemProxies") != null
					&& System.getProperty("java.net.useSystemProxies").equals("true")) {
				try {
					URL url = new URL(URL);
					if (!url.getAuthority().contains("localhost") && !url.getAuthority().contains("127.0.0.1")) {
						cb.defaultProxy("proxy.reply.it", 8080, "HTTP");
					}
				} catch (MalformedURLException e) {
					cb.defaultProxy("proxy.reply.it", 8080, "HTTP");
				}
				// TODO Fix using system properties
				// try {
				// List l=ProxySelector.getDefault().select(new
				// URI("http://foo/bar"));
				// for (Iterator iter = l.iterator(); iter.hasNext();) {
				// java.net.Proxy proxy = (java.net.Proxy) iter.next();
				// System.out.println("proxy hostname : " + proxy.type());
				//
				// InetSocketAddress addr = (InetSocketAddress) proxy.address();
				//
				// if (addr == null) {
				// System.out.println("No Proxy");
				// } else {
				// System.out.println("proxy hostname : " + addr.getHostName());
				// System.setProperty("http.proxyHost", addr.getHostName());
				// System.out.println("proxy port : " + addr.getPort());
				// System.setProperty("http.proxyPort",
				// Integer.toString(addr.getPort()));
				//
				// cb.defaultProxy(addr.getHostName(), addr.getPort());
				// }
				// }
				// } catch (URISyntaxException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}
		}

		ResteasyClient client = cb.build();

		Response response = null;
		try {
			ResteasyWebTarget target = client.target(URL);

			// Add Query Params
			if (queryParams != null)
				target = target.queryParams(queryParams);

			Builder reqBuilder = target.request();

			if (headers != null) {
				reqBuilder = reqBuilder.headers(headers);
			}

			switch (method) {
			case GET:
				if (body == null)
					response = reqBuilder.get();
				else
					response = reqBuilder.method("GET", Entity.entity(body, bodyMediaType));
				break;
			case HEAD:
				if (body == null)
					response = reqBuilder.head();
				else
					response = reqBuilder.method("HEAD", Entity.entity(body, bodyMediaType));
				break;
			case POST:
				if (body == null) {
					response = reqBuilder.method("POST");
				} else
					response = reqBuilder.post(Entity.entity(body, bodyMediaType));
				break;
			case PUT:
				if (body == null) {
					response = reqBuilder.method("PUT");
				} else {
					response = reqBuilder.put(Entity.entity(body, bodyMediaType));
				}
				break;
			case DELETE:
				if (body == null)
					response = reqBuilder.delete();
				else
					response = reqBuilder.method("DELETE", Entity.entity(body, bodyMediaType));
				break;
			}

			RestMessage msg;
			try {
				msg = new RestMessage(response.getHeaders(), response.readEntity(entityClass), response.getStatus());
			} catch (Exception e) {
				msg = new RestMessage(response.getHeaders(), null, response.getStatus());
			}

			return msg;

		} catch (Exception e) {
			throw new RestClientException(e.getMessage(), e);
		} finally {
			if (response != null)
				response.close();
			if (client != null)
				client.close();
		}

		// return null;
	}

}
