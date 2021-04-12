package at.co.svc.jareto.server.providers;

import java.util.HashSet;
import java.util.Set;

import at.co.svc.jareto.server.exceptions.ServiceAppExceptionMapper;
import at.co.svc.jareto.server.exceptions.ServiceAppRuntimeExceptionMapper;
import at.co.svc.jareto.server.exceptions.ServiceRuntimeExceptionMapper;
import at.co.svc.jareto.server.meta.ServiceResponseFilter;

/**
 * Convenience class for explicit provider registration.
 */
public final class ServerProviders {

  /**
   * Returns all server-side Jareto providers.
   */
  public static Set<Class<?>> getAll() {
    Set<Class<?>> allProviders = new HashSet<>();
    allProviders.add(ServiceAppExceptionMapper.class);
    allProviders.add(ServiceAppRuntimeExceptionMapper.class);
    allProviders.add(ServiceRuntimeExceptionMapper.class);
    allProviders.add(ServiceResponseFilter.class);
    return allProviders;
  }
  
}
