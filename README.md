# spring-rsocket-app

Evaluate RSocket + Spring Boot 2.2 + Kotlin + Gradle

This sample follows https://www.youtube.com/watch?v=BxHqeq58xrE

It contains: single value (Mono) request, stream (Flux) and single error handling.

Projects are run separatly: first producer, then consumer.

Endpoints for HTTP calls: 
* http://localhost:8080/greet/{name}, 
* http://localhost:8080/greet/sse/{name}, 
* http://localhost:8080/greet/error/

## Features implemented:

* Request/response communication
* Stream (bi-directional) communication
* Connection failure tolerance
