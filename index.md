# Jareto - Java REST Tools

## Overview

The following technologies already allow for convenient and efficient development of REST servers and clients:

 * [Java EE](https://www.oracle.com/java/technologies/java-ee-glance.html) / [Jakarta EE 8](https://jakarta.ee/release/)
   * [JAX-RS](https://jcp.org/en/jsr/detail?id=370)
   * [JSON-B](https://jcp.org/en/jsr/detail?id=367) 
 * [Eclipse Microprofile](https://projects.eclipse.org/projects/technology.microprofile)
   * [Microprofile REST client](https://github.com/eclipse/microprofile-rest-client)

However, these technologies currently lack certain features that are often required, forcing the
developer to "re-invent the wheel" or fall back to lower-level technologies 
such as [JSON-P](https://jcp.org/en/jsr/detail?id=374) in combination with `javax.xml.ws.Response`.

**Jareto** provides the following features in an easy-to-use way, for both server- and client-side development:

 * transporting HTTP meta data
   * HTTP status codes
   * HTTP headers
 * mapping Java exceptions
   * server: serialization to HTTP wire data (JSON); these JSON exceptions
     * can also be parsed by non-Java clients
     * are customizable and allow transport of structured data
   * client: de-serialization from HTTP wire data (JSON)

Depending on your requirements, you can use the server-side part of Jareto, or the client-side part, or both.

## Documentation

The `Usage` section below provides a crisp overview of how to apply Jareto's features.

For a gentle introduction, read the Jareto article in Oracle's [Java Magazine](https://blogs.oracle.com/javamagazine/java-jareto-rest-json-http).

## Usage

To use the features provided by Jareto, add the following dependency to your project:

```
    <!-- for using server-side features -->
    <dependency>
      <groupId>at.co.svc.jareto</groupId>
      <artifactId>jareto-server</artifactId>
      <version>1.1.2</version>
    </dependency>
    
    <!-- for using client-side features -->
    <dependency>
      <groupId>at.co.svc.jareto</groupId>
      <artifactId>jareto-client</artifactId>
      <version>1.1.2</version>
    </dependency>

```

Since auto-discovery of providers may cause problems in complex applications, you have to explicitly return the Jareto provider classes 
from the `getClasses` method of your server-side Application class:

```
return at.co.svc.jareto.server.providers.ServerProviders.getAll();
```

Likewise, if you want to use Jareto in a REST client, you have to register the Jareto provider classes during client construction:

```
at.co.svc.jareto.client.providers.ClientProviders.registerAll(builder);
```

For a sample web application (including an in-container REST client and JUnit tests using a standalone REST client),
see `jareto-demo`.

## Transporting HTTP meta data

As a prerequisite, inject the following class to your server code:

```
@Inject
private ServiceResponseBuilder _responseBuilder;
```

To set the **HTTP status code** on the server side:

```
_responseBuilder.get().status(DESIRED_STATUS);
```

To read the **HTTP status code** on the client side:

```
ClientResponse.CONTEXT.get().getStatus();
```

To add an **HTTP header** on the server side:

```
_responseBuilder.get().header(HEADER_NAME, HEADER_VALUE);
```

To add a **static HTTP header** on the server side (via class-level or method-level **annotation** to the REST interface):

```
@Header(name = HEADER_NAME, value = HEADER_VALUE)
```

To read an **HTTP header** on the client side:

```
ClientResponse.CONTEXT.get().getHeaderString(HEADER_NAME);
```

To add an **HTTP header** to a client request:

```
ClientRequestHeaders.addHeader(HEADER_NAME, HEADER_VALUE);
```

*Implementation note*: The client-side part of Jareto uses Java ThreadLocals for managing data, which are also usable in standalone applications (as opposed to @RequestScoped). These ThreadLocals are cleared by the client-side Jareto filters.

## Mapping Java Exceptions

The following exceptions are automatically mapped to and from HTTP wire data by Jareto:

 * `at.co.svc.jareto.common.exceptions.AppException` (checked exception)
 * `at.co.svc.jareto.common.exceptions.AppRuntimeException` (runtime exception)
 * `java.lang.RuntimeException`

To throw a checked exception on the server side:

```
throw new AppException(ERROR_CODE, ERROR_TEXT)
```

The error code and text will be transported as JSON payload. Extended constructors also allow 
transporting additional payload (by extending `at.co.svc.jareto.common.exceptions.AppExceptionData`) 
and explicitly setting the HTTP status code. 

On the client side, the HTTP wire data is automatically transformed back into an AppException.
The error code, text, and HTTP status are accessible via appropriate getters:

```
e.getData().getCode();
e.getData().getText();
e.getStatus();
```

When transporting additional payload, the allowed entity classes must
be registered explicitly as follows (to prevent Java deserialization attacks):

```
ClientExceptionMapper.registerEntityClass(YourEntityHere.class);
```

Certain aspects of the server-side mapping can be customized by defining and registering an
`IServiceExceptionCustomizer`:

```
WebApplicationExceptionFactory.registerCustomizer(new IServiceExceptionCustomizer() {
  // override the methods that you want to customize here  
});
```

## Supported Runtimes

Jareto has been tested with the following Application Servers:

 * Wildfly
 * JBoss EAP

## Release Notes

Version 1.1.3:
 * made compatible also with Java 8 (was: Java 11)

Version 1.1.2:
 * removed field underlines from exceptions (to support fixed JSON property order)

Version 1.1.1:
 * added handling for requests to unknown resources

Version 1.1.0:
 * added @Header annotation

Version 1.0.0:
 * Initial release.
