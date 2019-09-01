package service

import data.LoginService
import data.UserDto

fun loginService(): LoginService = { username -> UserDto(username) }