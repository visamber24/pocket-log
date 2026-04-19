package com.lazysloth.pocketlog

import android.app.Application
import com.lazysloth.pocketlog.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class ExpenseApplication : Application() {


    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext(this@ExpenseApplication)
            modules(appModule)
        }
    }
}