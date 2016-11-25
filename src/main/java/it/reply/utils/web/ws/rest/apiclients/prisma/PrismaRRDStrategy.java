package it.reply.utils.web.ws.rest.apiclients.prisma;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.javatuples.Pair;

import com.google.common.reflect.TypeToken;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.reply.domain.dsl.prisma.restprotocol.PrismaResponseWrapper;
import it.reply.utils.json.JsonUtility;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.RestMessage;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecodeStrategy;

/**
 * Generic Rest Decode strategy for Prisma Rest Protocol.
 * 
 * @param <APIResponseType>
 *            The expected ResponseType.
 * @author l.biava
 * 
 */
public class PrismaRRDStrategy<APIResponseType> implements
		RestResponseDecodeStrategy<String> {

  private TypeToken<APIResponseType> type;

  protected TypeToken<APIResponseType> getType() {
    return type;
  }
  
  public PrismaRRDStrategy(TypeToken<APIResponseType> genericType) {
    type = genericType;
  }
  
	@Override
	public StatusType getStatus(RestMessage<String> msg)
			throws NoMappingModelFoundException, ServerErrorResponseException {
		return Status.fromStatusCode(msg.getHttpStatusCode());
	}
	
  @Override
  public JavaType getModelClass(RestMessage<String> msg)
      throws NoMappingModelFoundException, ServerErrorResponseException {

		JavaType clazz;

		if (msg.getHeaders().containsKey(HttpHeaders.CONTENT_TYPE))
		{
			if (msg.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)
				.equals(MediaType.APPLICATION_JSON)) {
				// ResponseWrapper
				ObjectMapper mapper = new ObjectMapper();
				clazz = mapper.getTypeFactory().constructParametricType(
						PrismaResponseWrapper.class, JsonUtility.getJavaType(getType()));
			} else {
				// SERVER ERROR
			  StatusType status = getStatus(msg);
				throw new ServerErrorResponseException("SERVER_ERROR_RESPONSE",
						null, msg, status.getStatusCode());
			}
		}
		else
		{
			ObjectMapper mapper = new ObjectMapper();
			clazz = mapper.getTypeFactory().constructParametricType(
					PrismaResponseWrapper.class, JsonUtility.getJavaType(getType()));
		}
		
		return clazz;
	}
}
