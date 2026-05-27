package com.lazysloth.pocketlog.ui.screen.authentication.viewmodel

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lazysloth.pocketlog.data.LoginEvent
import com.lazysloth.pocketlog.data.User
import com.lazysloth.pocketlog.database.repository.UserRepository
import com.lazysloth.pocketlog.di.UserPersists
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest

class AuthViewModel(
    private val userRepository: UserRepository, private val userPersists: UserPersists
) : ViewModel() {

    //    val passwordExists: StateFlow<Boolean> = userRepository.
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)


    val _signUpUiState = MutableStateFlow(SignupUiState())
    val signUpUiState = _signUpUiState.asStateFlow()

    //    val signUpUiState: StateFlow<SignupUiState> = _signUpUiState.asStateFlow()
    val context = LocalContext
    private var auth = Firebase.auth
    private var db: FirebaseAuth = FirebaseAuth.getInstance()

    val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()


    val _loginEvent = Channel<LoginEvent>()
    val loginEvent = _loginEvent.receiveAsFlow()



    fun onIdentifierChange(identifier: String) {
        _loginUiState.update {
            it.copy(
                identifier = identifier
            )
        }
    }

    fun onPasswordChange(password: String) {
        _loginUiState.update {
            it.copy(
                password = password
            )
        }
    }

    fun verifyOtp(otp: String) {

    }

    fun resendVerificationEmail() {

    }

    fun resendOtp(emailOrPhone: String) {

    }

    fun checkEmailVerification() {
        auth.currentUser?.reload() ?: throw Exception("user not found")
        if (auth.currentUser?.isEmailVerified == true) {
            _signUpUiState.update {
                it.copy(
                    isSuccess = true
                )
            }
        }
    }

    fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }


    val actionCodeSettings = actionCodeSettings {
        url = "https://pocket-log.firebaseapp.com/finishSignUp"
        handleCodeInApp = true
        setAndroidPackageName(
            "com.lazysloth.pocketlog",
            true,
            null
        )
    }

    fun signUp(signupState: SignupUiState) {
        // [START create_user_with_email]
        viewModelScope.launch(Dispatchers.IO + SupervisorJob()) {
            try {
                println("signup username = ${signupState.username}")
                val result =
                    auth.createUserWithEmailAndPassword(signupState.email, signupState.password)
                        .await()
                val firebaseUser = result.user ?: throw Exception("user creation failed ")
                println("firebaseUser = $firebaseUser")
                firebaseUser.sendEmailVerification(actionCodeSettings)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("EMAIL", "Verification email sent.")
                        } else {
                            Log.e("EMAIL", "Failed: ${task.exception}")
                        }
                    }

                val firebaseUid = firebaseUser.uid
                userPersists.currentId = firebaseUid

                Log.d("AuthViewModel", "Signup successful -> UID: ${firebaseUser.uid}")
                println("firebaseUid = ${firebaseUser.uid}")
                userPersists.currentId = firebaseUser.uid
                _signUpUiState.update { it.copy(isSuccess = true, error = null) }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Signup failed", e)
                val errorMessage = when {
                    e.message?.contains(
                        "already in use",
                        ignoreCase = true
                    ) == true -> "This email is already registered. Try logging in."

                    e.message?.contains(
                        "weak password",
                        ignoreCase = true
                    ) == true || e.message?.contains(
                        "password",
                        ignoreCase = true
                    ) == true -> "Password should be at least 6 characters long."

                    e is FirebaseAuthException -> e.message ?: "Authentication error"
                    e is Job -> "Operation cancelled. Please try again."
                    else -> e.message ?: "Something went wrong. Please try again."
                }
                _signUpUiState.update { it.copy(error = errorMessage) }
            }
        }
    }

    fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        viewModelScope.launch(Dispatchers.IO + SupervisorJob()) {
            try{
                val result =
                    auth.signInWithEmailAndPassword(email, password).await()
                checkEmailVerification()
                val firebaseUser = result.user ?: throw Exception("user login failed..")
                if(_signUpUiState.value.isSuccess){
                    _loginEvent.send(LoginEvent.Success)
                    userPersists.currentId = firebaseUser.uid
                    Log.d(TAG, "signInWithEmail:success")
                }
                else {
                    _loginEvent.send(LoginEvent.Failure("please verify your email, check your spam folder"))
                    Log.w(TAG, "signInWithEmail:failure")
                }
            }
            catch (e: FirebaseAuthInvalidCredentialsException){
                _loginEvent.send(LoginEvent.Failure("Email or password is wrong"))
            }
            catch (e: FirebaseAuthInvalidUserException){
                _loginEvent.send(LoginEvent.Failure("Invalid User"))
            }
            catch (e: Exception){
                _loginEvent.send(LoginEvent.Failure("Unknown error"))
            }



            // [END sign_in_with_email]}
        }
    }

    fun logOut() {
        auth.signOut()
        userPersists.logout()
    }

    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener() { task ->
            // Email Verification sent
        }
        // [END send_email_verification]
    }

//    fun saveUser(userState: SignupUiState) {
//        viewModelScope.launch {
//            userRepository.saveUser(userState.toUser())
//
//        }
//    }

    fun getUserIdByIdentifier(identifier: String) {
        viewModelScope.launch {

            if (identifier.contains("@")) {
                userPersists.currentId = userRepository.getIdByEmailId(identifier)
            } else {
                userPersists.currentId = userRepository.getIdByUsername(identifier)
            }
            println("current id : ${userPersists.currentId}")
        }

    }


    companion object {
        private const val TAG = "EmailPassword"
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
    val firebaseUid: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val emailId: String = "",
    val checkPassword: Boolean = false,

    )

fun UserEntryUiState.toUser(): User = User(
    username = username,
    firebaseUid = firebaseUid,
    firstName = firstName,
    lastName = lastName,
    password = password,
    emailId = emailId,
)