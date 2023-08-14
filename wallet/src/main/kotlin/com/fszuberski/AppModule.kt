package com.fszuberski

import com.fszuberski.adapter.kafka.UserEventsConsumer
import com.fszuberski.core.WalletService
import com.fszuberski.port.CreateWalletUseCase
import org.koin.dsl.module

val appModule = module {
    single<CreateWalletUseCase> { WalletService() }
    single { UserEventsConsumer(createWalletUseCase = get()) }
}