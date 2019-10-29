package com.example.consumer.api

import io.rsocket.RSocketFactory
import io.rsocket.frame.decoder.PayloadDecoder
import io.rsocket.transport.netty.client.TcpClientTransport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.util.MimeType
import org.springframework.util.MimeTypeUtils

private const val METADATA_MIME_TYPE = "message/x.rsocket.routing.v0"

@Configuration
class Configuration {

    @Bean
    fun rSocket() = RSocketFactory
            .connect()
            .mimeType(METADATA_MIME_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
            .frameDecoder(PayloadDecoder.ZERO_COPY)
            .transport(TcpClientTransport.create(7000))
            .start()
            .block()!!

    @Bean
    fun request(rSocketStrategies: RSocketStrategies): RSocketRequester = RSocketRequester.wrap(
            rSocket(),
            MimeTypeUtils.APPLICATION_JSON,
            MimeType.valueOf(METADATA_MIME_TYPE),
            rSocketStrategies)
}