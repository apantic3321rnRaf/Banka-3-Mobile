package com.example.banka_3_mobile.networking.di

import android.util.Log
import com.example.banka_3_mobile.networking.serialization.AppJson
import com.example.banka_3_mobile.user.account.datastore.AccountDataStore
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserNetworkingModule {

    @Singleton
    @Provides
    @Named("UserOkHttp")
    fun provideOkHttpClient(
        accountDataStore: AccountDataStore
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                chain ->
                // Synchronously fetch the token from your datastore
                val token = runBlocking {
                    // Adjust this line according to how you store your token.
                    // For example, if your AccountData data class has a "token" field:
                    accountDataStore.data.first().token
                }
                // Build the new request with the Authorization header if token exists
                val requestBuilder = chain.request().newBuilder()
                if (!token.isNullOrEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                chain.proceed(requestBuilder.build())
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            )
            .build()
    }

    @Singleton
    @Provides
    @Named("UserRetrofit")
    fun provideRetrofitClient(
        @Named("UserOkHttp") okHttpClient: OkHttpClient,
    ): Retrofit {
        Log.d("raf", "Provide retrofit for user")
        return Retrofit.Builder()
            //.baseUrl("http://localhost:8080/api/")
            .baseUrl("http://10.0.2.2:8080/api/")
            .client(okHttpClient)
            .addConverterFactory(AppJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}