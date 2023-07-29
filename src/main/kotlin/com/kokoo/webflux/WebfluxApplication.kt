package com.kokoo.webflux

import org.springframework.boot.SpringBootVersion
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.system.JavaVersion
import org.springframework.core.SpringVersion

@SpringBootApplication
class WebfluxApplication

fun main(args: Array<String>) {
	runApplication<WebfluxApplication>(*args)
}
