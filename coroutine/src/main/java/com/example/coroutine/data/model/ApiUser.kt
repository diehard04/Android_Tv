package com.example.coroutine.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Dipak Kumar Mehta on 11/25/2021.
 */

data class ApiUser(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("email")
    val email: String = "",

    @SerializedName("avatar")
    val avatar: String = ""
)