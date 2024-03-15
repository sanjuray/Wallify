package com.example.wallify.ui_packages.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wallify.ui_packages.model.Data
import com.example.wallify.ui_packages.networking.RetrofitServices

class CategoryPagingSource(
    private val apiServices: RetrofitServices,
    private val category: String
): PagingSource<Int,Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let{anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try{
            val nextPage = params.key ?: FIRST_KEY_INDEX
            val response = apiServices.getCategoryFromApi(nextPage,category)
            LoadResult.Page(
                data = response.data,
                prevKey = null,
                nextKey = response.paggination.next.page
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    companion object{
        private const val FIRST_KEY_INDEX = 1
    }
}