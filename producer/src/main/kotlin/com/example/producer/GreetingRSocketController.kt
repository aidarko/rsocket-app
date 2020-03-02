package com.example.producer

import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import java.lang.IllegalArgumentException
import java.time.Duration
import java.util.stream.Stream

@Controller
class GreetingRSocketController {
    @MessageMapping("greet")
    fun greet(request: GreetingRequest) = GreetingResponse.greet(request.name)

    @MessageMapping("greet-stream")
    fun greetStream(request: GreetingRequest): Flux<GreetingResponse> {
        return Flux
                .fromStream(Stream.generate { GreetingResponse.greet(request.name) })
                .delayElements(Duration.ofSeconds(1))
    }

    @MessageMapping("greet-error")
    fun greetError(request: GreetingRequest) =
    Flux.error<GreetingResponse>(IllegalArgumentException())

    @MessageExceptionHandler
    fun handleException(e: Exception): Flux<GreetingResponse> {
        return Flux.just(GreetingResponse.error())
    }
}
