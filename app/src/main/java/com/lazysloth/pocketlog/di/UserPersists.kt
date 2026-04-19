package com.lazysloth.pocketlog.di

import android.content.Context
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.autofill.ContentType
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.lazysloth.pocketlog.database.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class UserPersists(
    context: Context,
    private val userRepository: UserRepository
) {
    private val prefs =
        context.getSharedPreferences("session", Context.MODE_PRIVATE)
    private val prefs2 =
        context.getSharedPreferences("id", Context.MODE_PRIVATE)
    var currentId: Int
        get() = prefs.getInt("user_id", -1)
        set(value) = prefs.edit { putInt("user_id", value) }

    var transactionId: Int
        get() = prefs2.getInt("transaction_id", -1)
        set(value) = prefs2.edit { putInt("transaction_id", value) }

    suspend fun checkUser(): Boolean {

        return userRepository.checkUser()

    }

    fun logout() {
        prefs.edit { clear() }
    }
}