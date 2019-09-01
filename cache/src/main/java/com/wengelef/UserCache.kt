package com.wengelef

import com.wengelef.cache.Database
import data.GetUsersFromDB
import data.SaveUserToDB
import data.UserDto

fun saveUser(database: Database): SaveUserToDB = { userDto ->
    database.userQueries.insert(userDto.username)
}

fun getUsers(database: Database): GetUsersFromDB = {
    database.userQueries
        .selectAll()
        .executeAsList()
        .map { username -> UserDto(username) }
}