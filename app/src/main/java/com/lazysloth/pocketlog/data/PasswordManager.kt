package com.lazysloth.pocketlog.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// At the top level of your kotlin file:
val Context.dataStore:DataStore<Preferences> by preferencesDataStore(name = "settings")

class PasswordManager(private val context: Context) {

    private val passwordKey = stringPreferencesKey("user_password")

    // Flow to check if a password exists
    val passwordExistsFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[passwordKey] != null
        }

    // Flow to get the saved password
    val savedPasswordFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[passwordKey]
        }

    // Function to save the password
    suspend fun savePassword(password: String) {
        context.dataStore.edit { settings ->
            settings[passwordKey] = password
        }
    }
}
