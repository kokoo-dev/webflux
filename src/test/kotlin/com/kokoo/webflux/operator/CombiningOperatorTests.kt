package com.kokoo.webflux.operator

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.test.StepVerifier

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CombiningOperatorTests {

    private lateinit var combiningOperator: CombiningOperator

    @BeforeAll
    fun beforeAll() {
        combiningOperator = CombiningOperator()
    }

    @Test
    fun withLatestFrom() {
        val numbers = listOf(1, 2, 3, 4)
        val strings = listOf("A", "B")
        val expect1 = "2A"
        val expect2 = "3B"
        val expect3 = "4B"

        val actual = combiningOperator.withLatestFrom(numbers, strings)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .expectNext(expect3)
                .verifyComplete()
    }

    @Test
    fun startWith() {
        val numbers = listOf(1, 2)
        val startWithNumber = 0
        val expect1 = 0
        val expect2 = 1
        val expect3 = 2

        val actual = combiningOperator.startWith(numbers, startWithNumber)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .expectNext(expect3)
                .verifyComplete()
    }

    @Test
    fun mergeWith() {
        val firstNumbers = listOf(1, 2)
        val secondNumbers = listOf(3, 4)
        val expect1 = 1
        val expect2 = 2
        val expect3 = 3
        val expect4 = 4

        val actual = combiningOperator.mergeWith(firstNumbers, secondNumbers)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .expectNext(expect3)
                .expectNext(expect4)
                .verifyComplete()
    }

    @Test
    fun zip() {
        val firstStrings = listOf("f1", "f2", "f3")
        val secondStrings = listOf("s1", "s2", "s3")
        val thirdStrings = listOf("t1", "t2", "t3")
        val expect1 = "f1s1t1"
        val expect2 = "f2s2t2"
        val expect3 = "f3s3t3"

        val actual = combiningOperator.zip(firstStrings, secondStrings, thirdStrings)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .expectNext(expect3)
                .verifyComplete()
    }
}