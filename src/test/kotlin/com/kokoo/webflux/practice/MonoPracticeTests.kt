package com.kokoo.webflux.practice

import com.kokoo.webflux.component.Example
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import reactor.util.retry.Retry
import java.time.Duration

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MonoPracticeTests {

    private lateinit var monoPractice: MonoPractice

    @BeforeAll
    fun beforeAll() {
        monoPractice = MonoPractice()
    }

    @Test
    fun switchIfEmpty_GetExampleDefault_MonoIsEmpty() {
        val expect = Example()

        val actual: Mono<Example> = monoPractice.switchIfEmpty()

        StepVerifier.create(actual)
            .expectNext(expect)
            .verifyComplete()
    }

    @Test
    fun fromSupplier_GetExample_StringFieldPresent() {
        val stringField = "test"
        val expect = Example(stringField = stringField)

        val actual: Mono<Example> = monoPractice.fromSupplier(stringField)

        StepVerifier.create(actual)
            .expectNext(expect)
            .verifyComplete()
    }

    @Nested
    inner class Error {

        @Test
        fun onErrorReturn_GetExampleDefault_ExceptionIsThrown() {
            val expect = Example()

            val actual: Mono<Example> = monoPractice.onErrorReturn()

            StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
        }

        @Test
        fun onErrorResume_GetExampleDefault_ExceptionIsThrown() {
            val expect = Example()

            val actual: Mono<Example> = monoPractice.onErrorResume()

            StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
        }
    }

    @Nested
    inner class ErrorRetry {

        @Test
        fun retry_GetThreeTryCount_RetryCountIsTwo() {
            val expect = 3

            val actual: Mono<Int> = monoPractice.retry(2)

            StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
        }

        @Test
        fun retryWhen_LessThanRunTime_FixedDelayCountIsTwoAndDurationSecondsIsOne() {
            val retryBackoff = Retry.fixedDelay(2, Duration.ofSeconds(1));
            val expect: Long = 3000

            val actual: Mono<Long> = monoPractice.retryWhen(retryBackoff)

            StepVerifier.create(actual)
                .expectNextMatches {
                    it < expect
                }
                .verifyComplete()
        }

        @Test
        fun retryWhen_LessThanRunTime_BackoffCountIsTwoAndDurationSecondsIsOne() {
            val retryBackoff = Retry.backoff(2, Duration.ofSeconds(1));
            val expect: Long = 5000

            val actual: Mono<Long> = monoPractice.retryWhen(retryBackoff)

            StepVerifier.create(actual)
                .expectNextMatches {
                    it < expect
                }
                .verifyComplete()
        }
    }
}