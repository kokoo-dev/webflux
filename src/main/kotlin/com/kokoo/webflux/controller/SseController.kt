package com.kokoo.webflux.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.LocalTime


@RestController
@RequestMapping(value = ["/sse"])
class SseController {
    @GetMapping(value = [""], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getFlux(): Flux<String> {
        return Flux.interval(Duration.ofSeconds(3))
            .map { sequence: Long ->
                "Event: $sequence, ${LocalTime.now()}"
            }
            .doOnCancel {
                TODO("disconnected")
            }
    }
}