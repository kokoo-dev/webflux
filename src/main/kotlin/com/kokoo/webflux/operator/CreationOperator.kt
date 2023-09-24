package com.kokoo.webflux.operator

import reactor.core.publisher.Flux
import java.time.Duration

class CreationOperator {

    fun just(number: Int): Flux<Int> {
        return Flux.just(number)
    }

    fun defer(number: Int): Flux<Int> {
        return Flux.defer {
            Flux.just(number)
        }
    }

    fun empty(): Flux<Int> {
        return Flux.empty()
    }

    fun from(number: Int): Flux<Int> {
        return Flux.from(Flux.just(number))
    }

    fun interval(period: Duration): Flux<Long> {
        return Flux.interval(period)
    }

    fun range(start: Int, count: Int): Flux<Int> {
        return Flux.range(start, count)
    }

    fun repeat(count: Long): Flux<Int> {
        return Flux.just(1)
                .repeat(count)
    }
}