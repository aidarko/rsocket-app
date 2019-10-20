package com.example.producer

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
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
}