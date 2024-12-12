package at.co.svc.jareto.client.meta;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientResponseContext;
import jakarta.ws.rs.client.ClientResponseFilter;

/**
 * Allows access to the client response context.
 */
public class ClientResponseContextFilter implements ClientResponseFilter {

  private static final Logger LOG = Logger.getLogger(ClientResponseContextFilter.class.getName());

  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
    LOG.info("filtering client response");
    // we clear the request headers on response
    ClientRequestHeaders.HEADERS.remove();
    ClientResponse.CONTEXT.set(responseContext);
  }

}
