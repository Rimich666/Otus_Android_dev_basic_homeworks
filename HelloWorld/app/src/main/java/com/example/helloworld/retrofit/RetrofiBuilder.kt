package com.example.helloworld.retrofit

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofiBuilder {
    private const val BASE_URL = "https://api.kinopoisk.dev/"

    private fun getRetrofit(): Retrofit{
        Log.d("tag", BASE_URL)
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}