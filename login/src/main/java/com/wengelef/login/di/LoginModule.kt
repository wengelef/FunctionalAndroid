package com.wengelef.login.di

import com.wengelef.autil.di.DELETE_USERS_USE_CASE
import com.wengelef.autil.di.GET_USERS_USE_CASE
import com.wengelef.autil.di.IO
import com.wengelef.autil.di.LOGIN_USE_CASE
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
                get(named(LOGIN_USE_CASE)),
                get(named(GET_USERS_USE_CASE)),
                get(named(DELETE_USERS_USE_CASE)),
                get(named(IO)))
        }
    }
}
