package com.kokoo.webflux.controller

import com.kokoo.webflux.component.Example
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping(value = ["/example"])
class ExampleController {

    @GetMapping(value = ["/mono"])
    fun getMono(): Mono<Example> {

        return Mono.just(Example())
    }

    @GetMapping(value = ["/flux"])
    fun getFlux(): Flux<Example> {

        return Flux.just(Example(), Example(stringField = "not Default", intField = 1, booleanField = true))
    }
}