package com.example.coroutine.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coroutine.paging.data.APIService

/**
 * Created by Dipak Kumar Mehta on 11/30/2021.
 */
class PagingViewModelFactory(private val apiService: APIService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PagingViewModel::class.java)) {
            return PagingViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}