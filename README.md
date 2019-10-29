# rsocket-app

Evaluate RSocket Messaging in Spring Boot 2.2 with Kotlin

This sampple follows https://www.youtube.com/watch?v=BxHqeq58xrE

It contains: single value (Mono) request, stream (Flux) and single error handling.

Projects are run separatly: first producer, then consumer.

Endpoints for HTTP calls: 
* http://localhost:8080/greet/{name}, 
* http://localhost:8080/greet/sse/{name}, 
* http://localhost:8080/greet/error/

It lacks: connection failure tolerance
