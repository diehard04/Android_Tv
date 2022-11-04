package com.example.androidtv.mobile.ads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.androidtv.R
import com.facebook.ads.*;
import android.widget.Toast

import com.facebook.ads.AdError

import com.facebook.ads.AdListener

class FacebookBannerAdsActivity : AppCompatActivity() {

    private var adView:AdView ?= null
    private var bannerContainer:LinearLayout ?= null
    private var adListener:AdListener ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_ads)
        initListener()
        initView()
    }

    private fun initListener() {
      adListener = object : AdListener {
            override fun onError(ad: Ad?, adError: AdError) {
                // Ad error callback
                Toast.makeText(this@FacebookBannerAdsActivity,
                    "Error: " + adError.errorMessage,
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onAdLoaded(ad: Ad?) {
                // Ad loaded callback
                Toast.makeText(this@FacebookBannerAdsActivity,
                    "Ad Loaded",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onAdClicked(ad: Ad?) {
                Toast.makeText(this@FacebookBannerAdsActivity,
                    " Ad Clicked ",
                    Toast.LENGTH_LONG
                ).show()
                // Ad clicked callback
            }

            override fun onLoggingImpression(ad: Ad?) {
                // Ad impression logged callback
            }
        }
    }

    private fun initView() {
        adView = AdView(this, ActivityConfig.FB_BANNER_50, AdSize.BANNER_HEIGHT_90)
        bannerContainer = findViewById(R.id.banner_container)
        bannerContainer?.addView(adView)
        AdSettings.addTestDevice("2d623805-5943-4923-9623-38b2fb4e942c")
        adView?.loadAd(adView?.buildLoadAdConfig()?.withAdListener(adListener)?.build())
    }

    override fun onDestroy() {
        if (adView != null) {
            adView?.destroy()
        }
        super.onDestroy()
    }
}