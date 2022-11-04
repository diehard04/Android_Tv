package com.example.coroutine.data.api

import com.example.coroutine.data.model.ApiUser

/**
 * Created by Dipak Kumar Mehta on 11/25/2021.
 */

interface ApiHelper {

    suspend fun getUsers(): List<ApiUser>

    suspend fun getMoreUsers(): List<ApiUser>

    suspend fun getUsersWithError(): List<ApiUser>

}