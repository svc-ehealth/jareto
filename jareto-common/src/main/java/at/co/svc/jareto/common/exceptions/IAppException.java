package at.co.svc.jareto.common.exceptions;

/**
 * Application exceptions implementing this interface (both checked and runtime exceptions)
 * can be automatically mapped to an HTTP response with appropriate status code, headers
 * and JSON payload.
 */
public interface IAppException {

  /**
   * HTTP header for transporting the exception type.
   */
  String HEADER_ERROR_TYPE = "x-error-type";

  /**
   * HTTP header for transporting the entity type contained inside the exception.
   */
  String HEADER_ERROR_ENTITY_TYPE = "x-error-entity-type";

  /**
   * Exception data to be transported via JSON.
   */
  AppExceptionData getData();

  /**
   * HTTP response status.
   */
  int getStatus();

}
