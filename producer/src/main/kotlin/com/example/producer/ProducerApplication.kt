package com.example.producer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import java.time.Instant

@SpringBootApplication
class ProducerApplication

fun main(args: Array<String>) {
    runApplication<ProducerApplication>(*args)
}

data class GreetingRequest(val name: String)

class GreetingResponse private constructor(val greeting: String) {
	companion object {
		fun greet(name: String): GreetingResponse {
			val text = "Hello $name @ ${Instant.now()}"
			return GreetingResponse(text)
		}
	}
}


@Controller
class GreetingRSocketController {
	@MessageMapping("greet")
	fun greet(request: GreetingRequest) = GreetingResponse.greet(request.name)
}