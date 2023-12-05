package com.kokoo.webflux.operator

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

class FilteringOperator {

    fun distinct(numbers: List<Int>): Flux<Int> {
        return Flux.fromIterable(numbers).distinct()
    }

    fun elementAt(numbers: List<Int>, index: Int): Mono<Int> {
        return Flux.fromIterable(numbers).elementAt(index)
    }

    fun filter(numbers: List<Int>): Flux<Int> {
        return Flux.fromIterable(numbers).filter {
            it % 2 == 0
        }
    }

    fun ignoreElements(numbers: List<Int>): Mono<Int> {
        return Flux.fromIterable(numbers).ignoreElements()
    }

    fun last(numbers: List<Int>): Mono<Int> {
        return Flux.fromIterable(numbers).last()
    }

    fun sample(numbers: List<Int>, timeSpan: Duration): Flux<Int> {
        return Flux.fromIterable(numbers).sample(timeSpan)
    }

    fun skip(numbers: List<Int>, skipCount: Long): Flux<Int> {
        return Flux.fromIterable(numbers).skip(skipCount)
    }

    fun skipLast(numbers: List<Int>, skipCount: Int): Flux<Int> {
        return Flux.fromIterable(numbers).skipLast(skipCount)
    }

    fun take(numbers: List<Int>, takeCount: Long): Flux<Int> {
        return Flux.fromIterable(numbers).take(takeCount)
    }

    fun takeLast(numbers: List<Int>, takeCount: Int): Flux<Int> {
        return Flux.fromIterable(numbers).takeLast(takeCount)
    }
}