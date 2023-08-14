package com.fszuberski.core

import com.fszuberski.port.CreateWalletUseCase
import java.util.*

class WalletService : CreateWalletUseCase {
    override fun createWalletForUser(userId: UUID) {
        // TODO
        println("Wallet created for user: $userId")
    }
}