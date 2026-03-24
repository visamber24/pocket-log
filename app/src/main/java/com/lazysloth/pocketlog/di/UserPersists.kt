package com.lazysloth.pocketlog.di

import android.content.Context
import androidx.core.content.edit


class UserPersists (
  context: Context
) {
    private val prefs =
        context.getSharedPreferences("session",Context.MODE_PRIVATE)
    var currentId: Int
        get() = prefs.getInt("user_id",-1)
        set(value) = prefs.edit { putInt("user_id", value) }

    fun logout(){
        prefs.edit { clear() }
    }
}