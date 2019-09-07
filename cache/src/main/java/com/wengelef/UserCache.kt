package com.wengelef

import com.wengelef.cache.Database
import data.model.UserDto

fun saveUser(database: Database, userDto: UserDto) =
    database.userQueries.insert(userDto.username)

fun getUsers(database: Database): List<UserDto> {
    return database.userQueries
        .selectAll()
        .executeAsList()
        .map { username -> UserDto(username) }
}