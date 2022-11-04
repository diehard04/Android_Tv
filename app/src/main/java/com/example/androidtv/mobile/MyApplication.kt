package com.example.androidtv.mobile

import android.app.Application
//import com.facebook.ads.AudienceNetworkAds

/**
 * Created by Dipak Kumar Mehta on 12/21/2021.
 */
class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize the Audience Network SDK
        //AudienceNetworkAds.initialize(this);
    }
}