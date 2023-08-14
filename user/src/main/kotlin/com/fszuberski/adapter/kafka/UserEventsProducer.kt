package com.fszuberski.adapter.kafka

import com.fszuberski.core.domain.User
import com.fszuberski.cryptoexchange.user.UserCreatedEvent
import com.fszuberski.port.out.ProduceUserCreatedPort
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata

private const val TOPIC_NAME = "user-topic"

class UserEventsProducer : ProduceUserCreatedPort {
    override fun produceUserCreatedEvent(user: User) {
        // TODO: immediately closes the producer once a message is produced.
        // Could be cached so we don't reestablish connections.
        Configuration.producer().use { producer ->
            try {
                val userCreatedEvent = UserCreatedEvent(user.id.toString(), user.name, user.surname)
                producer.send(
                    ProducerRecord(TOPIC_NAME, user.id.toString(), userCreatedEvent)
                ) { meta: RecordMetadata, e: Exception? ->
                    when (e) {
                        null -> println("Produced record to topic ${meta.topic()} partition [${meta.partition()}] @ offset ${meta.offset()}")
                        else -> e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                // TODO: think about proper logging instead of rethrowing
                e.printStackTrace()
                throw e
            }
        }
    }
}
