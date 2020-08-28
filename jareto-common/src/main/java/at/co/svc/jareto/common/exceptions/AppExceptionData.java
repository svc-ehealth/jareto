package at.co.svc.jareto.common.exceptions;

import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * Data to be transported with an application exception (both checked and runtime).
 */
@JsonbPropertyOrder({ "code", "detailCode", "text" })
public class AppExceptionData {

  private String _code;
  private String _detailCode;
  private String _text;
  
  public AppExceptionData() {
  }
  
  public AppExceptionData(String code, String text) {
    _code = code;
    _text = text;
  }
  
  public AppExceptionData(String code, String detailCode, String text) {
    _code = code;
    _detailCode = detailCode;
    _text = text;
  }
  
  /**
   * Error code.
   */
  public String getCode() {
    return _code;
  }

  protected void setCode(String code) {
    _code = code;
  }

  /**
   * Error detail code (optional).
   */
  public String getDetailCode() {
    return _detailCode;
  }

  protected void setDetailCode(String detailCode) {
    _detailCode = detailCode;
  }

  /**
   * Error text.
   */
  public String getText() {
    return _text;
  }

  protected void setText(String text) {
    _text = text;
  }

}
