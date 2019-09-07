package com.wengelef.functionalandroid.di

import com.wengelef.cache.DB
import com.wengelef.cache.Database
import com.wengelef.getUsers
import com.wengelef.saveUser
import org.koin.dsl.module
import util.partially

object CacheModule {
    operator fun invoke() = module {
        single { Database(DB.driver(get())) }
        single { ::saveUser.partially(get()) }
        single { ::getUsers.partially(get()) }
    }
}