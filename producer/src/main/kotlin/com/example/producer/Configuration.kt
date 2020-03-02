package com.example.producer

import org.springframework.boot.rsocket.server.ServerRSocketFactoryProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {
    @Bean
    fun serverRSocketFactoryProcessor(): ServerRSocketFactoryProcessor {
        return ServerRSocketFactoryProcessor { it.resume() }
    }
}
