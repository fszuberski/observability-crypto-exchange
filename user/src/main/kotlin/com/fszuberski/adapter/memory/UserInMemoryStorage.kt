package com.fszuberski.adapter.memory

import com.fszuberski.core.domain.User
import com.fszuberski.port.out.SaveUserPort
import com.fszuberski.port.out.UserAlreadyExists
import java.util.*

// Naive in-memory storage implementation, not thread safe.
class UserInMemoryStorage : SaveUserPort {
    private val storage = mutableMapOf<UUID, User>()

    override fun save(user: User) {
        if (storage[user.id] == null && !containsWithSurname(user.surname)) {
            storage[user.id] = user
        } else {
            throw UserAlreadyExists("user already exists")
        }
    }

    private fun containsWithSurname(surname: String) =
        storage.values.any { it.surname == surname }
}