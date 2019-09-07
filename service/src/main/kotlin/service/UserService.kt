package service

import data.model.UserDto

fun loginService(username: String): UserDto = UserDto(username)