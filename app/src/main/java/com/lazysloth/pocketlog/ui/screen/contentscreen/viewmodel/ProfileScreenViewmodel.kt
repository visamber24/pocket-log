package com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel

import android.se.omapi.Session
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.database.repository.UserRepository
import com.lazysloth.pocketlog.di.UserPersists
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileScreenViewmodel(
    private val userRepository: UserRepository,
    val session: UserPersists
) : ViewModel() {
    val _uiState = MutableStateFlow(ProfileUiState())
    var uiState = _uiState.asStateFlow()
    var username by mutableStateOf<String?>("")

    init {
        getUsername()
    }

    fun getUsername() {
        viewModelScope.launch {
            username = userRepository.getUsernameById(session.currentId)
            if (username != null) {
                _uiState.update {
                    it.copy(username = username!!)
                }
            } else {
                // fallback (important)
                _uiState.update {
                    it.copy(username = "Unknown User")
                }
            }
        }
        println("Username is $username")
    }
}
data class ProfileUiState(
    val username : String = "" ,
)