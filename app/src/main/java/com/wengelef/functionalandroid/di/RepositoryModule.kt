package com.wengelef.functionalandroid.di

import com.wengelef.functionalandroid.di.CacheModule.DELETE_USERS_DB
import com.wengelef.functionalandroid.di.CacheModule.GET_USERS_DB
import com.wengelef.functionalandroid.di.CacheModule.SAVE_USER_DB
import data.deleteUsers
import data.getUsers
import data.loginUser
import data.userDtoToUser
import domain.model.validInputToUserName
import org.koin.core.qualifier.named
import org.koin.dsl.module
import service.loginService
import util.partially

object RepositoryModule {
    operator fun invoke() = module {
        single(named(GET_USERS)) {
            ::getUsers.partially(get(named(GET_USERS_DB)), ::userDtoToUser)
        }

        single(named(LOGIN_USER)) {
            ::loginUser.partially(
                ::loginService,
                get(named(SAVE_USER_DB)),
                ::userDtoToUser)
        }

        single(named(DELETE_USERS)) {
            ::deleteUsers.partially(get(named(DELETE_USERS_DB)), ::userDtoToUser)
        }
    }

    const val GET_USERS = "getUsers"
    const val LOGIN_USER = "loginUser"
    const val DELETE_USERS = "deleteUsers"
}