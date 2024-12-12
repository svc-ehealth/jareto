package at.co.svc.jareto.server.exceptions;

import jakarta.inject.Inject;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import at.co.svc.jareto.server.meta.ServiceResponseBuilder;

/**
 * Server-side mapper for transforming an outgoing {@link RuntimeException} into a JAX-RS response ("catch-all").
 * Must be registered by the server to become effective (via auto-detection, or by returning it from 
 * {@link jakarta.ws.rs.core.Application#getClasses()}).
 */
// The {@link Provider} annotation enables auto-detection (only works if classes are not
// provided explicitly by an {@link Application} subclass).
// Not recommended because the developer might want to use only certain features explicitly.
// @Provider
public class ServiceRuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

  @Inject
  private ServiceResponseBuilder _responseBuilder;

  @Override
  public Response toResponse(RuntimeException exception) {
    // tell the response filter that the status has been set based on the thrown exception
    _responseBuilder.get().status(ServiceResponseBuilder.EXCEPTION_STATUS);
    // populate the exception to throw with values from the input exception
    if (exception instanceof ClientErrorException) {
      return WebApplicationExceptionFactory.createWaeForClientError((ClientErrorException) exception).getResponse();
    }
    else {
      return WebApplicationExceptionFactory.createWaeForRte(exception).getResponse();
    }
  }
  
}
