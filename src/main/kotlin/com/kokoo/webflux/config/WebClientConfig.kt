package com.kokoo.webflux.config

import com.kokoo.webflux.properties.WebClientProperties
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.Connection
import reactor.netty.http.client.HttpClient

@Configuration
class WebClientConfig(
        private val webClientProperties: WebClientProperties
) {

    @Bean
    fun apiSampleWebClient(): WebClient {
        val httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientProperties.connectionTimeoutMillis)
                .doOnConnected { connection: Connection ->
                    connection
                            .addHandlerLast(ReadTimeoutHandler(webClientProperties.readTimeoutSecond))
                            .addHandlerLast(WriteTimeoutHandler(webClientProperties.writeTimeoutSecond))
                }
        val connector: ClientHttpConnector = ReactorClientHttpConnector(httpClient)
        return WebClient.builder()
                .baseUrl(webClientProperties.apiSampleUrl)
                .clientConnector(connector)
                .build()
    }
}