package com.kokoo.webflux.mongo

import java.time.LocalDateTime

data class MemberDto(
        val id: String? = null,
        val name: String? = null,
        val age: Int? = null,
        val lastLoginAt: LocalDateTime? = null,
        val createdAt: LocalDateTime? = null,
        val modifiedAt: LocalDateTime? = null
)
