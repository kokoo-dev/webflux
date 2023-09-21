package com.kokoo.webflux.config

import com.kokoo.webflux.properties.WebClientProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(value = [WebClientProperties::class])
class PropertiesConfig {
}