package com.example.coroutine.paging.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Dipak Kumar Mehta on 11/30/2021.
 */
data class Data(@SerializedName( "avatar")
    val avatar: String,
    @SerializedName( "email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName( "id")
    val id: Int,
    @SerializedName("last_name")
    val lastName: String
)