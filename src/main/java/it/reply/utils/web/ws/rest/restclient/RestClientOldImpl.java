package it.reply.utils.web.ws.rest.restclient;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import it.reply.utils.web.ws.rest.apiencoding.RestMessage;
import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecodeStrategy;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecoder;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

public class RestClientOldImpl<RestResponseResultType extends BaseRestResponseResult> implements RestClientOld<RestResponseResultType> {

	@Override
	public RestResponseResultType getRequest(String URL,
			MultivaluedMap<String, Object> headers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestResponseResultType postRequestJSON(String URL,
			MultivaluedMap<String, Object> headers, String jsonBody,
			RestResponseDecoder<RestResponseResultType> rrd) throws RestClientException{

		return postRequestJSON(URL, headers, jsonBody, rrd,null);
	}

	@Override
	public RestResponseResultType postRequestJSON(String URL,
			MultivaluedMap<String, Object> headers, String jsonbody,
			RestResponseDecoder<RestResponseResultType> rrd,
			RestResponseDecodeStrategy strategy) throws RestClientException {

		ResteasyClient client = new ResteasyClientBuilder().build();
		Response response = null;
		try {
			ResteasyWebTarget target = client.target(URL);

			if (headers != null) {
				for (MultivaluedMap.Entry<String, List<Object>> header : headers.entrySet()) {
					//TODO: check lista
					target.request().header(header.getKey(), header.getValue());
				}
			}
			
			response = target.request().post(
					Entity.entity(jsonbody, MediaType.APPLICATION_JSON_TYPE));
			
			RestMessage msg = new RestMessage(response.getHeaders(),
					response.readEntity(String.class), response.getStatus());		
			
			return rrd.decode(msg,strategy);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RestClientException(e.getMessage(),e);
		} finally {
			if (response != null)
				response.close();
			if (client != null)
				client.close();
		}

	}

	@Override
	public RestResponseResultType postRequestForm(String URL,
			MultivaluedMap<String, Object> headers, String body,
			RestResponseDecoder<RestResponseResultType> rrd) {
		// TODO Auto-generated method stub
		return null;
	}

}
