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
class RedisSetTests(
        private val factory: ReactiveRedisConnectionFactory,
        private val redisOperations: ReactiveRedisOperations<String, String>
) {

    companion object {
        private const val INITIAL_COUNT = 10
        private const val INITIAL_KEY = 1
    }

    @BeforeEach
    fun beforeEach() {
        factory.reactiveConnection
                .serverCommands()
                .flushAll()
                .thenMany(Flux.range(INITIAL_KEY, INITIAL_COUNT).flatMap<Any> {
                    redisOperations.opsForSet().add("1", createValue(it))
                })
                .subscribe()
    }

    @Test
    fun add() {
        val key = INITIAL_KEY.toString()
        val newMember = createValue(INITIAL_COUNT + 1)
        val expect = 1L

        val actual = redisOperations
                .opsForSet()
                .add(key, newMember)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun remove() {
        val key = INITIAL_KEY.toString()
        val vararg = createValue(key)
        val expect = 1L

        val actual = redisOperations
                .opsForSet()
                .remove(key, vararg)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun pop() {
        val key = INITIAL_KEY.toString()
        val expect = "-added"

        val actual = redisOperations
                .opsForSet()
                .pop(key)

        StepVerifier.create(actual)
                .expectNextMatches {
                    it.endsWith(expect)
                }
                .verifyComplete()
    }

    @Test
    fun size() {
        val key = INITIAL_KEY.toString()
        val expect = INITIAL_COUNT.toLong()

        val actual = redisOperations
                .opsForSet()
                .size(key)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun members() {
        val key = INITIAL_KEY.toString()
        val expect = INITIAL_COUNT.toLong()

        val actual = redisOperations
                .opsForSet()
                .members(key)

        StepVerifier.create(actual)
                .expectNextCount(expect)
                .verifyComplete()
    }

    @Test
    fun isMembers() {
        val key = INITIAL_KEY.toString()
        val vararg = createValue(key)
        val expect = true

        val actual = redisOperations
                .opsForSet()
                .isMember(key, vararg)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    private fun createValue(key: Any): String {
        return StringBuilder().append(key).append("-added").toString()
    }
}