package com.example.wallify.ui_packages.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wallify.ui_packages.model.Data
import com.example.wallify.ui_packages.paging.CategoryPagingSource
import com.example.wallify.ui_packages.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CategoriesViewModel(private var categoryId: String):ViewModel() {
    private val repo = MainRepository()

    var data: MutableLiveData<PagingData<Data>> = MutableLiveData()

    init {
        viewModelScope.launch {
            loadCategory(categoryId).collect{
                data.postValue(it)
            }
        }
    }

    private fun loadCategory(category: String): Flow<PagingData<Data>>{
        return Pager(config = PagingConfig(pageSize = 30),
            pagingSourceFactory = {CategoryPagingSource(repo.retroService(),category)}
        ).flow.cachedIn(viewModelScope)
    }
}