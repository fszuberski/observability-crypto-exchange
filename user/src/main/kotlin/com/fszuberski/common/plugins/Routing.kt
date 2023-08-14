package com.fszuberski.common.plugins

import com.fszuberski.adapter.rest.userRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Application.configureRouting() {
    routing {
        userRouting(registerUserUseCase = get())
    }
}

