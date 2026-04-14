package com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel

import android.se.omapi.Session
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.database.repository.UserRepository
import com.lazysloth.pocketlog.di.UserPersists
import kotlinx.coroutines.launch

class ProfileScreenViewmodel(
    private val userRepository: UserRepository,
    val session: UserPersists
): ViewModel() {
    var username by mutableStateOf<String>("")
    init{
         getUsername()
    }
    fun getUsername() {
        viewModelScope.launch { username = userRepository.getUsernameById(session.currentId)!! }
        println("Username is $username")
    }
}