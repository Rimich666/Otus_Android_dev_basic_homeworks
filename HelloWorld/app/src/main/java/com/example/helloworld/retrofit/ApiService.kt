package com.example.helloworld.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("movie?token=MKRJKN4-Q0B463J-J85RBPK-ENWYABY&search=Зеленая миля&field=name")
    fun getFilms(): Call<List<Item>>
}