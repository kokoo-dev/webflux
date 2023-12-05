package com.kokoo.webflux.operator

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.test.StepVerifier
import java.time.Duration

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreatingOperatorTests {

    private lateinit var creatingOperator: CreatingOperator

    @BeforeAll
    fun beforeAll() {
        creatingOperator = CreatingOperator()
    }

    @Test
    fun just() {
        val number = 1
        val expect = 1

        val actual = creatingOperator.just(number)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun defer() {
        val number = 1
        val expect = 1

        val actual = creatingOperator.defer(number)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun empty() {
        val expect = 0L

        val actual = creatingOperator.empty()

        StepVerifier.create(actual)
                .expectNextCount(expect)
                .verifyComplete()
    }

    @Test
    fun from() {
        val number = 1
        val expect = 1

        val actual = creatingOperator.from(number)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun interval() {
        val period = Duration.ofSeconds(2)
        val takeCount = 2L
        val noEvent = Duration.ofSeconds(1)
        val await = Duration.ofSeconds(1)
        val expect1 = 0L
        val expect2 = 1L

        val actual = creatingOperator.interval(period).take(takeCount)

        StepVerifier.withVirtualTime {
            actual
        }.expectSubscription()
                .expectNoEvent(noEvent)
                .expectNext(expect1)
                .thenAwait(await)
                .expectNext(expect2)
                .verifyComplete();
    }

    @Test
    fun range() {
        val start = 1
        val count = 2
        val expect = 2L

        val actual = creatingOperator.range(start, count)

        StepVerifier.create(actual)
                .expectNextCount(expect)
                .verifyComplete()
    }

    @Test
    fun repeat() {
        val count = 1L
        val expect = 1

        val actual = creatingOperator.repeat(count)

        StepVerifier.withVirtualTime {
            actual
        }.expectSubscription()
                .expectNext(expect)
                .expectNext(expect)
                .verifyComplete()
    }
}