package com.example.androidtv.data.repository

import com.example.androidtv.data.api.ApiHelper
import com.example.androidtv.data.model.User
import io.reactivex.Single

/**
 * Created by Dipak Kumar Mehta on 10/21/2021.
 */
class UserRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

}