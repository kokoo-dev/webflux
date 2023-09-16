import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.14"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "com.kokoo"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")

	// webflux
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	// log
	implementation("org.slf4j:slf4j-api")
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

	// redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
	testImplementation("it.ozimov:embedded-redis:0.7.3") {
		exclude("org.slf4j", "slf4j-simple")
	}

	// mongodb
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
	testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
