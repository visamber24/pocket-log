package com.lazysloth.pocketlog.ui.screen.authentication.viewmodel

import androidx.lifecycle.ViewModel
import coil.compose.AsyncImagePainter
import com.lazysloth.pocketlog.database.User
import com.lazysloth.pocketlog.ui.screen.authentication.viewmodel.SignupUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignupViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState : StateFlow<SignupUiState> = _uiState.asStateFlow()

    fun onUsernameChange(username: String) {
        if (_uiState.value.username.contains(Regex("""[\p{Punct}\p{S}]"""))) {
            changeIsError(true)
        }
        else changeIsError(false)
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
    fun onConfirmPasswordChange(password: String){
        _uiState.update {
            it.copy(confirmPassword = password)
        }
    }
    fun onIdentifierChange(value: String){
        _uiState.update {
            it.copy(identifier = value)
        }
    }
    fun changeIsError(isError: Boolean){
        _uiState.update {
            it.copy(isError = isError)
        }
    }
}

data class SignupUiState(
    val id: Int = 0,
    val identifier: String = "",
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isError: Boolean = false,
)
fun SignupUiState.toUser() : User = User(
    id = id,
    username = username,
    emailId = email,
    password = password
)