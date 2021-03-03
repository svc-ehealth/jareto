package at.co.svc.jareto.common.exceptions;

import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * Data to be transported with an application exception (both checked and runtime).
 */
@JsonbPropertyOrder({ "code", "detailCode", "text" })
public class AppExceptionData {

  private String code;
  private String detailCode;
  private String text;
  
  public AppExceptionData() {
  }
  
  public AppExceptionData(String code, String text) {
    this.code = code;
    this.text = text;
  }
  
  public AppExceptionData(String code, String detailCode, String text) {
    this.code = code;
    this.detailCode = detailCode;
    this.text = text;
  }
  
  /**
   * Error code.
   */
  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Error detail code (optional).
   */
  public String getDetailCode() {
    return this.detailCode;
  }

  public void setDetailCode(String detailCode) {
    this.detailCode = detailCode;
  }

  /**
   * Error text.
   */
  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
