package com.fszuberski

import com.fszuberski.adapter.memory.UserInMemoryStorage
import com.fszuberski.core.service.UserService
import com.fszuberski.port.`in`.RegisterUserUseCase
import com.fszuberski.port.out.SaveUserPort
import org.koin.dsl.module

val appModule = module {
    single<SaveUserPort> { UserInMemoryStorage() }
    single<RegisterUserUseCase> { UserService(saveUserPort = get()) }
}