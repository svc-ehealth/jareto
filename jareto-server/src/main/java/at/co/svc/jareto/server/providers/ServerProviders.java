package at.co.svc.jareto.server.providers;

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
    return Set.of(
        ServiceAppExceptionMapper.class, 
        ServiceAppRuntimeExceptionMapper.class, 
        ServiceRuntimeExceptionMapper.class, 
        ServiceResponseFilter.class);
  }
  
}
