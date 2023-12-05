package com.kokoo.webflux.operator

import reactor.core.publisher.Flux

class TransformingOperator {

    fun buffer(numbers: List<Int>, bufferSize: Int): Flux<List<Int>> {
        return Flux.fromIterable(numbers).buffer(bufferSize)
    }

    fun flatMap(numbers: List<Int>): Flux<Int> {
        return Flux.fromIterable(numbers).flatMap {
            Flux.just(it)
        }
    }

    fun groupBy(strings: List<String>): Flux<String> {
        return Flux.fromIterable(strings).groupBy {
            it
        }.flatMap {
            Flux.just(it.key())
        }
    }

    fun map(numbers: List<Int>): Flux<Int> {
        return Flux.fromIterable(numbers).map {
            it.plus(10)
        }
    }

    fun scan(numbers: List<Int>): Flux<Int> {
        return Flux.fromIterable(numbers).scan { acc, it ->
            acc * it
        }
    }
}