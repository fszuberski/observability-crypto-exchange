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
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread

private const val TOPIC_NAME = "user-events"

class UserEventsConsumer(private val createWalletUseCase: CreateWalletUseCase, private val throttle: Boolean = true) {

    fun start() {
        thread(name = "consumer-$TOPIC_NAME", isDaemon = true) {
            consumerState.getAndUpdate { ConsumerState.RUNNING }
            val uceConsumer = KafkaConsumer<String, UserCreatedEvent>(consumerProperties)
            uceConsumer.subscribe(listOf(TOPIC_NAME))

            uceConsumer.use { consumer ->
                while (consumerState.get() == ConsumerState.RUNNING) {
                    consumer.poll(Duration.ofMillis(100))
                        .map { println(it); it.value() }
                        .forEach { createWalletUseCase.createWalletForUser(UUID.fromString(it.id)) }

                    consumer.commitAsync()
                    if (throttle) {
                        Thread.sleep(2000)
                    }
                }
            }
        }
    }

    fun stop() {
        consumerState.getAndUpdate { ConsumerState.STOPPED }
    }

    private enum class ConsumerState {
        INITIAL,
        RUNNING,
        STOPPED
    }

    companion object {
        private var consumerState = AtomicReference(ConsumerState.INITIAL)
        private val consumerProperties: Properties by lazy {
            val props = Properties()
            props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
            props[AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG] = "http://localhost:9081"
            props[ConsumerConfig.GROUP_ID_CONFIG] = "wallet1"
            props[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = "false"
            props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
            props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
            props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = KafkaAvroDeserializer::class.java
            props[KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG] = true
            props
        }
    }
}