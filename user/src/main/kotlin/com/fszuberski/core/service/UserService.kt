package com.fszuberski.core.service

import com.fszuberski.core.domain.User
import com.fszuberski.port.`in`.RegisterUserUseCase
import com.fszuberski.port.out.SaveUserPort

class UserService(val saveUserPort: SaveUserPort) : RegisterUserUseCase {
    override fun registerUser(name: String, surname: String): User {
        val user = User(name, surname)
        saveUserPort.save(user)
        return user
    }
}
