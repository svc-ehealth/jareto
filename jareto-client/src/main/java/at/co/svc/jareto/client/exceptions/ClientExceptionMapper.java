package at.co.svc.jareto.client.exceptions;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import at.co.svc.jareto.common.exceptions.AppExceptionData;
import at.co.svc.jareto.common.exceptions.IAppException;

/**
 * Contains functions common to all client-side exception mappers.
 */
public final class ClientExceptionMapper {

  private static final Logger LOG = Logger.getLogger(ClientExceptionMapper.class.getName());

  private static Set<Class> _allowedEntityClasses = new HashSet<>();
  
  static {
    _allowedEntityClasses.add(AppExceptionData.class);
  }
  
  private ClientExceptionMapper() {
    // nothing to do
  }
  
  /**
   * Registers the given class so that it can be used for de-serializing an entity contained in
   * an exception response returned by the server. This is necessary to prevent Java deserialization attacks.
   */
  public static void registerEntityClass(Class entityClass) {
    _allowedEntityClasses.add(entityClass);
  }
  
  /**
   * Deserializes an entity from the given Response into the corresponding type.
   */
  static AppExceptionData readEntity(Response response) {
    
    if (!response.hasEntity()) {
      LOG.warning("received application exception without entity");
      return new AppExceptionData();
    }

    Class entityClass = determineEntityClass(response);
    
    try {
      return (AppExceptionData) response.readEntity(entityClass);
    }
    catch (Exception e) {
      LOG.warning(e.getMessage());
      return new AppExceptionData();
    }
    
  }
  
  /**
   * Determine the class that the response entity shall be deserialized to.
   */
  private static Class determineEntityClass(Response response) {
    Class entityClass = null;
    String entityType = response.getHeaderString(IAppException.HEADER_ERROR_ENTITY_TYPE);
    if (entityType != null) {
      try {
        entityClass = Class.forName(entityType);
      }
      catch (ClassNotFoundException e) {
        LOG.warning(e.getMessage());
      }
    }
    if (entityClass != null) {
      if (!_allowedEntityClasses.contains(entityClass)) {
        LOG.warning("unexpected exception entity class, please register if required: " + entityClass);
        entityClass = String.class;
      }
    }
    else {
      // fallback
      entityClass = String.class;
    }
    return entityClass;
  }

}
