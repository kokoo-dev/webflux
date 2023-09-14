package com.kokoo.webflux.mongo

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document("MEMBER")
data class Member(

        @Id
        val id: String? = null,

        @Field
        val name: String,

        @Field
        val age: Int,

        @Field
        @CreatedDate
        val createdAt: LocalDateTime? = null,

        @Field
        @LastModifiedDate
        val modifiedAt: LocalDateTime? = null
)
