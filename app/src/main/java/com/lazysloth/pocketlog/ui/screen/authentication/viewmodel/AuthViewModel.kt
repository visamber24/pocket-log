package com.lazysloth.pocketlog.ui.screen.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.database.User
import com.lazysloth.pocketlog.database.data.PasswordManager
import com.lazysloth.pocketlog.database.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.security.MessageDigest

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

//    val passwordExists: StateFlow<Boolean> = userRepository.
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun hashPassword(password: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
    fun saveUser() {
        viewModelScope.launch {
            userRepository.saveUser(User())
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