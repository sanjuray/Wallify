package com.example.wallify.ui_packages.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPI {

    private const val BASE_URL = "https://woolapi.herokuapp.com/wallpapers/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: RetrofitServices = retrofit.create(
        RetrofitServices::class.java
    )
}
