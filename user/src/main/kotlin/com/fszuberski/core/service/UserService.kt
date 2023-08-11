package com.fszuberski.core.service

import com.fszuberski.core.domain.User
import com.fszuberski.port.`in`.RegisterUserUseCase
import com.fszuberski.port.out.ProduceUserCreatedPort
import com.fszuberski.port.out.SaveUserPort

class UserService(private val saveUserPort: SaveUserPort, private val produceUserCreatedPort: ProduceUserCreatedPort) :
    RegisterUserUseCase {
    override fun registerUser(name: String, surname: String): User {
        val user = User(name, surname)

        // TODO: outbox pattern
        saveUserPort.save(user)
        produceUserCreatedPort.produceUserCreatedEvent(user)

        return user
    }
}
