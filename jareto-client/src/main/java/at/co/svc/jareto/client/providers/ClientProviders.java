package at.co.svc.jareto.client.providers;

import java.util.HashSet;
import java.util.Set;

import jakarta.ws.rs.core.Configurable;

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
    Set<Class<?>> allProviders = new HashSet<>();
    allProviders.add(ClientRequestHeadersFilter.class);
    allProviders.add(ClientResponseContextFilter.class);
    allProviders.add(ClientAppExceptionMapper.class);
    return allProviders;
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
