package at.co.svc.bs.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import at.co.svc.jareto.client.exceptions.ClientAppExceptionMapper;
import at.co.svc.jareto.client.meta.ClientRequestHeadersFilter;
import at.co.svc.jareto.client.meta.ClientResponseContextFilter;
import at.co.svc.jareto.common.exceptions.AppException;
import at.co.svc.jareto.common.exceptions.AppRuntimeException;

/**
 * REST service interface used for injecting a Microprofile REST client.
 */
@Path("v1")
@Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
// the endpoint can also be provided via system property / Microprofile Config
@RegisterRestClient(baseUri = IBeanServiceForClient.ENDPOINT)
@RegisterProvider(ClientRequestHeadersFilter.class)
@RegisterProvider(ClientResponseContextFilter.class)
@RegisterProvider(ClientAppExceptionMapper.class)
public interface IBeanServiceForClient {

  public static final String ENDPOINT = "http://localhost:8080/jareto-demo/";

  /**
   * ENDPOINT/v1/ping 
   */
  @Path("ping")
  @GET
  public String ping(@HeaderParam(IBeanService.X_DESIRED_STATUS_FROM_CLIENT) Integer desiredStatus);
  
  /**
   * ENDPOINT/v1/exMapped
   */
  @Path("exMapped")
  @GET
  void exMapped() throws AppException;

  /**
   * ENDPOINT/v1/exMappedRte
   */
  @Path("exMappedRte")
  @GET
  void exMappedRte() throws AppRuntimeException;

}
