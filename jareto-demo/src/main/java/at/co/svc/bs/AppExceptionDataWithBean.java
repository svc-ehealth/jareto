package at.co.svc.bs;

import javax.json.bind.annotation.JsonbPropertyOrder;

import at.co.svc.jareto.common.exceptions.AppExceptionData;

/**
 * Custom, extended data to be transported along with an exception.
 */
@JsonbPropertyOrder({ "code", "detailCode", "text", "bean" })
public class AppExceptionDataWithBean extends AppExceptionData {

  private Bean _bean;

  public AppExceptionDataWithBean() {
  }
      
  public AppExceptionDataWithBean(String code, String text, Bean bean) {
    super(code, text);
    setBean(bean);
  }

  public Bean getBean() {
    return _bean;
  }

  public void setBean(Bean bean) {
    _bean = bean;
  }
  
}