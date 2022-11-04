package com.example.androidtv.tvlaunchermodel

import com.google.gson.annotations.SerializedName

/**
 * Created by Dipak Kumar Mehta on 10/14/2021.
 */

class HeaderList {
    @SerializedName("status_code")
    var statusCode: String? = null

    @SerializedName("header")
    var headerTypesList: List<HeaderTypes>? = null

    inner class HeaderTypes {
        @SerializedName("header_name")
        var headerName: String? = null

        @SerializedName("app_info")
        var appInfoList: List<AppInfo>? = null

        inner class AppInfo {
            var isFirstItem = false

            @SerializedName("id")
            var id: String? = null

            @SerializedName("imageurl")
            var imageUrl: String? = null

            @SerializedName("adurl")
            var adUrl: String? = null

            @SerializedName("videourl")
            var videoUrl: String? = null

            @SerializedName("title")
            var title: String? = null

            @SerializedName("duration")
            var duration: String? = null

            @SerializedName("description")
            var description: String? = null

            @SerializedName("rating")
            var rating: String? = null
        }
    }
}
