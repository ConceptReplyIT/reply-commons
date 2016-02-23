package it.prisma.utils.web.ws.rest.apiclients.prisma;

import it.prisma.domain.dsl.prisma.prismaprotocol.PrismaResponseWrapper;
import it.prisma.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.prisma.utils.web.ws.rest.apiencoding.RestMessage;
import it.prisma.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.prisma.utils.web.ws.rest.apiencoding.decode.RestResponseDecodeStrategy;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.javatuples.Pair;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Generic Rest Decode strategy for Prisma Rest Protocol.
 * 
 * @param <APIResponseType>
 *            The expected ResponseType.
 * @author l.biava
 * 
 */
public class PrismaRRDStrategy<APIResponseType> implements
		RestResponseDecodeStrategy {

	private JavaType targetClass;

	/**
	 * Constructor for a provided target class type descriptor.
	 * 
	 * @param targetClass
	 *            the {@link JavaType} mapping class descriptor.
	 */
	public PrismaRRDStrategy(JavaType targetClass) {		
		this.targetClass = targetClass;
	}
	
	/**
	 * Constructor for a single class type (not nested types ! Use the other
	 * constructor instead, <code>PrismaRRDStrategy(Class... c)</code>).
	 * 
	 * @param c
	 *            the mapping class.
	 */
	public PrismaRRDStrategy(Class<APIResponseType> c) {
		ObjectMapper mapper = new ObjectMapper();
		targetClass = mapper.getTypeFactory().constructType(c);
	}

	/**
	 * Constructor for nested classes types (ie. List&lt;Set&lt;String&gt;&gt;).
	 * 
	 * @param c
	 *            an array of nested classes. <br/>
	 *            For example, to express the following:
	 * 
	 *            <code>
	 * 				List&lt;Set&lt;String&gt;&gt;
	 * 			  </code> </br>Use: <code>
	 *            new PrismaRRDStrategy(List.class, Set.class, String.class)
	 * 			  </code>
	 */
	public PrismaRRDStrategy(@SuppressWarnings("rawtypes") Class... c) {
		if (c.length < 2)
			throw new IllegalArgumentException(
					"The array of nested classes must be at least of two items !");

		int n = c.length;
		TypeFactory tf = TypeFactory.defaultInstance();
		targetClass = tf.constructParametricType(c[n - 2], c[n - 1]);
		for (int i = n - 3; i >= 0; i--) {
			targetClass = tf.constructParametricType(c[i], targetClass);
		}

	}

	@Override
	public JavaType getModelClass(RestMessage msg)
			throws NoMappingModelFoundException, ServerErrorResponseException {
		Pair<StatusType, JavaType> result = strategy(msg);
		return result.getValue1();
	}

	@Override
	public StatusType getStatus(RestMessage msg)
			throws NoMappingModelFoundException, ServerErrorResponseException {
		Pair<StatusType, JavaType> result = strategy(msg);
		return result.getValue0();
	}
	
	private Pair<StatusType, JavaType> strategy(RestMessage msg)
			throws NoMappingModelFoundException, ServerErrorResponseException {

		JavaType clazz;
		StatusType status = Status.fromStatusCode(msg.getHttpStatusCode());

		if (msg.getHeaders().containsKey(HttpHeaders.CONTENT_TYPE))
		{
			if (msg.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)
				.equals(MediaType.APPLICATION_JSON)) {
				// ResponseWrapper
				ObjectMapper mapper = new ObjectMapper();
				clazz = mapper.getTypeFactory().constructParametricType(
						PrismaResponseWrapper.class, targetClass);
			} else {
				// SERVER ERROR
				throw new ServerErrorResponseException("SERVER_ERROR_RESPONSE",
						null, msg, status.getStatusCode());
			}
		}
		else
		{
			ObjectMapper mapper = new ObjectMapper();
			clazz = mapper.getTypeFactory().constructParametricType(
					PrismaResponseWrapper.class, targetClass);
		}
		
		return new Pair<StatusType, JavaType>(status, clazz);
	}
}
