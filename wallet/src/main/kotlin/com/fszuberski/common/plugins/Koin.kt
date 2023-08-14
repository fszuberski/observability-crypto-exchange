package com.fszuberski.common.plugins

import com.fszuberski.appModule
import io.ktor.server.application.*
import org.koin.core.module.Module
import org.koin.ktor.plugin.Koin

fun Application.configureKoin(modules: List<Module> = listOf(appModule)) {
   install(Koin) {
       modules(modules)
   }
}