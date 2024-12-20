package at.co.svc.jareto.server.exceptions;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import at.co.svc.jareto.common.exceptions.AppExceptionData;
import at.co.svc.jareto.common.exceptions.AppRuntimeException;
import at.co.svc.jareto.common.exceptions.IAppException;
import at.co.svc.jareto.server.meta.ServiceResponseFilter;

/**
 * Factory for creating {@link WebApplicationException} from other exceptions.
 */
public class WebApplicationExceptionFactory {

  private static IServiceExceptionCustomizer _customizer = new IServiceExceptionCustomizer() { };
  private static ThreadLocal<IServiceExceptionCustomizer> _threadLocalCustomizer = new ThreadLocal<>();

  /**
   * Registers a static, global customizer to be used when mapping exceptions to wire data.
   * Use {@link WebApplicationExceptionFactory#registerCustomizerThreadLocal(IServiceExceptionCustomizer)} instead if Jareto
   * is installed as application server module, and used for multiple applications with different customizers.
   */
  public static void registerCustomizer(IServiceExceptionCustomizer customizer) {
    _customizer = customizer;
  }

  /**
   * Registers a customizer to be used when mapping exceptions to wire data. Uses a {@link ThreadLocal} to ensure the right
   * customizer is used when sharing Jareto in an application server module over many applications.
   * Thread-local clean-up is performed automatically by {@link ServiceResponseFilter}.
   */
  public static void registerCustomizerThreadLocal(IServiceExceptionCustomizer customizer) {
    _threadLocalCustomizer.set(customizer);
  }

  /**
   * Removes the customizer for exception mapping from the {@link ThreadLocal}.
   */
  public static void removeCustomizerThreadLocal() {
    _threadLocalCustomizer.remove();
  }

  /**
   * Creates a WebApplicationException from the given AppException.
   */
  public static WebApplicationException createWae(IAppException ex) {
    IServiceExceptionCustomizer customizer = getCustomizer();
    customizer.appException(ex);
    return new WebApplicationException(Response
            .status(ex.getStatus())
            .entity(ex.getData())
            .header(IAppException.HEADER_ERROR_TYPE, ex.getClass().getName())
            // transporting all the exception data via headers may appear lean at first, but has disadvantages:
            // - you might need structured data in the future, which is better expressed with JSON
            // - data extraction on the client side is a bit nicer with using Java accessors instead of header key strings
            // - when no entity is provided in the response, JBoss logs the whole stack trace;
            // you would have to provide an exception mapper that catches the exception and converts it
            // into a regular response
            .header(IAppException.HEADER_ERROR_ENTITY_TYPE, ex.getData().getClass().getName())
            .type(MediaType.APPLICATION_JSON)
            .build());
  }

  /**
   * Creates a WebApplicationException from the given RuntimeException.
   */
  public static WebApplicationException createWaeForRte(RuntimeException ex) {
    IServiceExceptionCustomizer customizer = getCustomizer();
    customizer.runtimeException(ex);
    return new WebApplicationException(Response
            .status(customizer.getUnexpectedStatus())
            .entity(new AppExceptionData(
                customizer.getUnexpectedCode(),
                customizer.getUnexpectedDetailCode(),
                customizer.getUnexpectedText()))
            .header(IAppException.HEADER_ERROR_TYPE, AppRuntimeException.class.getName())
            .header(IAppException.HEADER_ERROR_ENTITY_TYPE, AppExceptionData.class.getName())
            .type(MediaType.APPLICATION_JSON)
            .build());
  }

  /**
   * Creates a WebApplicationException from the given ClientErrorException.
   */
  public static WebApplicationException createWaeForClientError(ClientErrorException ex) {
    IServiceExceptionCustomizer customizer = getCustomizer();
    customizer.clientErrorException(ex);
    return new WebApplicationException(Response
            .status(customizer.getClientErrorStatus(ex.getResponse().getStatus()))
            .entity(new AppExceptionData(
                customizer.getClientErrorCode(),
                customizer.getClientErrorDetailCode(),
                customizer.getClientErrorText()))
            .header(IAppException.HEADER_ERROR_TYPE, AppRuntimeException.class.getName())
            .header(IAppException.HEADER_ERROR_ENTITY_TYPE, AppExceptionData.class.getName())
            .type(MediaType.APPLICATION_JSON)
            .build());
  }

  /**
   * Takes the thread-local customizer if one is set, otherwise defaults to the static customizer.
   */
  private static IServiceExceptionCustomizer getCustomizer() {
    IServiceExceptionCustomizer customizer = _threadLocalCustomizer.get();
    if (customizer != null) {
      return customizer;
    }
    else {
      return _customizer;
    }
  }

}
