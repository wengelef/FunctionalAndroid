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
        factory(named("LoginUseCase")) {
            ::provideLoginUseCase
                .partially(
                    ::validInputToUserName,
                    ::loginService,
                    get()
                )
        }

        factory(named("GetUsersUseCase")) {
            ::provideGetUsersUseCase
                .partially(get(), ::userDtoToUser)
        }
    }
}