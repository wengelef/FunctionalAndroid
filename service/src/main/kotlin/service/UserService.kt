package service

import data.UserDto

fun loginService(username: String): UserDto = UserDto(username)