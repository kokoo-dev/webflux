package com.kokoo.webflux.operator

import reactor.core.publisher.Flux
import java.time.Duration

class CombiningOperator {

    fun withLatestFrom(numbers: List<Int>, strings: List<String>): Flux<String> {
        val flux1 = Flux.fromIterable(numbers).delayElements(Duration.ofMillis(800))
        val flux2 = Flux.fromIterable(strings).delayElements(Duration.ofMillis(1000))
        return flux1.withLatestFrom(flux2) { t1, t2 ->
            "$t1$t2"
        }
    }

    fun startWith(numbers: List<Int>, startWithNumber: Int): Flux<Int> {
        return Flux.fromIterable(numbers).startWith(startWithNumber)
    }

    fun mergeWith(firstNumbers: List<Int>, secondNumbers: List<Int>): Flux<Int> {
        val firstFlux = Flux.fromIterable(firstNumbers)
        val secondFlux = Flux.fromIterable(secondNumbers)

        return firstFlux.mergeWith(secondFlux)
    }

    fun zip(firstStrings: List<String>, secondStrings: List<String>, thirdStrings: List<String>): Flux<String> {
        return Flux.zip(Flux.fromIterable(firstStrings), Flux.fromIterable(secondStrings), Flux.fromIterable(thirdStrings)).map {
            "${it.t1}${it.t2}${it.t3}"
        }
    }
}