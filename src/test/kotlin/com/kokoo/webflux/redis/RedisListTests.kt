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
class RedisListTests(
        private val factory: ReactiveRedisConnectionFactory,
        private val redisOperations: ReactiveRedisOperations<String, String>
) {

    companion object {
        private const val INITIAL_COUNT = 10L
        private const val INITIAL_KEY = 1
    }

    @BeforeEach
    fun beforeEach() {
        factory.reactiveConnection
                .serverCommands()
                .flushAll()
                .thenMany(Flux.range(INITIAL_KEY, INITIAL_COUNT.toInt()).flatMap<Any> {
                    redisOperations.opsForList().rightPush(INITIAL_KEY.toString(), createValue(it))
                })
                .subscribe()
    }

    @Test
    fun rightPush() {
        val key = INITIAL_KEY.toString()
        val value = createValue(key)
        val expect = INITIAL_COUNT + 1

        val actual = redisOperations
                .opsForList()
                .rightPush(key, value)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun leftPush() {
        val key = INITIAL_KEY.toString()
        val value = createValue(key)
        val expect = INITIAL_COUNT + 1

        val actual = redisOperations
                .opsForList()
                .leftPush(key, value)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun index() {
        val key = INITIAL_KEY.toString()
        val index = 5L
        val expect = createValue(index + 1)

        val actual = redisOperations
                .opsForList()
                .index(key, index)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun range() {
        val key = INITIAL_KEY.toString()
        val start = 0L
        val end = 1L
        val expect1 = createValue(start + 1)
        val expect2 = createValue(end + 1)

        val actual = redisOperations
                .opsForList()
                .range(key, start, end)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .verifyComplete()
    }

    @Test
    fun leftPop() {
        val key = INITIAL_KEY.toString()
        val expect = createValue(1)

        val actual = redisOperations
                .opsForList()
                .leftPop(key)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun rightPop() {
        val key = INITIAL_KEY.toString()
        val expect = createValue(INITIAL_COUNT)

        val actual = redisOperations
                .opsForList()
                .rightPop(key)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    private fun createValue(key: Any): String {
        return StringBuilder().append(key).append("-added").toString()
    }
}