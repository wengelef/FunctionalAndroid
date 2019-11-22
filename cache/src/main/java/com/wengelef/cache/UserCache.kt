package com.wengelef.cache

import arrow.fx.IO
import arrow.fx.extensions.fx
import data.model.UserDto

fun saveUserToDB(database: Database, userDto: UserDto): IO<UserDto> = IO.fx {
    database.userQueries.insert(userDto.username)
    userDto
}

fun getUsersFromDB(database: Database): IO<List<UserDto>> = IO.fx {
    database.userQueries
        .selectAll()
        .executeAsList()
        .map(::toUserDto)
}

fun deleteUsersFromDB(database: Database): IO<List<UserDto>> = IO.fx {
    database.userQueries
        .deleteAll()

    database.userQueries
        .selectAll()
        .executeAsList()
        .map(::toUserDto)
}

private fun toUserDto(username: String) = UserDto(username)