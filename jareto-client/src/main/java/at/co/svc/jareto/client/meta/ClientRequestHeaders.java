package at.co.svc.jareto.client.meta;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Transports HTTP headers to be sent in the client's request to the responsible request filter.
 */
public class ClientRequestHeaders {

  public static final ThreadLocal<MultivaluedMap<String, String>> HEADERS = new ThreadLocal<>();

  /**
   * Adds a header to be sent to the service.
   */
  public static void addHeader(String name, String value) {
    MultivaluedMap<String, String> headers = HEADERS.get();
    if (headers == null) {
      headers = new MultivaluedHashMap<>();
      HEADERS.set(headers);
    }
    headers.add(name, value);
  }

  /**
   * Removes all headers stored before.
   */
  public static void clearHeaders() {
    HEADERS.remove();
  }

}
