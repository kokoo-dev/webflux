package com.kokoo.webflux.practice

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.kokoo.webflux.component.WebClientSample
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Component
class WebClientPractice(
        private val apiSampleWebClient: WebClient
) {

    fun post(webClientSample: WebClientSample): Mono<WebClientSample> {
        return apiSampleWebClient.post()
                .uri("/api-sample")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(webClientSample), WebClientSample::class.java)
                .retrieve()
                .bodyToMono(WebClientSample::class.java)
    }

    fun put(webClientSample: WebClientSample): Mono<WebClientSample> {
        return apiSampleWebClient.put()
                .uri("/api-sample")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(webClientSample), WebClientSample::class.java)
                .retrieve()
                .bodyToMono(WebClientSample::class.java)
    }

    fun get(webClientSample: WebClientSample): Mono<WebClientSample> {
        return apiSampleWebClient.get()
                .uri(getUriString("/api-sample", webClientSample))
                .retrieve()
                .bodyToMono(WebClientSample::class.java)
    }

    fun delete(webClientSample: WebClientSample): Mono<WebClientSample> {
        return apiSampleWebClient.delete()
                .uri(getUriString("/api-sample", webClientSample))
                .retrieve()
                .bodyToMono(WebClientSample::class.java)
    }

    private fun getUriString(uri: String, parameters: Any): String {
        return UriComponentsBuilder
                .fromUriString(uri)
                .queryParams(createMultiValueMap(parameters))
                .toUriString()
    }

    private fun createMultiValueMap(parameters: Any): MultiValueMap<String, String> {
        val objectMapper = ObjectMapper()
        val typeReference = object : TypeReference<Map<String, String>>() {}
        val parameterMap = objectMapper.convertValue(parameters, typeReference)

        val multiValueMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        multiValueMap.setAll(parameterMap)

        return multiValueMap
    }
}