package com.example.androidtv.data.api

/**
 * Created by Dipak Kumar Mehta on 10/21/2021.
 */

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUsers() = apiService.getUsers()
}