package com.kokoo.webflux.operator

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.test.StepVerifier

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConditionalBooleanOperatorTests {

    private lateinit var conditionalBooleanOperator: ConditionalBooleanOperator

    @BeforeAll
    fun beforeAll() {
        conditionalBooleanOperator = ConditionalBooleanOperator()
    }

    @Test
    fun all() {
        val numbers = listOf(5, 2, 3, 10)
        val expect = false

        val actual = conditionalBooleanOperator.all(numbers)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun defaultIfEmpty() {
        val numbers = emptyList<Int>()
        val expect = 0

        val actual = conditionalBooleanOperator.defaultIfEmpty(numbers)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun skipUntil() {
        val numbers = listOf(4, 1, 0, 5, 2, 3, 6, 1, 2)
        val expect1 = 6
        val expect2 = 1
        val expect3 = 2

        val actual = conditionalBooleanOperator.skipUntil(numbers)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .expectNext(expect3)
                .verifyComplete()
    }

    @Test
    fun skipWhile() {
        val numbers = listOf(4, 1, 0, 5, 2, 3, 6, 1, 2)
        val expect1 = 6
        val expect2 = 1
        val expect3 = 2

        val actual = conditionalBooleanOperator.skipWhile(numbers)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .expectNext(expect3)
                .verifyComplete()
    }

    @Test
    fun takeUntil() {
        val numbers = listOf(4, 1, 0, 5, 2, 3, 6, 1, 2)
        val expect1 = 4
        val expect2 = 1
        val expect3 = 0

        val actual = conditionalBooleanOperator.takeUntil(numbers)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .expectNext(expect3)
                .verifyComplete()
    }

    @Test
    fun takeWhile() {
        val numbers = listOf(4, 1, 0, 5, 2, 3, 6, 1, 2)
        val expect1 = 4
        val expect2 = 1

        val actual = conditionalBooleanOperator.takeWhile(numbers)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .verifyComplete()
    }
}