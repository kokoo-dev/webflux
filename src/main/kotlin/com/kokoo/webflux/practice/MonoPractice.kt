package com.kokoo.webflux.practice

import com.kokoo.webflux.component.Example
import mu.KotlinLogging
import org.springframework.util.StopWatch
import reactor.core.publisher.Mono
import reactor.util.retry.Retry

class MonoPractice {

    private val log = KotlinLogging.logger {}

    fun switchIfEmpty(): Mono<Example> {
        return Mono.empty<Example>()
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

    fun retry(retryCount: Long): Mono<Int> {
        var triedCount = 0

        return Mono.fromSupplier {
            triedCount++
            throw RuntimeException()
            triedCount
        }.retry(retryCount)
            .onErrorResume {
                Mono.just(triedCount)
            }
    }

    fun retryWhen(retry: Retry): Mono<Long> {
        val stopWatch = StopWatch()
        stopWatch.start()

        return Mono.fromSupplier {
            throw RuntimeException()
            0L
        }.retryWhen(retry)
            .onErrorResume {
                stopWatch.stop()

                log.info("stop watch :: ${stopWatch.lastTaskTimeMillis}")

                Mono.just(stopWatch.lastTaskTimeMillis)
            }
    }
}