package com.fszuberski.adapter.kafka

import com.fszuberski.cryptoexchange.user.UserCreatedEvent
import com.fszuberski.port.CreateWalletUseCase
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

private const val TOPIC_NAME = "user-topic"

class UserEventsConsumer(private val createWalletUseCase: CreateWalletUseCase) {

    fun consume() {
        val uceConsumer = KafkaConsumer<String, UserCreatedEvent>(props())
        uceConsumer.subscribe(listOf(TOPIC_NAME))

        uceConsumer.use { consumer ->
            while (true) {
                consumer.poll(Duration.ofMillis(100))
                    .map { println(it); it.value() }
                    .forEach { createWalletUseCase.createWalletForUser(UUID.fromString(it.id)) }

                consumer.commitAsync()
                Thread.sleep(2000)
            }
        }
    }

    private fun props(): Properties {
        val props = Properties()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        props[AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG] = "http://localhost:9081"
        props[ConsumerConfig.GROUP_ID_CONFIG] = "wallet1"
        props[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = "false"
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = KafkaAvroDeserializer::class.java
        props[KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG] = true
        return props
    }


}