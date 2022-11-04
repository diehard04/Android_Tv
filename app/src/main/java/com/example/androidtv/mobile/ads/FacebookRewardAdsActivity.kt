package com.example.androidtv.mobile.ads

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.facebook.ads.*
import com.facebook.ads.RewardedVideoAd

import android.widget.TextView
import com.facebook.ads.RewardData

import android.widget.Button
import android.widget.Toast

import com.facebook.ads.AdError
import com.example.androidtv.R

/**
 * Created by Dipak Kumar Mehta on 12/22/2021.
 */
class FacebookRewardAdsActivity:AppCompatActivity(), S2SRewardedVideoAdListener {

    private var rewardedVideoAdStatusLabel: TextView? = null
    private var loadRewardedVideoButton: Button? = null
    private var showRewardedVideoButton: Button? = null

    private var rewardedVideoAd: RewardedVideoAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook__reward_ads)
        loadAdd()
    }

    private fun loadAdd() {
        rewardedVideoAdStatusLabel = findViewById(R.id.rewardedVideoAdStatusLabel)
        loadRewardedVideoButton = findViewById(R.id.loadRewardedVideoButton) as Button
        showRewardedVideoButton = findViewById(R.id.showRewardedVideoButton) as Button

        loadRewardedVideoButton!!.setOnClickListener(object : View.OnClickListener {

                override fun onClick(v: View?) {
                    if (rewardedVideoAd != null) {
                        rewardedVideoAd!!.destroy()
                        rewardedVideoAd = null
                    }
                    rewardedVideoAd = RewardedVideoAd(
                        this@FacebookRewardAdsActivity,
                        "YOUR_PLACEMENT_ID"
                    )
//                    val loadAdConfig = rewardedVideoAd!!
//                        .buildLoadAdConfig()
//                        .withAdListener(this@FacebookRewardAdsActivity)
//                        .withFailOnCacheFailureEnabled(true)
//                        .withRewardData(RewardData("YOUR_USER_ID", "YOUR_REWARD", 10))
//                        .build()
//                    rewardedVideoAd!!.loadAd(loadAdConfig)
                    setStatusLabelText("Loading rewarded video ad...")
                }
            })

        showRewardedVideoButton!!.setOnClickListener(
            object : View.OnClickListener {
                override fun onClick(v: View?) {
                    if (rewardedVideoAd == null || !rewardedVideoAd!!.isAdLoaded()
                        || rewardedVideoAd!!.isAdInvalidated()
                    ) {
                        setStatusLabelText("Ad not loaded. Click load to request an ad.")
                    } else {
                        rewardedVideoAd!!.show()
                        setStatusLabelText("")
                    }
                }
            })

    }

    override fun onError(ad: Ad, error: AdError) {
        if (ad === rewardedVideoAd) {
            setStatusLabelText("Rewarded video ad failed to load: " + error.errorMessage)
        }
    }

    override fun onAdLoaded(ad: Ad) {
        if (ad === rewardedVideoAd) {
            setStatusLabelText("Ad loaded. Click show to present!")
        }
    }

    override fun onAdClicked(ad: Ad?) {
        showToast("Rewarded Video Clicked")
    }

    private fun setStatusLabelText(label: String) {
        if (rewardedVideoAdStatusLabel != null) {
            rewardedVideoAdStatusLabel!!.text = label
        }
    }

    private fun showToast(message: String) {
        //if (isAdded()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        //}
    }

    override fun onRewardedVideoCompleted() {
        showToast("Rewarded Video View Complete")
    }

    override fun onLoggingImpression(ad: Ad?) {
        showToast("Rewarded Video Impression")
    }

    override fun onRewardedVideoClosed() {
        showToast("Rewarded Video Closed")
    }

    override fun onRewardServerFailed() {
        showToast("Reward Video Server Failed")
    }

    override fun onRewardServerSuccess() {
        showToast("Reward Video Server Succeeded")
    }
}