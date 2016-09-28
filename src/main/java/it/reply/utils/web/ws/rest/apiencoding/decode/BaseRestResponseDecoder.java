package it.reply.utils.web.ws.rest.apiencoding.decode;

import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.RestMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * Basic implementation of a Rest response Decoder. This implementation uses
 * Jackson JSON mapping system.
 * 
 * @author l.biava
 * 
 */
public abstract class BaseRestResponseDecoder<RestResponseResult extends BaseRestResponseResult<R, String>, R> implements
		RestResponseDecoder<BaseRestResponseResult<R, String>, R, String> {

  private static Logger LOG = LoggerFactory.getLogger(BaseRestResponseDecoder.class);
  
	protected RestResponseDecodeStrategy defaultDecodeStrategy;

	public BaseRestResponseDecoder() {
		super();
	}

	public BaseRestResponseDecoder(
			RestResponseDecodeStrategy defaultDecodeStrategy) {
		this.defaultDecodeStrategy = defaultDecodeStrategy;
	}

	public RestResponseDecodeStrategy getDefaultDecodeStrategy() {
		return defaultDecodeStrategy;
	}

	public void setDefaultDecodeStrategy(
			RestResponseDecodeStrategy defaultDecodeStrategy) {
		this.defaultDecodeStrategy = defaultDecodeStrategy;
	}

	 protected void checkMediaType(RestMessage<String> msg) throws MappingException {
	    boolean hasMediatypeJson = false;
	    List<Object> mediaTypes = null;
	    
	    if (msg.getHeaders() != null) {
	      mediaTypes = msg.getHeaders().get(HttpHeaders.CONTENT_TYPE);
	    }
	    if (mediaTypes != null) {
	      if (mediaTypes.size() > 1) {
	        LOG.warn("Multiple MediaTypes found in HTTP response: {}", mediaTypes);
	      }
	      for (Object mediaType : mediaTypes) {
	        if (mediaType != null && mediaType.toString().contains(MediaType.APPLICATION_JSON)) {
	          hasMediatypeJson = true;
	          break;
	        }
	      }
	    }
	    if (!hasMediatypeJson) {
	      throw new MappingException("Not JSON encoded body.", null, msg);
	    }
	  }
}
