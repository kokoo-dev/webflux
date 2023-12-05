package com.kokoo.webflux.operator

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.test.StepVerifier

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransformingOperatorTests {

    private lateinit var transformingOperator: TransformingOperator

    @BeforeAll
    fun beforeAll() {
        transformingOperator = TransformingOperator()
    }

    @Test
    fun buffer() {
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val bufferSize = 3
        val expect1 = listOf(1, 2, 3)
        val expect2 = listOf(4, 5, 6)
        val expect3 = listOf(7, 8, 9)

        val actual = transformingOperator.buffer(numbers, bufferSize)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .expectNext(expect3)
                .verifyComplete()
    }

    @Test
    fun flatMap() {
        val numbers = listOf(1, 2, 3)
        val expect1 = 1
        val expect2 = 2
        val expect3 = 3

        val actual = transformingOperator.flatMap(numbers)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .expectNext(expect3)
                .verifyComplete()
    }

    @Test
    fun groupBy() {
        val strings = listOf("group1", "group1", "group2")
        val expect1 = "group1"
        val expect2 = "group2"

        val actual = transformingOperator.groupBy(strings)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .verifyComplete()
    }

    @Test
    fun map() {
        val numbers = listOf(1, 2, 3)
        val expect1 = 11
        val expect2 = 12
        val expect3 = 13

        val actual = transformingOperator.map(numbers)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .expectNext(expect3)
                .verifyComplete()
    }

    @Test
    fun scan() {
        val numbers = listOf(1, 2, 3, 4)
        val expect1 = 1
        val expect2 = 2
        val expect3 = 6
        val expect4 = 24

        val actual = transformingOperator.scan(numbers)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .expectNext(expect3)
                .expectNext(expect4)
                .verifyComplete()
    }
}