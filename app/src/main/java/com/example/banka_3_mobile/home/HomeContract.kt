package com.example.banka_3_mobile.home

import com.example.banka_3_mobile.user.model.ClientGetResponse

interface HomeContract {
    data class HomeUiState (
        val loggedIn: Boolean = false,
        val email: String = "",
        val firstName: String = "",
        val lastName: String = "",
        val client: ClientGetResponse? = null,
        val error: String? = null,
        val fetching: Boolean = true
    )

    sealed class HomeUIEvent {
        data object Logout: HomeUIEvent()
        data object Verify: HomeUIEvent()
        //data class TypingEmail(val email: String): HomeUIEvent()
        //data class TypingPassword(val password: String): HomeUIEvent()
    }
}