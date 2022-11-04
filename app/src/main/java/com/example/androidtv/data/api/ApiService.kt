package com.example.androidtv.data.api

import com.example.androidtv.data.model.User
import retrofit2.http.GET

/**
 * Created by Dipak Kumar Mehta on 10/21/2021.
 */
interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>

}