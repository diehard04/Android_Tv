package com.example.androidtv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.androidtv.data.repository.UserRepository
import com.example.androidtv.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

/**
 * Created by Dipak Kumar Mehta on 10/21/2021.
 */
class UserViewModel(private val userRepository: UserRepository) :ViewModel() {

    fun getUser() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = userRepository.getUsers()))
        } catch (exception:Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
        }
    }
}