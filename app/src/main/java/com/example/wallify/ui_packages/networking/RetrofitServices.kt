package com.example.wallify.ui_packages.networking

import com.example.wallify.ui_packages.model.Wallpaper
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {

    @GET("Random")
    suspend fun getHomeFromApi(@Query("page") page: Int): Wallpaper

    @GET("Popular")
    suspend fun getPopularFromApi(@Query("page") page: Int): Wallpaper

    @GET("latest")
    suspend fun getRandomFromApi(@Query("page") page: Int): Wallpaper

    @GET("category")
    suspend fun getCategoryFromApi(
        @Query("page") page: Int,
        @Query("category") category: String
    ): Wallpaper

}