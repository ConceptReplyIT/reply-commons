package it.reply.utils.web.ws.rest.restclient;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.RestMessage;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecodeStrategy;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecoder;
import it.reply.utils.web.ws.rest.restclient.Request.RequestBuilder;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

import java.util.List;

/**
 * A general RestClient to make Rest requests. <br/>
 * This client is meant to decode automatically the JSON response body using the
 * {@link RestResponseDecoder} system.
 * 
 * @author l.biava
 * 
 * @param <T>
 */
public interface RestClient {

	/**
	 * 
	 * @return the default request timeout in ms.
	 */
	public int getDefaultTimeout();

	/**
	 * 
	 * @param defaultTimeout
	 *            the default request timeout in ms.
	 */
	public void setDefaultTimeout(int defaultTimeout);

	public void setRequestInserceptors(List<RequestInterceptor> interceptors);
	
	public List<RequestInterceptor> getRequestInserceptors();
	
	public RequestBuilder<?> request(String url);
	
  public RequestBuilder<?> request(String url, Object...parameters);
	
	/***************************** GET Requests ******************************/

	/**
	 * Executes a Restful GET Request to the given URL, with the given headers
	 * and body (with the given media type) and <b>tries to decode the JSON
	 * result with the given {@link RestResponseDecoder} and/or
	 * {@link RestResponseDecodeStrategy}</b>.
	 * 
	 * @param URL
	 * @param headers
	 * @param rrd
	 *            A Rest Result Decoder.
	 * @param strategy
	 *            Can be null if the decoder's default strategy has to be used.
	 * @param reqOptions
	 *            Request options like timeout, proxy, etc.
	 * @return The Rest result decoded (of the type passed to the class).
	 * @throws RestClientException
	 *             if a problem with the request has occurred (not an error of
	 *             the remote application, just for the HTTP request).
	 * @throws NoMappingModelFoundException
	 *             {@link NoMappingModelFoundException}
	 * @throws ServerErrorResponseException
	 *             if the server responded with a custom error not using the
	 *             application protocol (ie. 404)
	 */
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, O> RestResponseResultType getRequest(String URL,
			MultivaluedMap<String, Object> headers, Request.Options reqOptions,
			RestResponseDecoder<RestResponseResultType, R, O> rrd,
			RestResponseDecodeStrategy<O> strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException,
			ServerErrorResponseException;

	/**
	 * See
	 * {@link RestClient#getRequest(String, MultivaluedMap, RequestOptions, RestResponseDecoder, RestResponseDecodeStrategy )}
	 * . Without RequestOptions param.
	 */
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, O> RestResponseResultType getRequest(String URL,
			MultivaluedMap<String, Object> headers,
			RestResponseDecoder<RestResponseResultType, R, O> rrd,
			RestResponseDecodeStrategy<O> strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException,
			ServerErrorResponseException;

	/**
	 * Executes a Restful GET Request to the given URL, with the given headers
	 * and body (with the given media type) and returns the response in a
	 * {@link RestMessage}.
	 * 
	 * @param URL
	 * @param headers
	 * @param reqOptions
	 *            Request options like timeout, proxy, etc.
	 * @return the response in a {@link RestMessage}.
	 * @throws RestClientException
	 *             if a problem with the request has occurred (not an error of
	 *             the remote application, just for the HTTP request).
	 */
	public RestMessage<String> getRequest(String URL,
			MultivaluedMap<String, Object> headers, Request.Options reqOptions)
			throws RestClientException;

	/**
	 * See {@link RestClient#getRequest(String, MultivaluedMap, RequestOptions)}
	 * . Without RequestOptions param.
	 */
	public RestMessage<String> getRequest(String URL,
			MultivaluedMap<String, Object> headers) throws RestClientException;

	/***************************** HEAD Requests ******************************/

	/**
	 * Executes a Restful HEAD Request to the given URL, with the given headers
	 * and body (with the given media type) and returns the response in a
	 * {@link RestMessage}.
	 * 
	 * @param URL
	 * @param headers
	 * @param reqOptions
	 *            Request options like timeout, proxy, etc.
	 * @return the response in a {@link RestMessage}.
	 * @throws RestClientException
	 *             if a problem with the request has occurred (not an error of
	 *             the remote application, just for the HTTP request).
	 */
	public RestMessage<Void> headRequest(String URL,
			MultivaluedMap<String, Object> headers, Request.Options reqOptions)
			throws RestClientException;

	/**
	 * See
	 * {@link RestClient#headRequest(String, MultivaluedMap, RequestOptions)}.
	 * Without RequestOptions param.
	 */
	public RestMessage<Void> headRequest(String URL,
			MultivaluedMap<String, Object> headers) throws RestClientException;

	/***************************** POST Requests ******************************/

	/**
	 * Executes a Restful POST Request to the given URL, with the given headers
	 * and body (with the given media type) and <b>tries to decode the JSON
	 * result with the given {@link RestResponseDecoder} and/or
	 * {@link RestResponseDecodeStrategy}</b>.
	 * 
	 * @param URL
	 * @param headers
	 * @param body
	 *            An entity representing the body of the request ({@see
	 *            RestClientHelper}).
	 * @param bodyMediaType
	 *            The media type of the body entity.
	 * @param rrd
	 *            A Rest Result Decoder.
	 * @param strategy
	 *            Can be null if the decoder's default strategy has to be used.
	 * @param reqOptions
	 *            Request options like timeout, proxy, etc.
	 * @return The Rest result decoded (of the type passed to the class).
	 * @throws RestClientException
	 *             if a problem with the request has occurred (not an error of
	 *             the remote application, just for the HTTP request).
	 * @throws NoMappingModelFoundException
	 *             {@link NoMappingModelFoundException}
	 * @throws ServerErrorResponseException
	 *             if the server responded with a custom error not using the
	 *             application protocolo (ie. 404)
	 */
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, B, O> RestResponseResultType postRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<B> body,
			MediaType bodyMediaType, Request.Options reqOptions,
			RestResponseDecoder<RestResponseResultType, R, O> rrd,
			RestResponseDecodeStrategy<O> strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException,
			ServerErrorResponseException;

	/**
	 * See
	 * {@link RestClient#postRequest(String, MultivaluedMap, GenericEntity, MediaType, RequestOptions, RestResponseDecoder, RestResponseDecodeStrategy)}
	 * Without RequestOptions param.
	 */
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, B, O> RestResponseResultType postRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<B> body,
			MediaType bodyMediaType,
			RestResponseDecoder<RestResponseResultType, R, O> rrd,
			RestResponseDecodeStrategy<O> strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException,
			ServerErrorResponseException;

	/**
	 * Executes a Restful POST Request to the given URL, with the given headers
	 * and body (with the given media type) and returns the response in a
	 * {@link RestMessage}.
	 * 
	 * @param URL
	 * @param headers
	 * @param body
	 *            An entity representing the body of the request ({@see
	 *            RestClientHelper}).
	 * @param bodyMediaType
	 *            The media type of the body entity.
	 * @param reqOptions
	 *            Request options like timeout, proxy, etc.
	 * @return the response in a {@link RestMessage}.
	 * @throws RestClientException
	 *             if a problem with the request has occurred (not an error of
	 *             the remote application, just for the HTTP request).
	 */
	public <B> RestMessage<String> postRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<B> body,
			MediaType bodyMediaType, Request.Options reqOptions)
			throws RestClientException;

	/**
	 * See
	 * {@link RestClient#postRequest(String, MultivaluedMap, GenericEntity, MediaType, RequestOptions)}
	 * . Without RequestOptions param.
	 */
	public <B> RestMessage<String> postRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<B> body,
			MediaType bodyMediaType) throws RestClientException;

	/***************************** PUT Requests ******************************/

	/**
	 * Executes a Restful PUT Request to the given URL, with the given headers
	 * and body (with the given media type) and <b>tries to decode the JSON
	 * result with the given {@link RestResponseDecoder} and/or
	 * {@link RestResponseDecodeStrategy}</b>.
	 * 
	 * @param URL
	 * @param headers
	 * @param body
	 *            An entity representing the body of the request ({@see
	 *            RestClientHelper}).
	 * @param bodyMediaType
	 *            The media type of the body entity.
	 * @param rrd
	 *            A Rest Result Decoder.
	 * @param strategy
	 *            Can be null if the decoder's default strategy has to be used.
	 * @return The Rest result decoded (of the type passed to the class).
	 * @param reqOptions
	 *            Request options like timeout, proxy, etc.
	 * @throws RestClientException
	 *             if a problem with the request has occurred (not an error of
	 *             the remote application, just for the HTTP request).
	 * @throws NoMappingModelFoundException
	 *             {@link NoMappingModelFoundException}
	 * @throws ServerErrorResponseException
	 *             if the server responded with a custom error not using the
	 *             application protocol (ie. 404)
	 */
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, B, O> RestResponseResultType putRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<B> body,
			MediaType bodyMediaType, Request.Options reqOptions,
			RestResponseDecoder<RestResponseResultType, R, O> rrd,
			RestResponseDecodeStrategy<O> strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException,
			ServerErrorResponseException;

	/**
	 * See
	 * {@link RestClient#putRequest(String, MultivaluedMap, GenericEntity, MediaType, RequestOptions, RestResponseDecoder, RestResponseDecodeStrategy)}
	 * . Without RequestOptions param.
	 */
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, B, O> RestResponseResultType putRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<B> body,
			MediaType bodyMediaType,
			RestResponseDecoder<RestResponseResultType, R, O> rrd,
			RestResponseDecodeStrategy<O> strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException,
			ServerErrorResponseException;

	/**
	 * Executes a Restful PUT Request to the given URL, with the given headers
	 * and body (with the given media type) and returns the response in a
	 * {@link RestMessage}.
	 * 
	 * @param URL
	 * @param headers
	 * @param body
	 *            An entity representing the body of the request ({@see
	 *            RestClientHelper}).
	 * @param bodyMediaType
	 *            The media type of the body entity.
	 * @param reqOptions
	 *            Request options like timeout, proxy, etc.
	 * @return the response in a {@link RestMessage}.
	 * @throws RestClientException
	 *             if a problem with the request has occurred (not an error of
	 *             the remote application, just for the HTTP request).
	 */
	public <B> RestMessage<String> putRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<B> body,
			MediaType bodyMediaType, Request.Options reqOptions)
			throws RestClientException;

	/**
	 * See
	 * {@link RestClient#putRequest(String, MultivaluedMap, GenericEntity, MediaType, RequestOptions)}
	 * . Without RequestOptions param.
	 */
	public <B> RestMessage<String> putRequest(String URL,
			MultivaluedMap<String, Object> headers, GenericEntity<B> body,
			MediaType bodyMediaType) throws RestClientException;

	/***************************** DELETE Requests ******************************/

	/**
	 * Executes a Restful DELETE Request to the given URL, with the given
	 * headers and body (with the given media type) and <b>tries to decode the
	 * JSON result with the given {@link RestResponseDecoder} and/or
	 * {@link RestResponseDecodeStrategy}</b>.
	 * 
	 * @param URL
	 * @param headers
	 * @param rrd
	 *            A Rest Result Decoder.
	 * @param strategy
	 *            Can be null if the decoder's default strategy has to be used.
	 * @param reqOptions
	 *            Request options like timeout, proxy, etc.
	 * @return The Rest result decoded (of the type passed to the class).
	 * @throws RestClientException
	 *             if a problem with the request has occurred (not an error of
	 *             the remote application, just for the HTTP request).
	 * @throws NoMappingModelFoundException
	 *             {@link NoMappingModelFoundException}
	 * @throws ServerErrorResponseException
	 *             if the server responded with a custom error not using the
	 *             application protocol (ie. 404)
	 */
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, O> RestResponseResultType deleteRequest(String URL,
			MultivaluedMap<String, Object> headers,
			Request.Options reqOptions,
			RestResponseDecoder<RestResponseResultType, R, O> rrd,
			RestResponseDecodeStrategy<O> strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException,
			ServerErrorResponseException;

	/**
	 * See
	 * {@link RestClient#deleteRequest(String, MultivaluedMap, RequestOptions, RestResponseDecoder, RestResponseDecodeStrategy)}
	 * . Without RequestOptions param.
	 */
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, O> RestResponseResultType deleteRequest(String URL,
			MultivaluedMap<String, Object> headers,
			RestResponseDecoder<RestResponseResultType, R, O> rrd,
			RestResponseDecodeStrategy<O> strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException,
			ServerErrorResponseException;

	/**
	 * Executes a Restful DELETE Request to the given URL, with the given
	 * headers and body (with the given media type) and returns the response in
	 * a {@link RestMessage}.
	 * 
	 * @param URL
	 * @param headers
	 * @param reqOptions
	 *            Request options like timeout, proxy, etc.
	 * @return the response in a {@link RestMessage}.
	 * @throws RestClientException
	 *             if a problem with the request has occurred (not an error of
	 *             the remote application, just for the HTTP request).
	 */
	public RestMessage<String> deleteRequest(String URL,
			MultivaluedMap<String, Object> headers, Request.Options reqOptions)
			throws RestClientException;

	/**
	 * See
	 * {@link RestClient#deleteRequest(String, MultivaluedMap, RequestOptions)}.
	 * Without RequestOptions param.
	 */
	public RestMessage<String> deleteRequest(String URL,
			MultivaluedMap<String, Object> headers) throws RestClientException;
	
	/*	GENERIC REQUESTS */
	
	/**
	 * 	
	 */
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, B, O> RestResponseResultType doRequest(Request.HttpMethod method, String URL,
			MultivaluedMap<String, Object> headers, MultivaluedMap<String, Object> queryParams, 
			GenericEntity<B> body,
			MediaType bodyMediaType,
			Request.Options reqOptions,
			RestResponseDecoder<RestResponseResultType, R, O> rrd,
			RestResponseDecodeStrategy<O> strategy) throws RestClientException,
			NoMappingModelFoundException, MappingException,
			ServerErrorResponseException;
	
	public <B> RestMessage<String> doRequest(Request.HttpMethod method, String URL,
			MultivaluedMap<String, Object> headers, MultivaluedMap<String, Object> queryParams, 
			GenericEntity<B> body,
			MediaType bodyMediaType,
			Request.Options reqOptions) throws RestClientException;
	
	public <B, O> RestMessage<O> doRequest(Request.HttpMethod method, String URL,
			MultivaluedMap<String, Object> headers, MultivaluedMap<String, Object> queryParams, 
			GenericEntity<B> body,
			MediaType bodyMediaType,
			Request.Options reqOptions, Class<O> entityClass) throws RestClientException;
	
	public <B, O> RestMessage<O> doRequest(Request<B> request, Class<O> entityClass) throws RestClientException;
	
	public <RestResponseResultType extends BaseRestResponseResult<R, O>, R, B, O> RestResponseResultType doRequest(Request<B> request, RestResponseDecoder<RestResponseResultType, R , O> rrd,
      RestResponseDecodeStrategy<O> strategy) throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException;
}
