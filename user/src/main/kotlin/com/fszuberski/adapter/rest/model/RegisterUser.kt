package com.fszuberski.adapter.rest.model

import com.fszuberski.core.domain.User
import kotlinx.serialization.Serializable

class RegisterUser {

    @Serializable
    data class Request(val name: String, val surname: String)

    @Serializable
    data class Response(val id: String) {
        companion object {
            fun fromUser(user: User) = Response(user.id.toString())
        }
    }
}