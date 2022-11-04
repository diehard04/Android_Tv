package com.example.coroutine.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.coroutine.paging.data.APIService
import com.example.coroutine.paging.datasource.PostDataSource

/**
 * Created by Dipak Kumar Mehta on 11/29/2021.
 */

class PagingViewModel(private val apiService: APIService) : ViewModel() {

    val listData = Pager(PagingConfig(pageSize = 6)) {
        PostDataSource(apiService)
    }.flow.cachedIn(viewModelScope)
}