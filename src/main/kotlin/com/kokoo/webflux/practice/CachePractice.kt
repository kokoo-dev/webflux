package com.kokoo.webflux.practice

import com.kokoo.webflux.constant.CacheNames
import mu.KotlinLogging
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class CachePractice {

    private val log = KotlinLogging.logger {}

    @CacheEvict(cacheNames = [CacheNames.MONO, CacheNames.FLUX])
    fun evictCaches() {
        log.info { "evict caches" }
    }

    @Cacheable(cacheNames = [CacheNames.MONO])
    fun cacheMono(): Mono<Boolean> {
        log.info { "no cache" }

        return Mono.just(true)
    }

    @Cacheable(cacheNames = [CacheNames.FLUX])
    fun cacheFlux(): Flux<Boolean> {
        log.info { "no cache" }

        return Flux.just(true)
    }
}