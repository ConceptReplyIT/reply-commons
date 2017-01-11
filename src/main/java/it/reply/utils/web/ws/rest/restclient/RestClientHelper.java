package it.reply.utils.web.ws.rest.restclient;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import it.reply.utils.json.JsonUtility;

/**
 * Helper class for {@link RestClient} request body.
 * 
 * @author l.biava
 * 
 */
public class RestClientHelper {

	/**
	 * Builder for JSON body. <br/>
	 * <b>Usage example:</b></br>
	 * {@code GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(some_POJO);
	 * }
	 * 
	 * @author l.biava
	 * 
	 */
	public static class JsonEntityBuilder {
		public static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON_TYPE;

		public GenericEntity<String> build(Object request)
				throws JsonParseException, JsonMappingException, IOException {
			return new GenericEntity<String>(JsonUtility.serializeJson(request)) {
			};
		}
	}

	/**
	 * Builder for MultiPart Form Data body. <br/>
	 * <br/>
	 * <b>Usage example:</b></br>
	 * {@code GenericEntity<MultipartFormDataOutput> ge = new
	 * RestClientHelper.FormDataEntityBuilder().create().addFormData(field_name,
	 * field_entity, field_media_type).build(); }
	 * 
	 * @author l.biava
	 * 
	 */
	public static class FormDataEntityBuilder {

		public static final MediaType MEDIA_TYPE = MediaType.MULTIPART_FORM_DATA_TYPE;

		private MultipartFormDataOutput mdo;

		// builder methods for setting property
		private FormDataEntityBuilder create() {
			this.mdo = new MultipartFormDataOutput();
			return this;
		}

		public FormDataEntityBuilder addFormData(String key, File file,
				MediaType mediaType) {
			if (this.mdo == null)
				this.create();
			String fileName = file != null ? file.getName() : null;
			this.mdo.addFormData(key, file, mediaType, fileName);
			return this;
		}

		public FormDataEntityBuilder addFormData(String key, Object entity,
				MediaType mediaType) {
			if (this.mdo == null)
				this.create();
			this.mdo.addFormData(key, entity, mediaType);
			return this;
		}

		/**
		 * 
		 * @return the fully built object.
		 */
		public GenericEntity<MultipartFormDataOutput> build() {
			return new GenericEntity<MultipartFormDataOutput>(this.mdo) {
			};
		}
	}

}
