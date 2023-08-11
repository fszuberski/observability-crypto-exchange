package com.fszuberski.port.out

import com.fszuberski.core.domain.User

interface ProduceUserCreatedPort {
    fun produceUserCreatedEvent(user: User)
}