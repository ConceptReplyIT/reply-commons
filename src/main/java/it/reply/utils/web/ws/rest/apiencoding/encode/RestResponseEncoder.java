package it.reply.utils.web.ws.rest.apiencoding.encode;

public interface RestResponseEncoder {

	public String encode(Object model, Class<?> modelClass);
	
}
