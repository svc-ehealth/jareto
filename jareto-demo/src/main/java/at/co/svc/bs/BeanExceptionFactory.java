package at.co.svc.bs;

import at.co.svc.jareto.common.exceptions.AppException;
import at.co.svc.jareto.common.exceptions.AppRuntimeException;

public class BeanExceptionFactory {

  public static AppException createBeanException() {
    return new AppException(new AppExceptionDataWithBean("12345", "some error text", BeanFactory.createBean()));
  }
  
  public static AppRuntimeException createBeanRuntimeException() {
    return new AppRuntimeException(new AppExceptionDataWithBean("12345", "some error text", BeanFactory.createBean()), null);
  }

}
