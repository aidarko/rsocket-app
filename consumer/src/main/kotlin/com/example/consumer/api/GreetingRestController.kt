package com.example.consumer.api

import org.reactivestreams.Publisher
import org.springframework.http.MediaType
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingRestController(val requester: RSocketRequester) {
    @GetMapping("/greet/{name}")
    fun greet(@PathVariable name: String): Publisher<GreetingResponse> =
            this.requester
                    .route("greet")
                    .data(GreetingRequest(name))
                    .retrieveMono(GreetingResponse::class.java)

    @GetMapping(produces = [MediaType.TEXT_EVENT_STREAM_VALUE], value = ["/greet/sse/{name}"])
    fun greetStream(@PathVariable name: String): Publisher<GreetingResponse> =
            this.requester
                    .route("greet-stream")
                    .data(GreetingRequest(name))
                    .retrieveFlux(GreetingResponse::class.java)
}