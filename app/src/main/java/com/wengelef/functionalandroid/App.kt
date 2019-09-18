package com.wengelef.functionalandroid

import androidx.multidex.MultiDexApplication
import com.wengelef.functionalandroid.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf(
                CacheModule(),
                DomainModule(),
                RepositoryModule(),
                AnalyticsModule(),
                ConcurrencyModule()))
        }
    }

}