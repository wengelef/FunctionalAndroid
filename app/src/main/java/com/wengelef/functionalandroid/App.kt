package com.wengelef.functionalandroid

import androidx.multidex.MultiDexApplication
import com.wengelef.functionalandroid.di.AnalyticsModule
import com.wengelef.functionalandroid.di.CacheModule
import com.wengelef.functionalandroid.di.DomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf(CacheModule(), DomainModule(), AnalyticsModule()))
        }
    }

}