package com.wengelef.functionalandroid.di

import analyticslibrary.analyticsTracker
import analyticslibrary.loginEventMapper
import com.wengelef.functionalandroid.viewmodel.LoginViewModel
import com.wengelef.getUsers
import com.wengelef.saveUser
import data.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import service.loginService
import tracking.getTracker

object ViewModelModule {
    operator fun invoke(): Module = module {
        viewModel {
            LoginViewModel(
                getTracker(
                    ::analyticsTracker,
                    ::loginEventMapper
                ),
                getLoginUseCase(
                    ::validInputToUserName,
                    loginRepository(loginService(), saveUser(get()))
                ),
                getUsersUseCase(usersFromRepository(getUsers(get())))
            )
        }
    }
}