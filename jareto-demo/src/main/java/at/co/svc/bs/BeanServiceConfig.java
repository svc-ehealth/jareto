package at.co.svc.bs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import at.co.svc.jareto.client.exceptions.ClientExceptionMapper;
import at.co.svc.jareto.server.providers.ServerProviders;

/**
 * Minimum required application configuration.
 */
@ApplicationPath("")
public class BeanServiceConfig extends Application {

  /**
   * Extending this method is only necessary if you don't want the container to perform auto-discovery
   * (slightly more cumbersome, but potentially better for security and startup performance). 
   */
  @Override
  public Set<Class<?>> getClasses() {
    initExceptionMapping();
    Set<Class<?>> classes = new HashSet<Class<?>>();
    classes.addAll(getServiceClasses());
    classes.addAll(getProviderClasses());
    return classes;
  }
  
  private Set<Class<?>> getServiceClasses() {
    return Set.of(BeanService.class);
  }
  
  private Set<Class<?>> getProviderClasses() {
    return ServerProviders.getAll();
  }
  
  private void initExceptionMapping() {
    
    // the following registration is necessary for IBeanServiceForClient (de-serializing custom entities in exceptions)
    ClientExceptionMapper.registerEntityClass(AppExceptionDataWithBean.class);
    
    // demo for customizing the exception mapping
    /*
    WebApplicationExceptionFactory.registerCustomizer(new IServiceExceptionCustomizer() {
      
      public String getUnexpectedCode() {
        return "UNEXPECTED_ERROR_CUSTOMIZED";
      }
      
      public String getUnexpectedText() {
        return "An unexpected error has occurred - customized text.";
      }
      
      public void appException(IAppException e) {
        System.out.println("+++++++++++++ appException");
      }

      public void runtimeException(RuntimeException e) {
        System.out.println("+++++++++++++ runtimeException");
      }
  
    });
    */
    
  }
  
}
