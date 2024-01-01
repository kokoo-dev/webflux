package com.kokoo.webflux.operator

import reactor.core.publisher.Flux
import java.lang.NumberFormatException
import java.lang.RuntimeException

class ErrorHandlingOperator {

    fun onErrorReturn(numbers: List<Int>, errorNumber: Int): Flux<Int> {
        return Flux.fromIterable(numbers)
                .map {
                    if (it == errorNumber) {
                        throw RuntimeException()
                    }
                    it
                }
                .onErrorReturn(0)
    }

    fun onErrorResume(numbers: List<Int>, errorNumber: Int): Flux<Int> {
        return Flux.fromIterable(numbers)
                .map {
                    if (it == errorNumber) {
                        throw NumberFormatException()
                    }
                    it
                }
                .onErrorResume(NumberFormatException::class.java) {
                    Flux.just(0)
                }
    }

    fun onErrorContinue(numbers: List<Int>, errorNumber: Int): Flux<Int> {
        return Flux.fromIterable(numbers)
                .map {
                    if (it == errorNumber) {
                        throw RuntimeException("message")
                    }
                    it
                }
                .onErrorContinue { t, u ->
                    println(t.message)
                }
    }

    // retry
}