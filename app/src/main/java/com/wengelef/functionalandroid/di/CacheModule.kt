package com.wengelef.functionalandroid.di

import com.wengelef.cache.DB
import com.wengelef.cache.Database
import com.wengelef.saveUser
import org.koin.dsl.module

object CacheModule {
    operator fun invoke() = module {
        single { Database(DB.driver(get())) }
        single { saveUser(get()) }
    }
}