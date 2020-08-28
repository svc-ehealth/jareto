package at.co.svc.jareto.common.exceptions;

import javax.ws.rs.core.Response;

/**
 * If you throw this runtime exception, it will be automatically mapped to HTTP wire data 
 * by ServiceAppExceptionMapper.
 */
@SuppressWarnings("serial")
public class AppRuntimeException extends RuntimeException implements IAppException {

  private final AppExceptionData _data;
  private Integer _status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();

  /**
   * Creates an application exception with the given code and text.
   * Status code defaults to 500.
   */
  public AppRuntimeException(String code, String text) {
    this(code, null, text, null);
  }
  
  /**
   * Creates an application exception with the given code, text, and status code.
   * Status code defaults to 500 (if the input parameter is null).
   */
  public AppRuntimeException(String code, String text, Integer status) {
    this(code, null, text, status);
  }
  
  /**
   * Creates an application exception with the given code, detail code, and text.
   * Status code defaults to 500.
   */
  public AppRuntimeException(String code, String detailCode, String text) {
    this(code, detailCode, text, null);
  }

  /**
   * Creates an application exception with the given code, detail code, text, and status code.
   * Status code defaults to 500 (if the input parameter is null).
   */
  public AppRuntimeException(String code, String detailCode, String text, Integer status) {
    this(new AppExceptionData(code, detailCode, text), status);
  }
  
  /**
   * Creates an application exception with the given data.
   * Status code defaults to 500.
   */
  public AppRuntimeException(AppExceptionData data) {
    this(data, null);
  }
  
  /**
   * Creates an application exception with the given data and status code.
   * Status code defaults to 500 (if the input parameter is null).
   */
  public AppRuntimeException(AppExceptionData data, Integer status) {
    _data = data;
    if (status != null) {
      _status = status;
    }
  }

  @Override
  public AppExceptionData getData() {
    return _data;
  }
  
  @Override
  public int getStatus() {
    return _status;
  }
  
}
