package com.wengelef.functionalandroid.di

import analyticslibrary.analyticsTracker
import analyticslibrary.loginEventMapper
import org.koin.dsl.module
import tracking.getTracker

object AnalyticsModule {
    operator fun invoke() = module {
        single {
            getTracker(::analyticsTracker, ::loginEventMapper)
        }
    }
}