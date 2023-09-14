package com.kokoo.webflux.mongo

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface MemberRepository: ReactiveMongoRepository<Member, String> {

    fun findByAge(age: Int): Flux<Member>
}