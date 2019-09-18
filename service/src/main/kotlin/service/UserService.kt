package service

import arrow.fx.IO
import arrow.fx.IODispatchers.CommonPool
import arrow.fx.extensions.fx
import data.model.UserDto

fun loginService(username: String): IO<UserDto> = IO.fx {
    continueOn(CommonPool)
    UserDto(username)
}