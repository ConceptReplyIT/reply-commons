package it.reply.utils.web.ws.rest.restclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import it.reply.utils.web.ws.rest.apiencoding.RestMessage;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

public class RestClientImpl extends AbstractRestClient {

	@Override
  public <R> RestMessage<R> doRequest(Request request,
      Class<R> entityClass) throws RestClientException {

    ResteasyClientBuilder cb = new ResteasyClientBuilder();

    Request.Options reqOptions = request.getReqOptions();
    // Socket Timeout
    if (reqOptions != null && reqOptions.getTimeout() > 0) {
      cb.socketTimeout(reqOptions.getTimeout(), TimeUnit.MILLISECONDS);
    } else {
      cb.socketTimeout(this.defaultTimeout, TimeUnit.MILLISECONDS);
    }

    // Proxy
    if (reqOptions != null && reqOptions.getProxy() != null) {
      cb.defaultProxy(reqOptions.getProxy().getHostname(), reqOptions.getProxy().getPort(), reqOptions.getProxy()
          .getProtocol());
    } else {
      if (System.getProperty("java.net.useSystemProxies") != null
          && System.getProperty("java.net.useSystemProxies").equals("true")) {
        try {
          URL url = new URL(request.getURL());
          if (!url.getAuthority().contains("localhost") && !url.getAuthority().contains("127.0.0.1")) {
            cb.defaultProxy("proxy.reply.it", 8080, "HTTP");
          }
        } catch (MalformedURLException e) {
          cb.defaultProxy("proxy.reply.it", 8080, "HTTP");
        }
        // TODO Fix using system properties
        // try {
        // List l=ProxySelector.getDefault().select(new
        // URI("http://foo/bar"));
        // for (Iterator iter = l.iterator(); iter.hasNext();) {
        // java.net.Proxy proxy = (java.net.Proxy) iter.next();
        // System.out.println("proxy hostname : " + proxy.type());
        //
        // InetSocketAddress addr = (InetSocketAddress) proxy.address();
        //
        // if (addr == null) {
        // System.out.println("No Proxy");
        // } else {
        // System.out.println("proxy hostname : " + addr.getHostName());
        // System.setProperty("http.proxyHost", addr.getHostName());
        // System.out.println("proxy port : " + addr.getPort());
        // System.setProperty("http.proxyPort",
        // Integer.toString(addr.getPort()));
        //
        // cb.defaultProxy(addr.getHostName(), addr.getPort());
        // }
        // }
        // } catch (URISyntaxException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
      }
    }
    Client client = null;
    try {
      client = cb.build();
      Response response = null;
      try {
        WebTarget target = client.target(request.getURL());
  
        // Add Query Params
        if (request.getQueryParams() != null) {
          for (MultivaluedMap.Entry<String, List<Object>> entry : request.getQueryParams().entrySet()) {
            Object[] params = null;
            if (entry.getValue() != null) {
              params = entry.getValue().toArray();
            }
            target.queryParam(entry.getKey(), params);
          }
        }
  
        Builder reqBuilder = target.request();
  
        if (request.getHeaders() != null) {
          reqBuilder = reqBuilder.headers(request.getHeaders());
        }
  
        switch (request.getMethod()) {
        case GET:
          if (request.getBody() == null)
            response = reqBuilder.get();
          else
            response = reqBuilder.method("GET", Entity.entity(request.getBody(), request.getBodyMediaType()));
          break;
        case HEAD:
          if (request.getBody() == null)
            response = reqBuilder.head();
          else
            response = reqBuilder.method("HEAD", Entity.entity(request.getBody(), request.getBodyMediaType()));
          break;
        case POST:
          if (request.getBody() == null) {
            response = reqBuilder.method("POST");
          } else
            response = reqBuilder.post(Entity.entity(request.getBody(), request.getBodyMediaType()));
          break;
        case PUT:
          if (request.getBody() == null) {
            response = reqBuilder.method("PUT");
          } else {
            response = reqBuilder.put(Entity.entity(request.getBody(), request.getBodyMediaType()));
          }
          break;
        case DELETE:
          if (request.getBody() == null)
            response = reqBuilder.delete();
          else
            response = reqBuilder.method("DELETE", Entity.entity(request.getBody(), request.getBodyMediaType()));
          break;
        }
  
        RestMessage<R> msg;
        try {
          msg = new RestMessage<R>(response.getHeaders(), response.readEntity(entityClass), response.getStatus());
        } catch (Exception e) {
          msg = new RestMessage<R>(response.getHeaders(), null, response.getStatus());
        }
  
        return msg;
  
      } catch (Exception e) {
        throw new RestClientException(e.getMessage(), e);
      } finally {
        if (response != null)
          response.close();
      }
    } finally {
      if (client != null) {
        client.close();
      }
    }
  }
}
