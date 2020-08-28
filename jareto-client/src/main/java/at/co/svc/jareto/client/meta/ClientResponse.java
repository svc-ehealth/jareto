package at.co.svc.jareto.client.meta;

import javax.ws.rs.client.ClientResponseContext;

/**
 * Makes the client response from the response filter accessible to the client application.
 */
public class ClientResponse {

  public static final ThreadLocal<ClientResponseContext> CONTEXT = new ThreadLocal<>();
  
}
