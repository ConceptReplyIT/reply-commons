package it.reply.utils.web.ws.rest.apiencoding.decode;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import com.google.common.reflect.TypeToken;

import com.fasterxml.jackson.databind.JavaType;
import it.reply.utils.json.JsonUtility;
import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.RestMessage;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public abstract class SimpleRestResponseDecoder<APIResponseType> extends
BaseRestResponseDecoder<BaseRestResponseResult<APIResponseType, String>, APIResponseType>
{

  private static Logger LOG = LoggerFactory.getLogger(SimpleRestResponseDecoder.class);
  
  protected boolean checkMediaType;
  
  public SimpleRestResponseDecoder() {
    this(true);
  }
  
  public SimpleRestResponseDecoder(boolean checkMediaType) {
    this.checkMediaType = checkMediaType;
  }
  
  private TypeToken<APIResponseType> type = new TypeToken<APIResponseType>(getClass()) {
    private static final long serialVersionUID = 1L;
  };

  protected TypeToken<APIResponseType> getType() {
    return type;
  }
  
  @Override
  public RestResponseDecodeStrategy getDefaultDecodeStrategy() {
    throw new UnsupportedOperationException("RestResponseDecodeStrategy are not supported");
  }

  @Override
  public void setDefaultDecodeStrategy(RestResponseDecodeStrategy defaultDecodeStrategy) {
    throw new UnsupportedOperationException("RestResponseDecodeStrategy are not supported");
  }

  @Override
  public BaseRestResponseResult<APIResponseType, String> decode(RestMessage<String> msg, RestResponseDecodeStrategy strategy)
      throws NoMappingModelFoundException, MappingException, ServerErrorResponseException {
    if (strategy != null) { 
      throw new UnsupportedOperationException("RestResponseDecodeStrategy are not supported");
    } else {
      return decode(msg);
    }
  }

  @Override
  public BaseRestResponseResult<APIResponseType, String> decode(RestMessage<String> msg)
      throws NoMappingModelFoundException, MappingException, ServerErrorResponseException {
    
    StatusType status = Status.fromStatusCode(msg.getHttpStatusCode());
    JavaType mappingClass = JsonUtility.getJavaType(getType());
      
    APIResponseType result = null;
    if (msg.getBody() != null)
    {
      if (checkMediaType) {
        checkMediaType(msg);
      }
      try {
        result = JsonUtility.deserializeJson(msg.getBody(), mappingClass);
      } catch (IOException e) {
        throw new MappingException(e.getMessage(), e, msg);
      }
    }
    return new BaseRestResponseResult<APIResponseType, String>(status, result, mappingClass, msg);
  }
}
