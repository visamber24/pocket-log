package com.lazysloth.pocketlog.ui.screen.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.database.User
import com.lazysloth.pocketlog.database.data.PasswordManager
import com.lazysloth.pocketlog.database.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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

    private val _userEntryUiState = MutableStateFlow(SignupUiState())
    val userEntryUiState: StateFlow<SignupUiState> = _userEntryUiState.asStateFlow()
    fun saveUser(userState : SignupUiState) {
        viewModelScope.launch {
            userRepository.saveUser(userState.toUser())
        }
    }

    suspend fun verifyPassword(email: String, password: String): Boolean {
        val savedPassword = userRepository.getUserPassword(email) ?: false
        return savedPassword == password


    }


}



data class UserEntryUiState(
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val emailId: String = "",
)