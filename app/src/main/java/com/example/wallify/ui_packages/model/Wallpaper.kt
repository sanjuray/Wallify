package com.example.wallify.ui_packages.model

data class Wallpaper(
    val count: Int,
    val `data`: List<Data>,
    val paggination: Paggination,
    val success: Boolean
)