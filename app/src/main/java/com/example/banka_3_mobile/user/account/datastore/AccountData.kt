package com.example.banka_3_mobile.user.account.datastore

import kotlinx.serialization.Serializable

@Serializable
data class AccountData(
    val email: String,
    val client_id: String,
    val token: String,
    val password: String,
) {
    companion object {
        val EMPTY = AccountData(email = "", client_id = "", token = "", password = "")
    }
}