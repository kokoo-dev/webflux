package com.kokoo.webflux.component

import java.time.LocalDateTime

class Example(
        var stringField: String = "default",
        var intField: Int = 0,
        var booleanField: Boolean = false
) {
    override fun toString(): String {
        return "{\"stringField\":\"$stringField\",\"intField\":$intField,\"booleanField\":$booleanField}"
    }
}