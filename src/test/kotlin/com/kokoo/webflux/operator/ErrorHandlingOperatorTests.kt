package com.kokoo.webflux.operator

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.test.StepVerifier

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ErrorHandlingOperatorTests {

    private lateinit var errorHandlingOperator: ErrorHandlingOperator

    @BeforeAll
    fun beforeAll() {
        errorHandlingOperator = ErrorHandlingOperator()
    }

    @Test
    fun onErrorReturn() {
        val numbers = listOf(1, 2, 3)
        val errorNumber = 2
        val expect1 = 1
        val expect2 = 0

        val actual = errorHandlingOperator.onErrorReturn(numbers, errorNumber)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .verifyComplete()
    }

    @Test
    fun onErrorResume() {
        val numbers = listOf(1, 2, 3)
        val errorNumber = 2
        val expect1 = 1
        val expect2 = 0

        val actual = errorHandlingOperator.onErrorResume(numbers, errorNumber)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .verifyComplete()
    }

    @Test
    fun onErrorContinue() {
        val numbers = listOf(1, 2, 3)
        val errorNumber = 2
        val expect1 = 1
        val expect2 = 3

        val actual = errorHandlingOperator.onErrorContinue(numbers, errorNumber)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .verifyComplete()
    }
}