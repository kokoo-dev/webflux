package com.kokoo.webflux.practice

import com.kokoo.webflux.component.Example
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FluxPracticeTests {

    private lateinit var fluxPractice: FluxPractice

    @BeforeAll
    fun beforeAll() {
        fluxPractice = FluxPractice()
    }

    @Test
    fun switchIfEmpty_GetExampleDefault_FluxIsEmpty() {
        val expect = Example()

        val actual = fluxPractice.switchIfEmpty()

        StepVerifier.create(actual)
            .expectNext(expect)
            .verifyComplete()
    }

    @Test
    fun concatWith_GetConcatExampleOneToThree_ExampleOneToTwoAndExampleThree() {
        val flux1 = createFlux(1, 2)
        val flux2 = createFlux(3, 3)
        val expect1 = createExample(1)
        val expect2 = createExample(2)
        val expect3 = createExample(3)

        val actual = fluxPractice.concatWith(flux1, flux2)

        StepVerifier.create(actual)
            .expectNext(expect1)
            .expectNext(expect2)
            .expectNext(expect3)
            .verifyComplete()
    }

    private fun createFlux(startIndex: Int, endIndex: Int): Flux<Example> {
        val examples = createExamples(startIndex, endIndex)

        return toFlux(examples)
    }

    private fun createExamples(startIndex: Int, endIndex: Int): List<Example> {
        val examples: ArrayList<Example> = ArrayList()
        for (i: Int in startIndex..endIndex) {
            examples.add(
                createExample(i)
            )
        }

        return examples
    }

    private fun createExample(index: Int): Example {
        return Example (
            stringField = "example${index}",
            intField = index,
            booleanField = index % 2 == 0
        )
    }

    private fun toFlux(examples: List<Example>): Flux<Example> {
        return Mono.just(examples).flatMapIterable { examples }
    }
}