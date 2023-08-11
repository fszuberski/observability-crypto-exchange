package com.fszuberski

import com.fszuberski.adapter.kafka.UserProducer
import com.fszuberski.adapter.memory.UserInMemoryStorage
import com.fszuberski.core.service.UserService
import com.fszuberski.port.`in`.RegisterUserUseCase
import com.fszuberski.port.out.ProduceUserCreatedPort
import com.fszuberski.port.out.SaveUserPort
import org.koin.dsl.module

val appModule = module {
    single<SaveUserPort> { UserInMemoryStorage() }
    single<ProduceUserCreatedPort> { UserProducer() }
    single<RegisterUserUseCase> { UserService(saveUserPort = get(), produceUserCreatedPort = get()) }
}