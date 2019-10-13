package com.example.consumer

import com.example.consumer.api.GreetingRequest
import com.example.consumer.api.GreetingResponse
import io.rsocket.RSocket
import io.rsocket.RSocketFactory
import io.rsocket.frame.decoder.PayloadDecoder
import io.rsocket.transport.netty.client.TcpClientTransport
import org.reactivestreams.Publisher
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.util.MimeTypeUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class ConsumerApplication

fun main(args: Array<String>) {
    runApplication<ConsumerApplication>(*args)
}

@Bean
fun rSocket() = RSocketFactory
        .connect()
        .dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
        .frameDecoder(PayloadDecoder.ZERO_COPY)
        .transport(TcpClientTransport.create(7000))
        .start()
        .block()!!

@Bean
fun request(rSocketStrategies: RSocketStrategies) = RSocketRequester.wrap(
        rSocket(),
        MimeTypeUtils.APPLICATION_JSON,
        MimeTypeUtils.APPLICATION_JSON,
        rSocketStrategies)

@RestController
class GreetingRestController(val requester: RSocketRequester) {
    @GetMapping("/greet")
    fun greet(@PathVariable name: String): Publisher<GreetingResponse> =
        this.requester
                .route("greet")
                .data(GreetingRequest(name))
				.retrieveMono(GreetingResponse::class.java)
}