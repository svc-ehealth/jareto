package at.co.svc.bs;

import java.net.URI;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import at.co.svc.bs.api.IBeanService;
import at.co.svc.jareto.client.exceptions.ClientExceptionMapper;
import at.co.svc.jareto.client.meta.ClientRequestHeaders;
import at.co.svc.jareto.client.meta.ClientResponse;
import at.co.svc.jareto.client.providers.ClientProviders;
import at.co.svc.jareto.common.exceptions.AppException;
import at.co.svc.jareto.common.exceptions.AppRuntimeException;

public class BeanServiceTest {

  private static IBeanService _demoClient;
  
  @BeforeClass
  public static void beforeClass() throws Exception {
    RestClientBuilder builder = RestClientBuilder.newBuilder()
        .baseUri(URI.create(IBeanService.ENDPOINT))
        .register(JacksonJsonProvider.class);
    ClientProviders.registerAll(builder);
    _demoClient = builder.build(IBeanService.class);
    ClientExceptionMapper.registerEntityClass(AppExceptionDataWithBean.class);
  }

  /**
   * Tests transfer of HTTP status.
   */
  @Test
  public void status() throws Exception {
    int desiredStatus = 202;
    String ret = _demoClient.ping(desiredStatus);
    Assert.assertEquals("pong", ret);
    Assert.assertEquals(desiredStatus, ClientResponse.CONTEXT.get().getStatus());
  }
  
  /**
   * Tests transfer of HTTP headers.
   */
  @Test
  public void headers() throws Exception {
    String headerValue = "test-header-value-from-client";
    ClientRequestHeaders.addHeader(IBeanService.X_TEST_HEADER_FROM_CLIENT, headerValue);
    String ret = _demoClient.ping(null);
    Assert.assertEquals(headerValue, ClientResponse.CONTEXT.get().getHeaderString(IBeanService.X_TEST_HEADER_FROM_SERVER));
    Assert.assertEquals("pong", ret);
  }

  /**
   * Tests mapping of a RuntimeException.
   */
  @Test
  public void exRte() throws Exception {
    try {
      _demoClient.exRte();
      Assert.fail("expecting exception");
    }
    catch (AppRuntimeException e) {
      Assert.assertEquals("UNEXPECTED_ERROR", e.getData().getCode());
      Assert.assertEquals("An unexpected error has occurred.", e.getData().getText());
      Assert.assertEquals(500, e.getStatus());
      Assert.assertEquals(500, ClientResponse.CONTEXT.get().getStatus());
    }
  }

  /**
   * Tests mapping of an AppException (checked exception).
   */
  @Test
  public void exMapped() throws Exception {
    try {
      _demoClient.exMapped();
      Assert.fail("expecting exception");
    }
    catch (AppException e) {
      AppException expected = BeanExceptionFactory.createBeanException();
      Assert.assertEquals(expected.getData().getCode(), e.getData().getCode());
      Assert.assertEquals(expected.getData().getText(), e.getData().getText());
      Assert.assertEquals(BeanFactory.createBean(), ((AppExceptionDataWithBean) e.getData()).getBean());
      Assert.assertEquals(expected.getStatus(), e.getStatus());
      Assert.assertEquals(expected.getStatus(), ClientResponse.CONTEXT.get().getStatus());
    }
  }
  
  /**
   * Tests mapping of an AppRuntimeException (runtime exception).
   */
  @Test
  public void exMappedRte() throws Exception {
    try {
      _demoClient.exMappedRte();
      Assert.fail("expecting exception");
    }
    catch (AppRuntimeException e) {
      AppRuntimeException expected = BeanExceptionFactory.createBeanRuntimeException();
      Assert.assertEquals(expected.getData().getCode(), e.getData().getCode());
      Assert.assertEquals(expected.getData().getText(), e.getData().getText());
      Assert.assertEquals(BeanFactory.createBean(), ((AppExceptionDataWithBean) e.getData()).getBean());
      Assert.assertEquals(expected.getStatus(), e.getStatus());
      Assert.assertEquals(expected.getStatus(), ClientResponse.CONTEXT.get().getStatus());
    }
  }
  
  /**
   * Tests a "cascading" request (i.e., one that triggers another request by the server).
   * Makes sure that transfer of HTTP status and headers also works for a client that is used
   * within the server.
   */
  @Test
  public void cascade() throws Exception {
    
    String headerValue = "test-header-value-from-client";
    int desiredStatus = 202;
    
    ClientRequestHeaders.addHeader(IBeanService.X_TEST_HEADER_FROM_CLIENT, headerValue);
    String ret = _demoClient.cascade(desiredStatus);
    
    Assert.assertEquals("cascading pong", ret);
    Assert.assertEquals(desiredStatus, ClientResponse.CONTEXT.get().getStatus());
    Assert.assertEquals(headerValue, ClientResponse.CONTEXT.get().getHeaderString(IBeanService.X_TEST_HEADER_FROM_SERVER));

  }

  /**
   * Tests mapping of a checked exception in a "cascading" request.
   */
  @Test
  public void cascadeExMapped() throws Exception {
    try {
      _demoClient.cascadeExMapped();
      Assert.fail("expecting exception");
    }
    catch (AppException e) {
      AppException expected = BeanExceptionFactory.createBeanException();
      Assert.assertEquals(expected.getData().getCode(), e.getData().getCode());
      Assert.assertEquals(expected.getData().getText(), e.getData().getText());
      Assert.assertEquals(BeanFactory.createBean(), ((AppExceptionDataWithBean) e.getData()).getBean());
      Assert.assertEquals(expected.getStatus(), e.getStatus());
      Assert.assertEquals(expected.getStatus(), ClientResponse.CONTEXT.get().getStatus());
    }
  }

  /**
   * Tests mapping of a runtime exception in a "cascading" request.
   */
  @Test
  public void cascadeMappedRte() throws Exception {
    try {
      _demoClient.cascadeExMappedRte();
      Assert.fail("expecting exception");
    }
    catch (AppRuntimeException e) {
      AppRuntimeException expected = BeanExceptionFactory.createBeanRuntimeException();
      Assert.assertEquals(expected.getData().getCode(), e.getData().getCode());
      Assert.assertEquals(expected.getData().getText(), e.getData().getText());
      Assert.assertEquals(BeanFactory.createBean(), ((AppExceptionDataWithBean) e.getData()).getBean());
      Assert.assertEquals(expected.getStatus(), e.getStatus());
      Assert.assertEquals(expected.getStatus(), ClientResponse.CONTEXT.get().getStatus());
    }
  }
  
}
