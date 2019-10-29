package com.example.producer

import java.time.Instant

data class GreetingRequest(val name: String)

class GreetingResponse private constructor(val greeting: String) {
    companion object {
        fun greet(name: String): GreetingResponse {
            val text = "Hello $name @ ${Instant.now()}"
            return GreetingResponse(text)
        }

        fun error() = GreetingResponse("Error")
    }
}