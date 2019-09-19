package service

import arrow.fx.IO
import data.model.UserDto

fun loginService(username: String): IO<UserDto> = IO { UserDto(username) }