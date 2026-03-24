package com.lazysloth.pocketlog

import android.app.Application
import com.lazysloth.pocketlog.database.AppContainer
import com.lazysloth.pocketlog.database.AppDataContainer
import com.lazysloth.pocketlog.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class ExpenseApplication : Application() {
    lateinit var container : AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)

        startKoin {
            androidContext(this@ExpenseApplication)
            modules(appModule)
        }
    }
}