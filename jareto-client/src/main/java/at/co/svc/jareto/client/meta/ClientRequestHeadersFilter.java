package at.co.svc.jareto.client.meta;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.core.MultivaluedMap;

/**
 * Allows modification of headers sent to the service.
 */
public class ClientRequestHeadersFilter implements ClientRequestFilter {

  private static final Logger LOG = Logger.getLogger(ClientRequestHeadersFilter.class.getName());

  @Override
  public void filter(ClientRequestContext requestContext) throws IOException {
    LOG.info("filtering client request");
    // we clear the previous response context on request
    ClientResponse.CONTEXT.remove();
    MultivaluedMap<String, String> requestHeaders = ClientRequestHeaders.HEADERS.get();
    if (requestHeaders != null) {
      for (Entry<String, List<String>> entry : ClientRequestHeaders.HEADERS.get().entrySet()) {
        for (String value : entry.getValue()) {
          requestContext.getHeaders().add(entry.getKey(), value);
        }
      }
    }
  }

}
