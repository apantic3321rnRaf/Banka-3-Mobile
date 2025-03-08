package com.example.banka_3_mobile.networking.di

import android.util.Log
import com.example.banka_3_mobile.networking.serialization.AppJson
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
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
            .baseUrl("http://localhost:8080/api/")
            .client(okHttpClient)
            .addConverterFactory(AppJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}