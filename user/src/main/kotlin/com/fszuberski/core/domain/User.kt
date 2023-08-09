package com.fszuberski.core.domain

import com.fszuberski.common.ValidationError
import com.fszuberski.common.ensure
import com.fszuberski.common.validate
import java.util.*

data class User(val id: UUID, val name: String, val surname: String) {
    constructor(name: String, surname: String) : this(UUID.randomUUID(), name, surname)

    init {
        validate(
            ensure(name.trim().isNotBlank()) { ValidationError("name cannot be blank") },
            ensure(surname.trim().isNotBlank()) { ValidationError("surname cannot be blank") }
        )
    }
}

