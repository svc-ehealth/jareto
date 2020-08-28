package at.co.svc.jareto.client.providers;

import java.util.Set;

import javax.ws.rs.core.Configurable;

import at.co.svc.jareto.client.exceptions.ClientAppExceptionMapper;
import at.co.svc.jareto.client.meta.ClientRequestHeadersFilter;
import at.co.svc.jareto.client.meta.ClientResponseContextFilter;

/**
 * Convenience class for obtaining all server-side providers at once (for explicit registration).
 */
public final class ClientProviders {

  /**
   * Returns all client-side Jareto providers.
   */
  public static Set<Class<?>> getAll() {
    return Set.of(
      ClientRequestHeadersFilter.class,
      ClientResponseContextFilter.class,
      ClientAppExceptionMapper.class);
  }
  
  /**
   * Registers all client-side Jareto providers with the given REST client.
   */
  public static void registerAll(Configurable client) {
    for (Class<?> provider : getAll()) {
      client.register(provider);
    }
  }

}
