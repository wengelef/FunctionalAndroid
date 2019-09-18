package com.wengelef.functionalandroid.di

import com.wengelef.cache.DB
import com.wengelef.cache.Database
import com.wengelef.deleteUsersFromDB
import com.wengelef.getUsersFromDB
import com.wengelef.saveUserToDB
import org.koin.core.qualifier.named
import org.koin.dsl.module
import util.partially

object CacheModule {
    operator fun invoke() = module {
        single { Database(DB.driver(get())) }
        single(named(SAVE_USER_DB)) { ::saveUserToDB.partially(get()) }
        single(named(GET_USERS_DB)) { ::getUsersFromDB.partially(get()) }
        single(named(DELETE_USERS_DB)) { ::deleteUsersFromDB.partially(get()) }
    }

    const val SAVE_USER_DB = "SaveUserFromDB"
    const val GET_USERS_DB = "GetUsersFromDB"
    const val DELETE_USERS_DB = "DeleteUsersFromDB"
}