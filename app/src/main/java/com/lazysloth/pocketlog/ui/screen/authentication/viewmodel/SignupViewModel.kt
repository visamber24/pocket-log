package com.lazysloth.pocketlog.ui.screen.authentication.viewmodel

import androidx.lifecycle.ViewModel
import com.lazysloth.pocketlog.ui.screen.authentication.uiState.SignupUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignupViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState : StateFlow<SignupUiState> = _uiState.asStateFlow()

    fun onUsernameChange(username: String) {
        _uiState.update {
            it.copy(username = username)
        }
    }
    fun onFirstNameChange(firstName: String) {
        _uiState.update {
            it.copy(firstName = firstName)
        }
    }
    fun onLastNameChange(lastName: String) {
        _uiState.update {
            it.copy( lastName= lastName)
        }
    }
    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }
    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(password= password)
        }
    }
}