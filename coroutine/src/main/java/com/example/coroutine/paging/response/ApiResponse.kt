package com.example.coroutine.paging.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Dipak Kumar Mehta on 11/30/2021.
 */
data class ApiResponse(
    @SerializedName("ad")
    val ad: Ad,
    @SerializedName("data")
    val myData: List<Data>,
    @SerializedName( "page")
    val page: Int,
    @SerializedName("per_page")
    val per_page: Int,
    @SerializedName( "total")
    val total: Int,
    @SerializedName("total_pages")
    val total_pages: Int
)