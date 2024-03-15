package com.example.wallify.ui_packages.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.wallify.ui_packages.paging.HomePagingSource
import com.example.wallify.ui_packages.repository.MainRepository

class HomeViewModel: ViewModel(){
    private val repo = MainRepository()
    val homePage = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
         HomePagingSource(repo.retroService())
        }).flow.cachedIn(viewModelScope)
}