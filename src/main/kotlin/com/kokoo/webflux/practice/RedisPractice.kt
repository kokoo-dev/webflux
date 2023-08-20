package com.kokoo.webflux.practice

import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux


@Service
class RedisPractice(
        private val redisOperations: ReactiveRedisOperations<String, String>
) {

    fun getAll(): Flux<String> {
        return redisOperations
                .keys("*")
                .flatMap {
                    key -> val uuid = redisOperations.opsForValue().get(key)
                    uuid
                }
    }
}