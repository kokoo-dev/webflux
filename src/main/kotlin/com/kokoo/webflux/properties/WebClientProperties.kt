package com.kokoo.webflux.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("spring.webclient")
data class WebClientProperties(
        val connectionTimeoutMillis: Int,
        val readTimeoutSecond: Int,
        val writeTimeoutSecond: Int,
        val apiSampleUrl: String
)