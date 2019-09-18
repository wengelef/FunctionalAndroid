package com.wengelef

import android.util.Log
import arrow.fx.IO
import arrow.fx.IODispatchers.CommonPool
import arrow.fx.extensions.fx
import com.wengelef.cache.Database
import data.model.UserDto

fun saveUserToDB(database: Database, userDto: UserDto): IO<UserDto> = IO.fx {
    continueOn(CommonPool)
    Log.e("SaveUser", Thread.currentThread().name)
    database.userQueries.insert(userDto.username)
    userDto
}

fun getUsersFromDB(database: Database): IO<List<UserDto>> = IO.fx {
    continueOn(CommonPool)
    Log.e("GetUsers", Thread.currentThread().name)
    database.userQueries
        .selectAll()
        .executeAsList()
        .map(::toUserDto)
}

fun deleteUsersFromDB(database: Database): IO<List<UserDto>> = IO.fx {
    continueOn(CommonPool)
    Log.e("DeleteUsers", Thread.currentThread().name)
    database.userQueries
        .deleteAll()

    database.userQueries
        .selectAll()
        .executeAsList()
        .map(::toUserDto)
}

private fun toUserDto(username: String) = UserDto(username)