package com.example.androidtv.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Dipak Kumar Mehta on 10/21/2021.
 */
data class User(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("avatar")
    val avatar: String = ""
)