package com.kokoo.webflux.practice

import com.kokoo.webflux.config.EmbeddedRedisConfig
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(*[EmbeddedRedisConfig::class])
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class RedisPracticeTests(
        private val factory: ReactiveRedisConnectionFactory,
        private val redisOperations: ReactiveRedisOperations<String, String>,
        private val redisPractice: RedisPractice
) {

    @BeforeAll
    fun beforeAll() {
        factory.reactiveConnection
                .serverCommands()
                .flushAll()
                .thenMany(Flux.range(1, 100).flatMap<Any> {
                    redisOperations.opsForValue().set(it.toString(), UUID.randomUUID().toString())
                })
                .subscribe()
    }

    @Test
    fun getAll_CountIsOneHundred_FindAll() {

        val actual = redisPractice.getAll()

        StepVerifier.create(actual)
                .expectNextCount(100)
                .verifyComplete()

    }
}