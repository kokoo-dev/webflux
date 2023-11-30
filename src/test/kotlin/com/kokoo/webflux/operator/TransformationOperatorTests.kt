package com.kokoo.webflux.operator

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.test.StepVerifier

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransformationOperatorTests {

    private lateinit var transformationOperator: TransformationOperator

    @BeforeAll
    fun beforeAll() {
        transformationOperator = TransformationOperator()
    }

    @Test
    fun buffer() {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val bufferSize = 3
        val expect1 = listOf(1, 2, 3)
        val expect2 = listOf(4, 5, 6)
        val expect3 = listOf(7, 8, 9)

        val actual = transformationOperator.buffer(list, bufferSize)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .expectNext(expect3)
                .verifyComplete()
    }

    @Test
    fun flatMap() {
        val list = listOf(1, 2, 3)
        val expect1 = 1
        val expect2 = 2
        val expect3 = 3

        val actual = transformationOperator.flatMap(list)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .expectNext(expect3)
                .verifyComplete()
    }

    @Test
    fun groupBy() {
        val list = listOf("group1", "group1", "group2")
        val expect1 = "group1"
        val expect2 = "group2"

        val actual = transformationOperator.groupBy(list)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .verifyComplete()
    }

    @Test
    fun map() {
        val list = listOf(1, 2, 3)
        val expect1 = 11
        val expect2 = 12
        val expect3 = 13

        val actual = transformationOperator.map(list)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .expectNext(expect3)
                .verifyComplete()
    }

    @Test
    fun scan() {
        val list = listOf(1, 2, 3, 4)
        val expect1 = 1
        val expect2 = 2
        val expect3 = 6
        val expect4 = 24

        val actual = transformationOperator.scan(list)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .expectNext(expect3)
                .expectNext(expect4)
                .verifyComplete()
    }
}