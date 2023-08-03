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

    fun onErrorReturn(): Mono<Example> {
        return Mono.fromSupplier {
            throw RuntimeException()
            Example()
        }.onErrorReturn(Example())
    }

    fun onErrorResume(): Mono<Example> {
        return Mono.fromSupplier {
            throw RuntimeException()
            Example()
        }.onErrorResume {
            Mono.just(Example())
        }
    }

    fun onErrorResumeAndRetry(retryCount: Long): Mono<Int> {
        var triedCount = 0

        return Mono.fromSupplier {
            triedCount++
            throw RuntimeException()
            0
        }.retry(retryCount)
            .onErrorResume {
                Mono.just(triedCount)
            }
    }
}