package com.wengelef.functionalandroid.di

import analyticslibrary.analyticsTracker
import analyticslibrary.loginEventMapper
import org.koin.dsl.module
import tracking.LoginTracker
import tracking.getTracker

object AnalyticsModule {
    operator fun invoke() = module {
        single {
            LoginTracker(getTracker(::analyticsTracker, ::loginEventMapper))
        }
    }
}