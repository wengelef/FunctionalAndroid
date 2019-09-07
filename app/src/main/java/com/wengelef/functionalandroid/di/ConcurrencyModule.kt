package com.wengelef.functionalandroid.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import util.Dispatcher
import util.IO

object ConcurrencyModule {
    operator fun invoke() = module {
        single<Dispatcher>(named("IO")) { IO }
    }
}