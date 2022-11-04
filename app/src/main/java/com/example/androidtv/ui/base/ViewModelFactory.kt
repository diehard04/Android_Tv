package com.example.androidtv.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidtv.data.api.ApiHelper
import com.example.androidtv.data.repository.UserRepository
import com.example.androidtv.viewmodel.UserViewModel

/**
 * Created by Dipak Kumar Mehta on 10/21/2021.
 */
class ViewModelFactory(private val apiHelper: ApiHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(UserRepository(apiHelper)) as  T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}