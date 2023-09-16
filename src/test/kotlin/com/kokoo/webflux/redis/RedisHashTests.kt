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

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(*[EmbeddedRedisConfig::class])
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class RedisHashTests(
        private val factory: ReactiveRedisConnectionFactory,
        private val redisOperations: ReactiveRedisOperations<String, String>
) {

    companion object {
        private const val INITIAL_COUNT = 10
        private const val INITIAL_KEY = "1"
        private const val INITIAL_VALUE = "initial_value"
    }

    @BeforeEach
    fun beforeEach() {
        factory.reactiveConnection
                .serverCommands()
                .flushAll()
                .thenMany(Flux.range(INITIAL_KEY.toInt(), INITIAL_COUNT).flatMap<Any> {
                    redisOperations.opsForHash<String, String>().put(INITIAL_KEY, it.toString(), INITIAL_VALUE)
                })
                .subscribe()
    }

    @Test
    fun put() {
        val key = INITIAL_KEY
        val hashKey = (INITIAL_COUNT + 1).toString()
        val value = "value"
        val expect = true

        val actual = redisOperations
                .opsForHash<String, String>()
                .put(key, hashKey, value)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun remove() {
        val key = INITIAL_KEY
        val hashKey = "1"
        val expect = 1L

        val actual = redisOperations
                .opsForHash<String, String>()
                .remove(key, hashKey)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun delete() {
        val key = INITIAL_KEY
        val expect = true

        val actual = redisOperations
                .opsForHash<String, String>()
                .delete(key)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun get() {
        val key = INITIAL_KEY
        val hashKey = "2"
        val expect = INITIAL_VALUE

        val actual = redisOperations
                .opsForHash<String, String>()
                .get(key, hashKey)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun size() {
        val key = INITIAL_KEY
        val expect = INITIAL_COUNT.toLong()

        val actual = redisOperations
                .opsForHash<String, String>()
                .size(key)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun hasKey() {
        val key = INITIAL_KEY
        val hashKey = "1"
        val expect = true

        val actual = redisOperations
                .opsForHash<String, String>()
                .hasKey(key, hashKey)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }
}