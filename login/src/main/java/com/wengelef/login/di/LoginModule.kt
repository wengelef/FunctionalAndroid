package com.wengelef.login.di

import com.wengelef.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object LoginModule {
    operator fun invoke(): Module = module {
        viewModel { LoginViewModel(get(), get(), get()) }
    }
}
