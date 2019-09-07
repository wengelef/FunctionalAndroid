package com.wengelef.functionalandroid.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import util.Dispatcher

object ConcurrencyModule {
    operator fun invoke() = module {
        single<Dispatcher>(named(IO)) { util.IO }
    }

    const val IO = "IO"
}