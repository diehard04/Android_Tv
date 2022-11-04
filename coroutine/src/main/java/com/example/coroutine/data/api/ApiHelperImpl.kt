package com.example.coroutine.data.api

/**
 * Created by Dipak Kumar Mehta on 11/25/2021.
 */
class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers() = apiService.getUsers()

    override suspend fun getMoreUsers() = apiService.getMoreUsers()

    override suspend fun getUsersWithError() = apiService.getUsersWithError()

}