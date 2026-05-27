package com.lazysloth.pocketlog.data

sealed class LoginEvent {
    object Success : LoginEvent()
    data class Failure(val message: String) : LoginEvent()
}