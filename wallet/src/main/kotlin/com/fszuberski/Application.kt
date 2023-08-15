package com.fszuberski

import com.fszuberski.adapter.kafka.UserEventsConsumer
import com.fszuberski.common.plugins.configureKoin
import com.fszuberski.common.plugins.configureRouting
import com.fszuberski.common.plugins.configureSerialization
import io.ktor.server.application.*
import org.koin.ktor.ext.get

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)

}

fun Application.module() {
    configureKoin(listOf(appModule))
    configureSerialization()
    configureRouting()

    startConsumers()
}

fun Application.startConsumers() {
    environment.monitor.subscribe(ApplicationStopPreparing) {
        get<UserEventsConsumer>().stop()
    }
    get<UserEventsConsumer>().start()
}