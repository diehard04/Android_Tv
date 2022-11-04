package com.example.coroutine.paging.data

import com.example.coroutine.paging.response.ApiResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Dipak Kumar Mehta on 11/29/2021.
 */
interface APIService {

    @GET("api/users")
    suspend fun getListData(@Query("page") pageNumber: Int): Response<ApiResponse>

    companion object {
//        private val moshi = Moshi.Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()

        fun getApiService() = Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }
}