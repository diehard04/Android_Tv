package com.example.coroutine.paging.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Dipak Kumar Mehta on 11/30/2021.
 */
data class Ad(@SerializedName( "company")
    val company: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("url")
    val url: String
)