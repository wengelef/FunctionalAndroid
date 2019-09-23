package com.wengelef.functionalandroid.di

import com.wengelef.functionalandroid.di.RepositoryModule.DELETE_USERS
import com.wengelef.functionalandroid.di.RepositoryModule.GET_USERS
import com.wengelef.functionalandroid.di.RepositoryModule.LOGIN_USER
import domain.deleteusers.deleteUsersUseCase
import domain.getusers.getUsersUseCase
import domain.login.loginUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module
import util.partially

object DomainModule {
    operator fun invoke() = module {
        factory(named(LOGIN_USECASE)) {
            ::loginUseCase.partially(get(named(LOGIN_USER)))
        }

        factory(named(GET_USERS_USECASE)) {
            ::getUsersUseCase
                .partially(get(named(GET_USERS)))
        }

        factory(named(DELETE_USERS_USECASE)) {
            ::deleteUsersUseCase
                .partially(get(named(DELETE_USERS)))
        }
    }

    const val LOGIN_USECASE = "LoginUseCase"
    const val GET_USERS_USECASE = "GetUsersUseCase"
    const val DELETE_USERS_USECASE = "DeleteUsersUseCase"
}