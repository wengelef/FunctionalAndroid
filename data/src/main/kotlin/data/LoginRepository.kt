package data

import data.model.UserDto
import domain.model.LoginInput
import domain.model.User
import domain.model.Username

fun validInputToUserName(input: LoginInput.Valid): Username =
    Username(input.value)

fun userDtoToUser(userDto: UserDto) = User(Username(userDto.username))