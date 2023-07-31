package com.kokoo.webflux.component

class Example(
        var stringField: String = "default",
        var intField: Int = 0,
        var booleanField: Boolean = false
) {
    override fun toString(): String {
        return "{\"stringField\":\"$stringField\",\"intField\":$intField,\"booleanField\":$booleanField}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Example

        if (stringField != other.stringField) return false
        if (intField != other.intField) return false
        if (booleanField != other.booleanField) return false

        return true
    }

    override fun hashCode(): Int {
        var result = stringField.hashCode()
        result = 31 * result + intField
        result = 31 * result + booleanField.hashCode()
        return result
    }

}