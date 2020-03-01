package com.example.consumer.api

import org.reactivestreams.Publisher
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import org.springframework.beans.factory.annotation.Autowired

@RestController
class GreetingRestController {

    @Autowired
    private lateinit var rSocketRequesterSupplier: RSocketRequesterSupplier

    @GetMapping("/greet/{name}")
    fun greet(@PathVariable name: String): Publisher<GreetingResponse> =
            this.rSocketRequesterSupplier
                    .get()
                    .route("greet")
                    .data(GreetingRequest(name))
                    .retrieveMono(GreetingResponse::class.java)

    @GetMapping(produces = [MediaType.TEXT_EVENT_STREAM_VALUE], value = ["/greet/sse/{name}"])
    fun greetStream(@PathVariable name: String): Publisher<GreetingResponse> =
            this.rSocketRequesterSupplier
                    .get()
                    .route("greet-stream")
                    .data(GreetingRequest(name))
                    .retrieveFlux(GreetingResponse::class.java)

    @GetMapping("/greet/error/")
    fun greetError(): Publisher<GreetingResponse> =
            this.rSocketRequesterSupplier
                    .get()
                    .route("greet-error")
                    .data(Mono.empty<GreetingRequest>())
                    .retrieveMono(GreetingResponse::class.java)
}