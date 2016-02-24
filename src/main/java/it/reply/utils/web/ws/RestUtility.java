package it.reply.utils.web.ws;
//package it.prisma.utils.web.ws;
//
//import it.prisma.util.json.JsonUtility;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Map;
//
//import javax.ws.rs.core.MultivaluedMap;
//
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.WebResource.Builder;
//
///**
// * RestUtility class that provides facilities in order to use REST services.
// * 
// * @author <a href="mailto:g.demusso@reply.it">Giulio Vito de Musso</a>
// * @author <a href="mailto:v.denotaris@reply.it">Vincenzo De Notaris</a>
// * @version 0.1.0
// * @since 0.1.0
// * @see <a
// *      href="http://www.ponsmartcities-prisma.it/">Progetto PRISMA</a>
// */
//public class RestUtility {
//
//	/**
//	 * Static method that makes a request REST with HTTP GET method to
//	 * <b>URL</b> adding <b>header</b> to request.
//	 * 
//	 * @param URL          
//     *            The URL to which to make a REST request.
//	 * @param headers
//	 *            Map containing request headers (key) and their
//	 *            values(value).
//	 * @param outClass
//	 *            Class of return. Is instantiated an object of class
//	 *            <b>outClass</b> in which are inserted received data from
//	 *            service.
//	 * @return Returns a class instance <b>outClass</b> containing 
//	 *         received data from service.
//	 * @throws IOException
//	 * @throws JsonMappingException
//	 * @throws JsonParseException
//	 * @throws RuntimeException
//	 */
//	public static <T> Object get(String URL, Map<String, String> headers,
//			Class<T> outClass) throws JsonParseException, JsonMappingException,
//			IOException, RuntimeException {
//
//		String output = "";
//		Object outputObj = null;
//
//		Client client = Client.create();
//		WebResource webResource = client.resource(URL);
//		Builder webResourceBuilder = webResource.getRequestBuilder();
//		for (Map.Entry<String, String> header : headers.entrySet()) {
//			webResourceBuilder.header(header.getKey(), header.getValue());
//		}
//
//		ClientResponse response = webResourceBuilder.get(ClientResponse.class);
//		if (response.getStatus() / 200 != 1) {
//			throw new RuntimeException("Failed : HTTP error code : "
//					+ response.getStatus());
//		}
//		output = response.getEntity(String.class);
//		outputObj = (Object) JsonUtility.deserializeJson(output, outClass);
//		return outputObj;
//	}
//
//	/**
//	 * Static method that makes a request REST with HTTP POST method to
//	 * <b>URL</b> adding <b>header</b> to request and JSON string 
//	 * <b>jsonBody</b> like body.
//	 * 
//	 * @param URL
//	 *            The URL to which to make a REST request.
//	 * @param requestHeaders
//	 *            Map containing request headers (key) and their
//	 *            values(value).
//	 * @param jsonBody
//	 *            JSON string to use like body of the REST POST request
//	 * @param outClass
//	 *            Class of return. Is instantiated an object of class
//	 *            <b>outClass</b> in which are inserted received data from
//	 *            service.
//	 * @param responseHeaders
//	 *             Map containing response headers 
//	 * @return Returns a class instance <b>outClass</b> containing 
//	 *         received data from service.
//	 * @throws IOException
//	 * @throws JsonMappingException
//	 * @throws JsonParseException
//	 * @throws RuntimeException
//	 */
//	public static <T> Object post(String URL,
//			Map<String, String> requestHeaders, String jsonBody,
//			Class<T> outClass, MultivaluedMap<String, String> responseHeaders)
//			throws JsonParseException, JsonMappingException, IOException,
//			RuntimeException {
//
//		String output = "";
//		Object outputObj = null;
//
//		Client client = Client.create();
//		WebResource webResource = client.resource(URL);
//		Builder webResourceBuilder = webResource.getRequestBuilder();
//		
//		for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
//			webResourceBuilder.header(header.getKey(), header.getValue());
//		}
//		
//		webResourceBuilder.entity(jsonBody);
//		ClientResponse response = webResourceBuilder.post(ClientResponse.class);
//		
//		if (response.getStatus() / 200 != 1) {
//			throw new RuntimeException("Failed : HTTP error code : "
//					+ response.getStatus());
//		}
//		
//		output = response.getEntity(String.class);
//		MultivaluedMap<String, String> respHeaders = response.getHeaders();
//
//		for (String key : new ArrayList<String>(respHeaders.keySet())) {
//			ArrayList<String> values = new ArrayList<String>();
//			for (String value : respHeaders.get(key))
//				values.add(value);
//			responseHeaders.put(key, values);
//		}
//		outputObj = (Object) JsonUtility.deserializeJson(output, outClass);
//		return outputObj;
//	}
//	
//}