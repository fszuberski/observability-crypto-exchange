package com.fszuberski.port.out

import com.fszuberski.core.domain.User

interface SaveUserPort {
    fun save(user: User)
}

class UserAlreadyExists(message: String = "user already exists") : Exception(message)
