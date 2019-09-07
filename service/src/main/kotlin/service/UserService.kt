package service

import data.LoginService
import data.model.UserDto

fun loginService(): LoginService = { username -> UserDto(username) }