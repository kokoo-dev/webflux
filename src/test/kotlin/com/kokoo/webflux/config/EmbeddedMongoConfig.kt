package com.kokoo.webflux.config

import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfig
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import java.io.IOException


@TestConfiguration
@EnableReactiveMongoAuditing
class EmbeddedMongoConfig {
    @Bean
    fun connectEmbeddedMongodb() {
        val mongodConfig: MongodConfig = MongodConfig
                .builder()
                .version(Version.Main.V6_0)
                .net(Net("127.0.0.1", 0, Network.localhostIsIPv6()))
                .build()

        val starter = MongodStarter.getDefaultInstance()
        val mongodExecutable: MongodExecutable = starter.prepare(mongodConfig)
        mongodExecutable.start()
    }
}