package at.co.svc.bs;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

import at.co.svc.jareto.common.exceptions.AppExceptionData;

/**
 * Custom, extended data to be transported along with an exception.
 * Note that we add explicitly annotated getters and setters for the fields from the parent class
 * to make sure that they are serialized by every library implementing JSON-B.
 */
@JsonbPropertyOrder({ "code", "detailCode", "text", "bean" })
public class AppExceptionDataWithBean extends AppExceptionData {

  private Bean bean;

  public AppExceptionDataWithBean() {
  }
      
  public AppExceptionDataWithBean(String code, String text, Bean bean) {
    super(code, text);
    setBean(bean);
  }

  @JsonbProperty
  public Bean getBean() {
    return this.bean;
  }

  @JsonbProperty
  public void setBean(Bean bean) {
    this.bean = bean;
  }

  @Override
  @JsonbProperty
  public String getCode() {
    return super.getCode();
  }

  @Override
  @JsonbProperty
  public void setCode(String code) {
    super.setCode(code);
  }

  @Override
  @JsonbProperty
  public String getDetailCode() {
    return super.getDetailCode();
  }

  @Override
  @JsonbProperty
  public void setDetailCode(String detailCode) {
    super.setDetailCode(detailCode);
  }

  @Override
  @JsonbProperty
  public String getText() {
    return super.getText();
  }

  @Override
  @JsonbProperty
  public void setText(String text) {
    super.setText(text);
  }

}
