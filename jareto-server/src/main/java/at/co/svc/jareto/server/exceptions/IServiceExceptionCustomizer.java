package at.co.svc.jareto.server.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.ws.rs.ClientErrorException;

import at.co.svc.jareto.common.exceptions.IAppException;

/**
 * Allows customization of certain aspects when an exception is mapped to wire data.
 */
public interface IServiceExceptionCustomizer {

  /**
   * Returns the HTTP status that shall be used in case of an unexpected error
   * (i.e., when mapping a RuntimeException other than AppRuntimeException).
   */
  default int getUnexpectedStatus() {
    return 500;
  }

  /**
   * Returns the error code that shall be used in case of an unexpected error
   * (i.e., when mapping a RuntimeException other than AppRuntimeException).
   */
  default String getUnexpectedCode() {
    return "UNEXPECTED_ERROR";
  }
  
  /**
   * Returns the error detail code that shall be used in case of an unexpected error
   * (i.e., when mapping a RuntimeException other than AppRuntimeException).
   */
  default String getUnexpectedDetailCode() {
    return null;
  }

  /**
   * Returns the error text that shall be used in case of an unexpected error
   * (i.e., when mapping a RuntimeException other than AppRuntimeException).
   */
  default String getUnexpectedText() {
    return "An unexpected error has occurred.";
  }

  /**
   * Returns the HTTP status that shall be used in case of a generic client error
   * (i.e., when mapping a {@link ClientErrorException}).
   */
  default int getClientErrorStatus(int suggestedStatus) {
    return suggestedStatus;
  }
  
  /**
   * Returns the error code that shall be used in case of a generic client error
   * (i.e., when mapping a {@link ClientErrorException}).
   */
  default String getClientErrorCode() {
    return "CLIENT_ERROR";
  }
  
  /**
   * Returns the error detail code that shall be used in case of a generic client error
   * (i.e., when mapping a {@link ClientErrorException}).
   */
  default String getClientErrorDetailCode() {
    return null;
  }
  
  /**
   * Returns the error text that shall be used in case of a generic client error
   * (i.e., when mapping a {@link ClientErrorException}).
   */
  default String getClientErrorText() {
    return "A client error has occurred.";
  }
  
  /**
   * Invoked before mapping an IAppException.
   * Can be used to customize logging (default: no logging).
   */
  default void appException(IAppException e) {
  }

  /**
   * Invoked before mapping a RuntimeException other than AppRuntimeException.
   * Can be used to customize logging (default: the stack trace is logged with level SEVERE).
   */
  default void runtimeException(RuntimeException e) {
    Logger.getLogger(WebApplicationExceptionFactory.class.getName())
      .log(Level.SEVERE, "mapping a runtime exception", e);
  }
  
  /**
   * Invoked before mapping a ClientErrorException (e.g., due to a "404 Not Found").
   * Can be used to customize logging (default: the exception string is logged with level WARNING).
   */
  default void clientErrorException(ClientErrorException e) {
    Logger.getLogger(WebApplicationExceptionFactory.class.getName())
      .log(Level.WARNING, e.toString());
  }
  
}
