package com.fszuberski.port

import java.util.*

interface CreateWalletUseCase {
    fun createWalletForUser(userId: UUID)
}