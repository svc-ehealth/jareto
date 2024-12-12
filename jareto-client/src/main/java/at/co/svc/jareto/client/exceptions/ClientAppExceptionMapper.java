package at.co.svc.jareto.client.exceptions;

import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import at.co.svc.jareto.common.exceptions.AppException;
import at.co.svc.jareto.common.exceptions.AppRuntimeException;
import at.co.svc.jareto.common.exceptions.IAppException;

/**
 * Client-side mapper for transforming an incoming {@link AppException} or {@link AppRuntimeException} 
 * from the JAX-RS response. Must be registered by the client to become effective.
 * If the exception contains an entity, its class must be explicitly registered via
 * {@link ClientExceptionMapper#registerEntityClass(Class)}. 
 * 
 * <p>
 * Note: It would be cleaner to define separate exception mappers for these two exception types
 * instead of using {@link Throwable} as generic type. However, this is currently not possible
 * due to a supposed bug in the microprofile implementation of the current RestEasy version.
 * </p>
 */
public class ClientAppExceptionMapper implements ResponseExceptionMapper<Throwable> {

  @Override
  public Throwable toThrowable(Response response) { 
    if (AppException.class.getName().equals(response.getHeaderString(IAppException.HEADER_ERROR_TYPE))) {
      return new AppException(ClientExceptionMapper.readEntity(response), response.getStatus());
    }
    if (AppRuntimeException.class.getName().equals(response.getHeaderString(IAppException.HEADER_ERROR_TYPE))) {
      return new AppRuntimeException(ClientExceptionMapper.readEntity(response), response.getStatus());
    }
    // supposed bug in the currently used RestEasy version: 
    // if the following line is reached, we get a NullPointerException (even though the interface JavaDoc
    // allows returning null); hence, we have to return Throwable instead, and hope that the REST interface
    // does not declare "throws Throwable" (which it shouldn't, anyway)...
    // return null;
    return new Throwable();
  }
  
}
