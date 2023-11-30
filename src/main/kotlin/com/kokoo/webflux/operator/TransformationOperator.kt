package com.kokoo.webflux.operator

import reactor.core.publisher.Flux

class TransformationOperator {

    fun buffer(list: List<Int>, bufferSize: Int): Flux<List<Int>> {
        return Flux.fromIterable(list).buffer(bufferSize)
    }

    fun flatMap(list: List<Int>): Flux<Int> {
        return Flux.fromIterable(list).flatMap {
            Flux.just(it)
        }
    }

    fun groupBy(list: List<String>): Flux<String> {
        return Flux.fromIterable(list).groupBy {
            it
        }.flatMap {
            Flux.just(it.key())
        }
    }

    fun map(list: List<Int>): Flux<Int> {
        return Flux.fromIterable(list).map {
            it.plus(10)
        }
    }

    fun scan(list: List<Int>): Flux<Int> {
        return Flux.fromIterable(list).scan { acc, it ->
            acc * it
        }
    }
}