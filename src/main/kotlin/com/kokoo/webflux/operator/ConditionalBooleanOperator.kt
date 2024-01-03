package com.kokoo.webflux.operator

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class ConditionalBooleanOperator {

    fun all(numbers: List<Int>): Mono<Boolean> {
        return Flux.fromIterable(numbers)
                .all {
                    it < 10
                }
    }

    fun defaultIfEmpty(numbers: List<Int>): Flux<Int> {
        return Flux.fromIterable(numbers)
                .defaultIfEmpty(0)
    }

    fun skipUntil(numbers: List<Int>): Flux<Int> {
        return Flux.fromIterable(numbers)
                .skipUntil {
                    it > 5
                }
    }

    fun skipWhile(numbers: List<Int>): Flux<Int> {
        return Flux.fromIterable(numbers)
                .skipWhile {
                    it != 6
                }
    }

    fun takeUntil(numbers: List<Int>): Flux<Int> {
        return Flux.fromIterable(numbers)
                .takeUntil {
                    it == 0
                }
    }

    fun takeWhile(numbers: List<Int>): Flux<Int> {
        return Flux.fromIterable(numbers)
                .takeWhile {
                    it > 0
                }
    }
}