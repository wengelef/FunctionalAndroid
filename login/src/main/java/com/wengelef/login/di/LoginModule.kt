package com.wengelef.login.di

import com.wengelef.functionalandroid.di.ConcurrencyModule
import com.wengelef.functionalandroid.di.DomainModule.GET_USERS_USECASE
import com.wengelef.functionalandroid.di.DomainModule.LOGIN_USECASE
import com.wengelef.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

object LoginModule {
    operator fun invoke(): Module = module {
        viewModel {
            LoginViewModel(
                get(),
                get(named(LOGIN_USECASE)),
                get(named(ConcurrencyModule.IO)))
        }
    }
}
