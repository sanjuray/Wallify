package com.example.wallify.ui_packages.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CategoryViewModelFactory(private val categoryId: String):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CategoriesViewModel::class.java)){
            return CategoriesViewModel(categoryId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}