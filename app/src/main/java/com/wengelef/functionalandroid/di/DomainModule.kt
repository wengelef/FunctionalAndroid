package com.wengelef.functionalandroid.di

import data.provideGetUsersUseCase
import data.provideLoginUseCase
import data.userDtoToUser
import domain.model.validInputToUserName
import org.koin.core.qualifier.named
import org.koin.dsl.module
import service.loginService
import util.partially

object DomainModule {
    operator fun invoke() = module {
        factory(named(LOGIN_USECASE)) {
            ::provideLoginUseCase
                .partially(
                    ::validInputToUserName,
                    ::loginService,
                    get()
                )
        }

        factory(named(GET_USERS_USECASE)) {
            ::provideGetUsersUseCase
                .partially(get(), ::userDtoToUser)
        }
    }

    const val LOGIN_USECASE = "LoginUseCase"
    const val GET_USERS_USECASE = "GetUsersUseCase"
}