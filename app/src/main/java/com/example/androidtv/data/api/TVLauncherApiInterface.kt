package com.example.androidtv.data.api

import com.example.androidtv.tvlaunchermodel.HeaderList
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Dipak Kumar Mehta on 10/14/2021.
 */
interface TVLauncherApiInterface {

    @GET("data.json")
    fun getHeaderList(): Single<Response<HeaderList>>
}