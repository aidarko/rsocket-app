package com.example.consumer.api

import org.springframework.messaging.rsocket.RSocketRequester
import io.rsocket.resume.PeriodicResumeStrategy
import io.rsocket.exceptions.RejectedResumeException
import javax.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.atomic.AtomicReference

@Component
class RSocketRequesterSupplier {

    private val R_SOCKET_REQUESTER = AtomicReference<RSocketRequester>()

    @Autowired
    private lateinit var rSocketRequesterBuilder: RSocketRequester.Builder

    @PostConstruct
    fun init() {
        rSocketRequesterBuilder
                .rsocketFactory { rsocketFactory ->
                    rsocketFactory
                            .errorConsumer { throwable ->
                                if (throwable is RejectedResumeException) {
                                    init()
                                    print("restarting bean")
                                }
                            }
                            .resume()
                            // tune it for your requirements
                            .resumeStreamTimeout(Duration.ofSeconds(1))
                            .resumeStrategy {
                                PeriodicResumeStrategy(
                                        Duration.ofSeconds(1))
                            }
                }
                .connectTcp("localhost", 7000)
                .retryBackoff(Integer.MAX_VALUE.toLong(), Duration.ofSeconds(1))
                .subscribe(R_SOCKET_REQUESTER::set)
    }

    fun get(): RSocketRequester {
        return R_SOCKET_REQUESTER.get()
    }
}