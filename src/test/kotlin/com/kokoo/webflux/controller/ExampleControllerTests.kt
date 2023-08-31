package com.kokoo.webflux.controller

import com.kokoo.webflux.component.Example
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(controllers = [ExampleController::class])
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ExampleControllerTests(
        private val webTestClient: WebTestClient
) {

    @Test
    fun isOk_getMonoExample() {
        val uri = "/example/mono"

        val responseSpec = webTestClient.get().uri(uri).accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus()

        responseSpec.isOk
    }

    @Test
    fun equalsBody_getMonoExample() {
        val uri = "/example/mono"
        val expected = Example().toString()

        val responseSpec = webTestClient.get().uri(uri).accept(MediaType.APPLICATION_JSON)
                .exchange().expectBody()

        responseSpec.json(expected)
    }

    @Test
    fun isOk_getFluxExample() {
        val uri = "/example/flux"

        val responseSpec = webTestClient.get().uri(uri).accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus()

        responseSpec.isOk
    }

    @Test
    fun equalsBody_getFluxExample() {
        val uri = "/example/flux"
        val expected = listOf(Example(), Example(stringField = "not Default", intField = 1, booleanField = true)).toString()

        val responseSpec = webTestClient.get().uri(uri).accept(MediaType.APPLICATION_JSON)
                .exchange().expectBody()

        responseSpec.json(expected)
    }
}