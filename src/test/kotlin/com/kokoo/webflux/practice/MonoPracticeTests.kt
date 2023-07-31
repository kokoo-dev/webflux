package com.kokoo.webflux.practice

import com.kokoo.webflux.component.Example
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MonoPracticeTests {

    private lateinit var monoPractice: MonoPractice

    @BeforeAll
    fun beforeAll() {
        monoPractice = MonoPractice()
    }

    @Test
    fun getExampleDefault_ExampleIsNull() {
        val expect = Example()
        val example: Example? = null

        val actual: Mono<Example> = monoPractice.switchIfEmpty(example)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }
}