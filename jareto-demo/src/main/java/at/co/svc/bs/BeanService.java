package at.co.svc.bs;

import jakarta.inject.Inject;
import jakarta.ws.rs.HeaderParam;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.co.svc.bs.api.IBeanService;
import at.co.svc.bs.api.IBeanServiceForClient;
import at.co.svc.jareto.client.meta.ClientRequestHeaders;
import at.co.svc.jareto.client.meta.ClientResponse;
import at.co.svc.jareto.common.exceptions.AppException;
import at.co.svc.jareto.common.exceptions.AppRuntimeException;
import at.co.svc.jareto.common.exceptions.IAppException;
import at.co.svc.jareto.server.exceptions.IServiceExceptionCustomizer;
import at.co.svc.jareto.server.exceptions.WebApplicationExceptionFactory;
import at.co.svc.jareto.server.meta.ServiceResponseBuilder;

/**
 * REST service implementation.
 */
public class BeanService implements IBeanService {

  @Inject
  @RestClient
  private IBeanServiceForClient _client;

  // you could also add a HeaderParam as explicit method param if you want to make it explicit
  // to the client (example see below)
  @HeaderParam(IBeanService.X_TEST_HEADER_FROM_CLIENT)
  private String _testHeader;

  @Inject
  private ServiceResponseBuilder _responseBuilder;

  @Override
  public String ping(Integer desiredStatus) {
    // reflect the client header back via server header
    _responseBuilder.get().header(IBeanService.X_TEST_HEADER_FROM_SERVER, _testHeader);
    if (desiredStatus != null) {
      _responseBuilder.get().status(desiredStatus);
    }
    return "pong";
  }

  @Override
  public void exRte() {
    throw new IllegalArgumentException("rte");
  }

  @Override
  public void exMapped() throws AppException {
    throw BeanExceptionFactory.createBeanException();
  }

  @Override
  public void exMappedCustomized() throws AppException {
    WebApplicationExceptionFactory.registerCustomizerThreadLocal(new IServiceExceptionCustomizer() {
      @Override
      public void appException(IAppException e) {
        e.getData().setCode(e.getData().getCode() + "c");
        e.getData().setText(e.getData().getText() + " customized");
      }
    });
    throw BeanExceptionFactory.createBeanException();
  }

  @Override
  public void exMappedRte() throws AppRuntimeException {
    throw BeanExceptionFactory.createBeanRuntimeException();
  }

  @Override
  public String cascade(Integer desiredStatus) {

    // pass through the incoming request header
    ClientRequestHeaders.addHeader(IBeanService.X_TEST_HEADER_FROM_CLIENT, _testHeader);

    String ping = _client.ping(desiredStatus);

    // pass through the incoming response header
    _responseBuilder.get().header(IBeanService.X_TEST_HEADER_FROM_SERVER,
        ClientResponse.CONTEXT.get().getHeaderString(IBeanService.X_TEST_HEADER_FROM_SERVER));

    // pass through the incoming response status
    _responseBuilder.get().status(ClientResponse.CONTEXT.get().getStatus());

    return "cascading " + ping;

  }

  @Override
  public void cascadeExMapped() throws AppException {
    _client.exMapped();
  }

  @Override
  public void cascadeExMappedRte() throws AppRuntimeException {
    _client.exMappedRte();
  }

}