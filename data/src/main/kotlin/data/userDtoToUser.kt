package data

import data.model.UserDto
import domain.model.User
import domain.model.Username

fun userDtoToUser(userDto: UserDto) = User(Username(userDto.username))