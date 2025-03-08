package com.example.banka_3_mobile.user.account.datastore

import kotlinx.serialization.Serializable

@Serializable
data class AccountData(
    val fullName: String,
    val email: String,
    val client_id: String
) {
    companion object {
        val EMPTY = AccountData(fullName = "", email = "", client_id = "")
    }
}