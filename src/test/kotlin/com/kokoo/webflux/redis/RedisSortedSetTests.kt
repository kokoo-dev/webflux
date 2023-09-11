package com.kokoo.webflux.redis

import com.kokoo.webflux.config.EmbeddedRedisConfig
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Range
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ZSetOperations.TypedTuple
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
class RedisSortedSetTests(
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
                    redisOperations.opsForZSet().add(INITIAL_KEY.toString(), createValue(it), createScore(it))
                })
                .subscribe()
    }

    @Test
    fun add() {
        val key = INITIAL_KEY.toString()
        val value = createValue(INITIAL_COUNT + 1)
        val score = createScore(INITIAL_COUNT + 1)
        val expect = true

        val actual = redisOperations
                .opsForZSet()
                .add(key, value, score)

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
                .opsForZSet()
                .remove(key, vararg)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun size() {
        val key = INITIAL_KEY.toString()
        val expect = INITIAL_COUNT.toLong()

        val actual = redisOperations
                .opsForZSet()
                .size(key)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun popMax() {
        val key = INITIAL_KEY.toString()
        val score = createScore(INITIAL_KEY)
        val expect = TypedTuple.of(createValue(INITIAL_KEY), score)

        val actual = redisOperations
                .opsForZSet()
                .popMax(key)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun popMin() {
        val key = INITIAL_KEY.toString()
        val score = createScore(INITIAL_COUNT)
        val expect = TypedTuple.of(createValue(INITIAL_COUNT), score)

        val actual = redisOperations
                .opsForZSet()
                .popMin(key)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun rank() {
        val key = INITIAL_KEY.toString()
        val value = createValue(INITIAL_KEY)
        val expect = (INITIAL_COUNT - INITIAL_KEY).toLong()

        val actual = redisOperations
                .opsForZSet()
                .rank(key, value)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun range() {
        val key = INITIAL_KEY.toString()
        val range = Range.open(0L, 1L)
        val expect1 = createValue(INITIAL_COUNT)
        val expect2 = createValue(INITIAL_COUNT - 1)

        val actual = redisOperations
                .opsForZSet()
                .range(key, range)

        StepVerifier.create(actual)
                .expectNext(expect1)
                .expectNext(expect2)
                .verifyComplete()
    }

    private fun createValue(key: Any): String {
        return StringBuilder().append(key).append("-added").toString()
    }

    private fun createScore(value: Int): Double {
        return (INITIAL_COUNT - value).toDouble()
    }
}