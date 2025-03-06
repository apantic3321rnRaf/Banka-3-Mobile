package com.example.banka_3_mobile.login

interface LoginContract {
    data class LoginUiState (
        val email: String = "",
        val fullName: String = "",
        val loggedIn: Boolean = false,
        val incorrectEmailFormat: Boolean = false,
        val incorrectFullNameFormat: Boolean = false,
        val error: Throwable? = null,
    )

    sealed class LoginUIEvent {
        data object Login: LoginUIEvent()
        //data class TypingEmail(val email: String): LoginUIEvent()
        //data class TypingFullName(val fullName: String): LoginUIEvent()
    }
}