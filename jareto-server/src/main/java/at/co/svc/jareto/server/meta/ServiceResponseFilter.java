package at.co.svc.jareto.server.meta;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import at.co.svc.jareto.common.meta.Header;

/**
 * Allows returning custom HTTP headers and status codes in the response
 * (using {@link ServiceResponseBuilder} or {@link Header}).
 */
// The {@link Provider} annotation enables auto-detection (only works if classes are not
// provided explicitly by an {@link Application} subclass).
// Not recommended because the developer might want to use only certain features explicitly.
// @Provider
public class ServiceResponseFilter implements ContainerResponseFilter {

  @Inject
  private ServiceResponseBuilder _builder;
  
  @Context
  private ResourceInfo _resourceInfo;

  private static final Logger LOG = Logger.getLogger(ServiceResponseFilter.class.getName());

  @Override
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
      throws IOException {
    
    LOG.info("filtering service response");
    
    Response response = _builder.get().build();

    // transfer headers that have been set via class-level annotations
    Class<?> resourceClass = _resourceInfo.getResourceClass();
    if (resourceClass != null) {
      for (Header header : resourceClass.getAnnotationsByType(Header.class)) {
        responseContext.getHeaders().add(header.name(), header.value());
      }
    }
    
    // transfer headers that have been set via method-level annotations
    Method resourceMethod = _resourceInfo.getResourceMethod();
    if (resourceMethod != null) {
      for (Header header : resourceMethod.getAnnotationsByType(Header.class)) {
        responseContext.getHeaders().add(header.name(), header.value());
      }
    }

    // transfer headers that have been set via ServiceResponseBuilder
    for (Entry<String, List<Object>> entry : response.getHeaders().entrySet()) {
      responseContext.getHeaders().addAll(entry.getKey(), entry.getValue());
    }

    // transfer status code
    if (response.getStatus() != ServiceResponseBuilder.INITIAL_STATUS
        && response.getStatus() != ServiceResponseBuilder.EXCEPTION_STATUS) {
      responseContext.setStatus(response.getStatus());
    }
    
  }

}
