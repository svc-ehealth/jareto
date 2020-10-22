package at.co.svc.jareto.common.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Apply this annotation to the REST interface (class or method) to add a static header to the response.
 */
@Repeatable(Header.List.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Header {

  /**
   * Header name.
   */
  String name();
  
  /**
   * Header value.
   */
  String value();
  
  @Target({ElementType.TYPE, ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  @interface List {
    Header[] value();
  }
  
}
