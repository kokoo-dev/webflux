package com.kokoo.webflux.config

import com.github.benmanes.caffeine.cache.Caffeine
import com.kokoo.webflux.constant.CacheNames
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
class CacheConfig {
    @Bean
    fun cacheManager(): CacheManager {
        val cacheManager = CaffeineCacheManager()

        // default
        cacheManager.setCaffeine(createCaffeine(1, TimeUnit.DAYS, 1000))

        registerCaffeineCache(cacheManager, CacheNames.MONO)
        registerCaffeineCache(cacheManager, CacheNames.FLUX)

        return cacheManager
    }

    private fun registerCaffeineCache(
        cacheManager: CaffeineCacheManager,
        cacheName: String,
        expireDuration: Long = 1,
        expireTimeUnit: TimeUnit = TimeUnit.DAYS,
        maximumSize: Long = 1000
    ) {
        cacheManager.registerCustomCache(
            cacheName,
            createCaffeine(expireDuration, expireTimeUnit, maximumSize).build()
        )
    }

    private fun createCaffeine(
        expireDuration: Long,
        expireTimeUnit: TimeUnit,
        maximumSize: Long
    ): Caffeine<Any, Any> {
        return Caffeine.newBuilder()
            .expireAfterWrite(expireDuration, expireTimeUnit)
            .maximumSize(maximumSize)
    }
}