package com.fszuberski.adapter.rest

import com.fszuberski.adapter.rest.model.ErrorResponse
import com.fszuberski.adapter.rest.model.RegisterUser
import com.fszuberski.common.ValidationException
import com.fszuberski.port.`in`.RegisterUserUseCase
import com.fszuberski.port.out.UserAlreadyExists
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private const val BASE_API_ROUTE = "v1"

fun Route.userRouting(registerUserUseCase: RegisterUserUseCase) {
    route(BASE_API_ROUTE) {
        route("user") {
            post {
                try {
                    val userRegistrationRequest = call.receive<RegisterUser.Request>()
                    val (name, surname) = userRegistrationRequest

                    val registeredUser = registerUserUseCase.registerUser(name, surname)
                    call.respond(HttpStatusCode.Created, RegisterUser.Response.fromUser(registeredUser))
                } catch (e: Exception) {
                    when (e) {
                        is ValidationException ->
                            call.respond(HttpStatusCode.BadRequest, ErrorResponse.fromException(e))

                        is UserAlreadyExists ->
                            call.respond(HttpStatusCode.Conflict, ErrorResponse.fromException(e))

                        else -> call.respond(HttpStatusCode.InternalServerError)
                    }
                }
            }
        }
        route("login") {
            post {

            }
        }
    }
}
