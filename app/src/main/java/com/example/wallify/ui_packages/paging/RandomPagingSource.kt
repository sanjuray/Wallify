package com.example.wallify.ui_packages.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wallify.ui_packages.model.Data
import com.example.wallify.ui_packages.networking.RetrofitServices

class RandomPagingSource(
    private val apiServices:RetrofitServices
): PagingSource<Int, Data>() {

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val nextPage = params.key ?: FIRST_PAGE_INDEX
            val responseHome = apiServices.getRandomFromApi(nextPage)
            LoadResult.Page(
                data = responseHome.data,
                prevKey = null,
                nextKey = responseHome.paggination.next.page
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    companion object{
        private const val FIRST_PAGE_INDEX = 1
    }

}