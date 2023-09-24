package com.kokoo.webflux.operator

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.test.StepVerifier
import java.time.Duration

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreationOperatorTests {

    private lateinit var creationOperator: CreationOperator

    @BeforeAll
    fun beforeAll() {
        creationOperator = CreationOperator()
    }

    @Test
    fun just() {
        val number = 1
        val expect = 1

        val actual = creationOperator.just(number)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun defer() {
        val number = 1
        val expect = 1

        val actual = creationOperator.defer(number)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun empty() {
        val expect = 0L

        val actual = creationOperator.empty()

        StepVerifier.create(actual)
                .expectNextCount(expect)
                .verifyComplete()
    }

    @Test
    fun from() {
        val number = 1
        val expect = 1

        val actual = creationOperator.from(number)

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

        val actual = creationOperator.interval(period).take(takeCount)

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

        val actual = creationOperator.range(start, count)

        StepVerifier.create(actual)
                .expectNextCount(expect)
                .verifyComplete()
    }

    @Test
    fun repeat() {
        val count = 1L
        val expect = 1

        val actual = creationOperator.repeat(count)

        StepVerifier.withVirtualTime {
            actual
        }.expectSubscription()
                .expectNext(expect)
                .expectNext(expect)
                .verifyComplete()
    }
}