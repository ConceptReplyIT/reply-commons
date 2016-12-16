package it.reply.utils.web.ws.rest.apiclients.prisma;

import java.io.IOException;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.StatusType;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.reply.domain.dsl.prisma.restprotocol.PrismaResponseWrapper;
import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.RestMessage;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseDecoder;
import it.reply.utils.web.ws.rest.apiencoding.decode.RestResponseDecodeStrategy;

/**
 * Basic implementation of a Rest response Decoder. This implementation uses
 * Jackson JSON mapping system.
 * 
 * @author l.biava
 * 
 */
public abstract class PrismaRestResponseDecoder<APIResponseType> extends
		BaseRestResponseDecoder<PrismaRestResponseResult<APIResponseType>, PrismaResponseWrapper<APIResponseType>> {
  
  private TypeToken<APIResponseType> type = new TypeToken<APIResponseType>(getClass()) {
    private static final long serialVersionUID = 1L;
  };

  protected TypeToken<APIResponseType> getType() {
    return type;
  }
  
	public PrismaRestResponseDecoder() {
		super();
		this.defaultDecodeStrategy = new PrismaRRDStrategy<APIResponseType>(getType());
	}
	
  public <X> PrismaRestResponseDecoder<APIResponseType> where(TypeParameter<X> typeParam,
      TypeToken<X> typeArg) {
    this.type = type.where(typeParam, typeArg);
    this.defaultDecodeStrategy = new PrismaRRDStrategy<APIResponseType>(getType());
    return this;
  }
	
  public <X> PrismaRestResponseDecoder<APIResponseType> where(TypeParameter<X> typeParam,
      Class<X> typeArg) {
    this.type = type.where(typeParam, typeArg);
    this.defaultDecodeStrategy = new PrismaRRDStrategy<APIResponseType>(getType());
    return this;
  }
	 
	/**
	 * <b>Supports only default strategy. MUST pass null in strategy field !</b><br/>
	 * {@inheritDoc}
	 */
	@Override
	public PrismaRestResponseResult<APIResponseType> decode(RestMessage<String> msg,
			RestResponseDecodeStrategy<String> strategy)
			throws NoMappingModelFoundException, MappingException,
			ServerErrorResponseException {

		if (strategy != null)
			throw new UnsupportedOperationException(
					"Only default decoding strategy can be used with this decoder.");

		return decode(msg);
	}

	@Override
	public PrismaRestResponseResult<APIResponseType> decode(RestMessage<String> msg)
			throws NoMappingModelFoundException, MappingException,
			ServerErrorResponseException {

		StatusType status = defaultDecodeStrategy.getStatus(msg);
		JavaType mappingClass = defaultDecodeStrategy.getModelClass(msg);

		checkMediaType(msg);

		PrismaResponseWrapper<APIResponseType> result = null;
		if (msg.getBody() != null)
		{
			try {
				result = new ObjectMapper().readValue(msg.getBody(), mappingClass);
			} catch (IOException e) {
				throw new MappingException(e.getMessage(), e, msg);
			}
		}
		
		return new PrismaRestResponseResult<APIResponseType>(status, result, mappingClass, msg);
	}

}
