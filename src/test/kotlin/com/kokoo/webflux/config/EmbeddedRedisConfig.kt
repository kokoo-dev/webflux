package com.kokoo.webflux.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import redis.embedded.RedisServer
import java.io.IOException
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy


@TestConfiguration
class EmbeddedRedisConfig(
        @Value("\${spring.redis.port}")
        private val redisPort: Int
) {

    private val redisServer: RedisServer = RedisServer(redisPort)

    @PostConstruct
    @Throws(IOException::class)
    fun redisServer() {
        redisServer.start()
    }

    @PreDestroy
    fun stopRedis() {
        redisServer.stop()
    }
}