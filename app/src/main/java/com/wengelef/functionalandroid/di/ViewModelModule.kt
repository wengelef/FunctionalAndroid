package com.wengelef.functionalandroid.di

import analyticslibrary.adobeTracker
import analyticslibrary.loginEventMapper
import com.wengelef.functionalandroid.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import tracking.getTracker

object ViewModelModule {
    operator fun invoke(): Module = module {
        viewModel {
            LoginViewModel(
                getTracker(
                    ::adobeTracker,
                    ::loginEventMapper
                )
            )
        }
    }
}