package com.fszuberski.common

class ValidationException(val errors: List<ValidationError>) : Exception() {
    constructor(error: ValidationError) : this(listOf(error))
}

data class ValidationError(val error: String)