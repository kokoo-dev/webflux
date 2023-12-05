package com.kokoo.webflux.operator

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.test.StepVerifier
import java.time.Duration

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FilteringOperatorTests {

    private lateinit var filteringOperator: FilteringOperator

    @BeforeAll
    fun beforeAll() {
        filteringOperator = FilteringOperator()
    }

    @Test
    fun distinct() {
        val numbers = listOf(1, 2, 1, 3, 4, 2)
        val expect1 = 1
        val expect2 = 2
        val expect3 = 3
        val expect4 = 4

        val actual = filteringOperator.distinct(numbers)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .expectNext(expect3)
                .expectNext(expect4)
                .verifyComplete()
    }

    @Test
    fun elementAt() {
        val numbers = listOf(1, 2, 3, 4)
        val index = 1
        val expect = 2

        val actual = filteringOperator.elementAt(numbers, index)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun filter() {
        val numbers = listOf(1, 2, 3, 4)
        val expect1 = 2
        val expect2 = 4

        val actual = filteringOperator.filter(numbers)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .verifyComplete()
    }

    @Test
    fun ignoreElements() {
        val numbers = listOf(1, 2, 3, 4, 5, 6)
        val expect = 0L

        val actual = filteringOperator.ignoreElements(numbers)

        StepVerifier.create(actual)
                .expectNextCount(expect)
                .verifyComplete()
    }

    @Test
    fun last() {
        val numbers = listOf(1, 2, 3, 4)
        val expect = 4

        val actual = filteringOperator.last(numbers)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun sample() {
        val numbers = listOf(1, 2, 3, 4)
        val timeSpan = Duration.ofSeconds(1)
        val expect = 4

        val actual = filteringOperator.sample(numbers, timeSpan)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun skip() {
        val numbers = listOf(1, 2, 3, 4)
        val skipCount = 2L
        val expect1 = 3
        val expect2 = 4

        val actual = filteringOperator.skip(numbers, skipCount)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .verifyComplete()
    }

    @Test
    fun skipLast() {
        val numbers = listOf(1, 2, 3, 4)
        val skipCount = 2
        val expect1 = 1
        val expect2 = 2

        val actual = filteringOperator.skipLast(numbers, skipCount)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .verifyComplete()
    }

    @Test
    fun take() {
        val numbers = listOf(1, 2, 3, 4)
        val takeCount = 2L
        val expect1 = 1
        val expect2 = 2

        val actual = filteringOperator.take(numbers, takeCount)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .verifyComplete()
    }

    @Test
    fun takeLast() {
        val numbers = listOf(1, 2, 3, 4)
        val takeCount = 2
        val expect1 = 3
        val expect2 = 4

        val actual = filteringOperator.takeLast(numbers, takeCount)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .verifyComplete()
    }
}