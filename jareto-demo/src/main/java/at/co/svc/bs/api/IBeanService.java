package at.co.svc.bs.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import at.co.svc.jareto.common.exceptions.AppException;
import at.co.svc.jareto.common.exceptions.AppRuntimeException;
import at.co.svc.jareto.common.meta.Header;
import at.co.svc.jareto.server.exceptions.ServiceAppExceptionMapper;

/**
 * REST service interface.
 */
@Path("v1")
@Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
@Header(name = IBeanService.X_TEST_HEADER_FROM_SERVER_CLASS, value = "test-class")
public interface IBeanService {

  public static final String ENDPOINT = "http://localhost:8080/jareto-demo/";

  /**
   * Some custom header that the service accepts from the client.
   */
  public static final String X_TEST_HEADER_FROM_CLIENT = "x-test-header-from-client";

  /**
   * Test header sent by the client transporting the desired response status code.
   */
  public static final String X_DESIRED_STATUS_FROM_CLIENT = "x-desired-status-from-client";

  /**
   * Some custom header that the service returns to the client.
   */
  public static final String X_TEST_HEADER_FROM_SERVER = "x-test-header-from-server";

  /**
   * Some custom header that the service returns to the client via class-level annotation.
   */
  public static final String X_TEST_HEADER_FROM_SERVER_CLASS = "x-test-header-from-server-class";

  /**
   * Some custom header that the service returns to the client via method-level annotation.
   */
  public static final String X_TEST_HEADER_FROM_SERVER_METHOD = "x-test-header-from-server-method";

  /**
   * ENDPOINT/v1/ping
   */
  @Path("ping")
  @GET
  @Header(name = IBeanService.X_TEST_HEADER_FROM_SERVER_METHOD, value = "test-method")
  public String ping(@HeaderParam(IBeanService.X_DESIRED_STATUS_FROM_CLIENT) Integer desiredStatus);

  /**
   * ENDPOINT/v1/exRte
   *
   * Throws a RuntimeException. Bad if not handled globally (Wildfly returns an HTML page with stack trace).
   */
  @Path("exRte")
  @GET
  void exRte();

  /**
   * ENDPOINT/v1/exMapped
   *
   * Throws an {@link AppException}, which is mapped by {@link ServiceAppExceptionMapper}.
   */
  @Path("exMapped")
  @GET
  void exMapped() throws AppException;

  /**
   * ENDPOINT/v1/exMappedCustomized
   *
   * Throws a customized {@link AppException}, which is mapped by {@link ServiceAppExceptionMapper}.
   */
  @Path("exMappedCustomized")
  @GET
  void exMappedCustomized() throws AppException;

  /**
   * ENDPOINT/v1/exMappedRte
   *
   * Throws an {@link AppRuntimeException}, which is mapped by {@link ServiceAppExceptionMapper}.
   */
  @Path("exMappedRte")
  @GET
  void exMappedRte() throws AppRuntimeException;

  /**
   * ENDPOINT/v1/cascade
   */
  @Path("cascade")
  @GET
  public String cascade(@HeaderParam(IBeanService.X_DESIRED_STATUS_FROM_CLIENT) Integer desiredStatus);

  /**
   * ENDPOINT/v1/exMapped
   *
   * Throws an {@link AppException}, which is mapped by {@link ServiceAppExceptionMapper}.
   */
  @Path("cascadeExMapped")
  @GET
  void cascadeExMapped() throws AppException;

  /**
   * ENDPOINT/v1/exMappedRte
   *
   * Throws an {@link AppRuntimeException}, which is mapped by {@link ServiceAppExceptionMapper}.
   */
  @Path("cascadeExMappedRte")
  @GET
  void cascadeExMappedRte() throws AppRuntimeException;

}
