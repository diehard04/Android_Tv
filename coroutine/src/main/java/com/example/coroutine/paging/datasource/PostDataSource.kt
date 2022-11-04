package com.example.coroutine.paging.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.coroutine.paging.data.APIService
import com.example.coroutine.paging.response.Data
import java.io.IOException

/**
 * Created by Dipak Kumar Mehta on 11/30/2021.
 */

class PostDataSource(private val apiService: APIService) : PagingSource<Int, Data>() {

    private val DEFAULT_PAGE_INDEX= 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val currentLoadingPageKey = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = apiService.getListData(currentLoadingPageKey)
            val responseData = mutableListOf<Data>()
            val data = response.body()?.myData ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == DEFAULT_PAGE_INDEX) null else currentLoadingPageKey - 1
            val nextKey = if(responseData.isEmpty()) null else currentLoadingPageKey + 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        TODO("Not yet implemented")
    }
}