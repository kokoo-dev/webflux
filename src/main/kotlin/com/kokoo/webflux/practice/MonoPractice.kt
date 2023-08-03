package com.kokoo.webflux.practice

import com.kokoo.webflux.component.Example
import reactor.core.publisher.Mono

class MonoPractice {

    fun switchIfEmpty(example: Example?): Mono<Example> {
        return Mono.justOrEmpty(example)
                .switchIfEmpty(Mono.defer {
                    Mono.just(Example())
                })
    }

    fun fromSupplier(stringField: String): Mono<Example> {
        return Mono.fromSupplier {
            val example = Example()
            example.stringField = stringField
            example
        }
    }
}