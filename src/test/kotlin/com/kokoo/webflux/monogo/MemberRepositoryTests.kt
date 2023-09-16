package com.kokoo.webflux.monogo

import com.kokoo.webflux.config.EmbeddedMongoConfig
import com.kokoo.webflux.mongo.Member
import com.kokoo.webflux.mongo.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.dropCollection
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.test.StepVerifier
import java.util.*
import kotlin.random.Random

@ExtendWith(SpringExtension::class)
@DataMongoTest
@ActiveProfiles("test")
@Import(*[EmbeddedMongoConfig::class])
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberRepositoryTests(
        private val memberRepository: MemberRepository,
        private val reactiveMongoTemplate: ReactiveMongoTemplate
) {

    companion object {
        private const val INITIAL_NAME = "kokoo"
        private const val INITIAL_AGE = 28
    }

    private lateinit var savedMember: Member

    @BeforeEach
    fun beforeEach() {
        reactiveMongoTemplate.dropCollection<Member>().subscribe()
        savedMember = memberRepository.save(Member(name = INITIAL_NAME, age = INITIAL_AGE)).block()!!
//        memberRepository.save(Member(name = INITIAL_NAME, age = INITIAL_AGE)).subscribe()
    }

    @Test
    fun save() {
        val name = UUID.randomUUID().toString()
        val age = Random.nextInt()

        val actual = memberRepository.save(Member(name = name, age = age))

        StepVerifier.create(actual)
                .assertNext {
                    assertThat(it.id).isNotNull()
                    assertThat(it.name).isEqualTo(name)
                    assertThat(it.age).isEqualTo(age)
                }
                .verifyComplete()
    }

    @Test
    fun deleteById() {
        val id = savedMember.id!!

        val actual = memberRepository.deleteById(id)

        StepVerifier.create(actual)
                .verifyComplete()
    }

    @Test
    fun findAll() {
        val expect = savedMember

        val actual = memberRepository.findAll()

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun findById() {
        val id = savedMember.id!!
        val expect = savedMember

        val actual = memberRepository.findById(id)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun findByAge() {
        val age = savedMember.age
        val expect = savedMember

        val actual = memberRepository.findByAge(age)

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }

    @Test
    fun count() {
        val expect = 1L

        val actual = memberRepository.count()

        StepVerifier.create(actual)
                .expectNext(expect)
                .verifyComplete()
    }
}