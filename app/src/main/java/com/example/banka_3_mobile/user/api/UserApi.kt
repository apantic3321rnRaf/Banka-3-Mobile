package com.example.banka_3_mobile.user.api

import com.example.banka_3_mobile.user.model.LoginPostRequest
import com.example.banka_3_mobile.user.model.LoginPostResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("auth/login/client")
    suspend fun login(
        @Body request: LoginPostRequest
    ): LoginPostResponse
}