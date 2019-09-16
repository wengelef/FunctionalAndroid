package service

import arrow.core.Either
import arrow.core.Right
import data.LoginServiceError
import data.model.UserDto

fun loginService(username: String): Either<LoginServiceError, UserDto> = Right(UserDto(username))