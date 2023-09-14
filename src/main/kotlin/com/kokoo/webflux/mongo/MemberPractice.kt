package com.kokoo.webflux.mongo

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class MemberPractice(
        private val memberRepository: MemberRepository
) {

    fun save(): Mono<MemberDto> {
        return memberRepository.save(Member(name = "kjh", age = 28))
                .map {
                    MemberDto(id = it.id)
                }
    }

    fun getAll(): Flux<MemberDto> {
        return memberRepository.findAll()
                .map {
                    MemberDto(id = it.id, name = it.name, age = it.age)
                }
    }

    fun get(id: String): Mono<MemberDto> {
        return memberRepository.findById(id)
                .map {
                    MemberDto(id = it.id, name = it.name, age = it.age)
                }
    }

    fun get(age: Int): Flux<MemberDto> {
        return memberRepository.findByAge(age)
                .map {
                    MemberDto(id = it.id, name = it.name, age = it.age)
                }
    }
}