package com.lazysloth.pocketlog.ui.screen.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.data.PasswordManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthViewModel(private val passwordManager: PasswordManager) : ViewModel() {

    val passwordExists: StateFlow<Boolean> = passwordManager.passwordExistsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun savePassword(password: String) {
        viewModelScope.launch {
            passwordManager.savePassword(password)
        }
    }

    val savedPassword: StateFlow<String?> = passwordManager.savedPasswordFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}

class AuthViewModelFactory(
    private val passwordManager: PasswordManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(passwordManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}