package com.fszuberski

import com.fszuberski.plugins.configureKoin
import com.fszuberski.plugins.configureRouting
import com.fszuberski.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin(listOf(appModule))
    configureSerialization()
    configureRouting()
}
