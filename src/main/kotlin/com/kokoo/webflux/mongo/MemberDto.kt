package com.kokoo.webflux.mongo

import java.util.*

data class MemberDto(
        val id: String? = null,
        val name: String? = null,
        val age: Int? = null,
        val createdAt: Date? = null,
        val modifiedAt: Date? = null
)
