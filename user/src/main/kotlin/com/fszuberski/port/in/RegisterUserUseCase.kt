package com.fszuberski.port.`in`

import com.fszuberski.core.domain.User

interface RegisterUserUseCase {
    fun registerUser(name: String, surname: String): User
}
