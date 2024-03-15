package com.example.wallify.ui_packages.repository

import com.example.wallify.ui_packages.networking.RetrofitAPI
import com.example.wallify.ui_packages.networking.RetrofitServices

class MainRepository {

    fun retroService(): RetrofitServices = RetrofitAPI.apiService

}