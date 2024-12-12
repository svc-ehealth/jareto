package at.co.svc.jareto.server.meta;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

/**
 * Allows returning custom HTTP headers and status codes in the response
 * (via {@link ServiceResponseFilter}).
 */
@RequestScoped
public class ServiceResponseBuilder {

  private ResponseBuilder _builder;
  
  static final int INITIAL_STATUS = 9999;
  
  /** Indicates that the status has been set explicitly due to a thrown exception. */
  public static final int EXCEPTION_STATUS = 7777;
  
  @PostConstruct
  public void init() {
    _builder = Response.status(INITIAL_STATUS);
  }

  public ResponseBuilder get() {
    return _builder;
  }
  
}
