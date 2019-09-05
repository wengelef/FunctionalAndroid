package com.wengelef.functionalandroid.di

import com.wengelef.getUsers
import com.wengelef.saveUser
import data.*
import org.koin.dsl.module
import service.loginService

object DomainModule {
    operator fun invoke() = module {
        factory {
            getLoginUseCase(::validInputToUserName, loginRepository(loginService(), saveUser(get())))
        }

        factory { getUsersUseCase(usersFromRepository(getUsers(get()))) }
    }
}