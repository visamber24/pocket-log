package com.lazysloth.pocketlog.ui.screen.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.database.repository.UserRepository
import com.lazysloth.pocketlog.di.UserPersists
import kotlinx.coroutines.launch
import java.security.MessageDigest

class AuthViewModel(private val userRepository: UserRepository,private val userPersists: UserPersists) : ViewModel() {

//    val passwordExists: StateFlow<Boolean> = userRepository.
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun hashPassword(password: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun saveUser(userState : SignupUiState) {
        viewModelScope.launch {
            val userId = userPersists.currentId
            userRepository.saveUser(userState.toUser())

        }
    }
    suspend fun getUserIdByIdentifier(identifier: String){
        if(identifier.contains("@")){
            userPersists.currentId = userRepository.getIdByEmailId(identifier)!!
        }
        else{
            userPersists.currentId = userRepository.getIdByUsername(identifier)!!
        }

    }

    suspend fun verifyPassword(identifier: String, password: String): Boolean {
        if(identifier.contains("@")){
            val savedPassword = userRepository.getUserPasswordByEmail(identifier)
            return savedPassword == password
        }
        else{
            val savedPassword = userRepository.getUserPasswordByUsername(identifier)
            return savedPassword == password
        }
    }


}
//fun SignupUiState.toTransaction(): Transaction = Transaction(
//    userId = id,
//    id = TODO(),
//    amount = TODO(),
//    account = TODO(),
//    category = TODO(),
//    transactionType = TODO(),
//    note = TODO(),
//    description = TODO(),
//    dateTime = TODO(),
//)


data class UserEntryUiState(
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val emailId: String = "",
)