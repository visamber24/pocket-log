package com.lazysloth.pocketlog.ui.screen.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.database.repository.UserRepository
import com.lazysloth.pocketlog.di.UserPersists
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
    private val userPersists: UserPersists
): ViewModel() {

//    fun verifyPassword(identifier: String, password: String) {
//        viewModelScope.launch {
//            if (identifier.contains("@")) {
//                val savedPassword = userRepository.getUserPasswordByEmail(identifier)
//                val isPasswordCorrect = savedPassword == password
//                _uiState.update {
//                    it.copy(
//                        checkPassword = isPasswordCorrect
//                    )
//                }
//            } else {
//                val savedPassword = userRepository.getUserPasswordByUsername(identifier)
//                val isPasswordCorrect = savedPassword == password
//                _uiState.update {
//                    it.copy(
//                        checkPassword = isPasswordCorrect
//                    )
//                }
//            }
//        }
//    }
}
data class LoginUiState(
    val identifier: String = "",
    val password: String = "",
    val firebaseUid : String = "",
    val isPasswordMatch: Boolean = false,
)