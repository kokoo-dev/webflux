package com.kokoo.webflux.practice

import com.kokoo.webflux.component.Example
import mu.KotlinLogging
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class FluxPractice {

    private val log = KotlinLogging.logger {}

    fun switchIfEmpty(): Flux<Example> {
        return Flux.empty<Example>()
                .switchIfEmpty(Flux.defer {
                    Flux.just(Example())
                })
    }

    fun concatWith(flux1: Flux<Example>, flux2: Flux<Example>): Flux<Example> {
        return flux1.concatWith(flux2)
    }
}