package com.kokoo.webflux.redis

import com.kokoo.webflux.config.EmbeddedRedisConfig
import org.junit.jupiter.api.BeforeEach
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
import java.lang.StringBuilder

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(*[EmbeddedRedisConfig::class])
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class RedisValueTests(
        private val factory: ReactiveRedisConnectionFactory,
        private val redisOperations: ReactiveRedisOperations<String, String>
) {

    companion object {
        const val INITIAL_COUNT = 10
    }

    @BeforeEach
    fun beforeEach() {
        factory.reactiveConnection
                .serverCommands()
                .flushAll()
                .thenMany(Flux.range(1, INITIAL_COUNT).flatMap<Any> {
                    redisOperations.opsForValue().set(it.toString(), createValue(it))
                })
                .subscribe()
    }

    @Test
    fun keys() {
        val expect = INITIAL_COUNT.toLong()

        val actual = redisOperations
                .keys("*")
                .flatMap { key ->
                    val uuid = redisOperations.opsForValue().get(key)
                    uuid
                }

        StepVerifier.create(actual)
                .expectNextCount(expect)
                .verifyComplete()
    }

    @Test
    fun get() {
        val key = 1
        val expect = createValue(key)

        val actual = redisOperations
                .opsForValue()
                .get(key.toString())

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun hasKey() {
        val key = "1"
        val expect = true

        val actual = redisOperations
                .hasKey(key)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun set() {
        val key = INITIAL_COUNT + 1
        val expect = true

        val actual = redisOperations
                .opsForValue()
                .set(key.toString(), createValue(key))

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun delete() {
        val key = 1
        val expect = true

        val actual = redisOperations
                .opsForValue()
                .delete(key.toString())

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    private fun createValue(key: Int): String {
        return StringBuilder().append(key).append("-added").toString()
    }
}