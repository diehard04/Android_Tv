package com.example.coroutine.data.api

import com.example.coroutine.data.model.ApiUser
import retrofit2.http.GET

/**
 * Created by Dipak Kumar Mehta on 11/25/2021.
 */
interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<ApiUser>

    @GET("more-users")
    suspend fun getMoreUsers(): List<ApiUser>

    @GET("error")
    suspend fun getUsersWithError(): List<ApiUser>
}