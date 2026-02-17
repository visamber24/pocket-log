package com.lazysloth.pocketlog

import android.app.Application
import com.lazysloth.pocketlog.database.AppContainer
import com.lazysloth.pocketlog.database.AppDataContainer

class ExpenseApplication : Application() {
    lateinit var container : AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}