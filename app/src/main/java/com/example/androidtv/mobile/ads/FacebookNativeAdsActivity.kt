package com.example.androidtv.mobile.ads

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtv.R
import com.facebook.ads.*

/**
 * Created by Dipak Kumar Mehta on 12/21/2021.
 */
class FacebookNativeAdsActivity:AppCompatActivity() {

    private val TAG = FacebookNativeAdsActivity::class.java.name
    private var nativeAd: NativeAd? = null
    private var nativeAdLayout: NativeAdLayout? = null
    private var adView: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_native_ads)
        loadNativeAd()
    }

    private fun loadNativeAd() {
        // Instantiate a NativeAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        nativeAd = NativeAd(this, ActivityConfig.FB_NATIVE)
        val nativeAdListener: NativeAdListener = object : NativeAdListener {
            override fun onMediaDownloaded(ad: Ad?) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.")
            }

            override fun onError(ad: Ad?, adError: AdError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.errorMessage)
            }

            override fun onAdLoaded(ad: Ad?) {
                // Native ad is loaded and ready to be displayed
                Log.d(TAG, "Native ad is loaded and ready to be displayed!")
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd!!);
            }

            override fun onAdClicked(ad: Ad?) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!")
            }

            override fun onLoggingImpression(ad: Ad?) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!")
            }
        }

        // Request an ad
        nativeAd!!.loadAd(
            nativeAd!!.buildLoadAdConfig()
                .withAdListener(nativeAdListener)
                .build()
        )

    }

    private fun inflateAd(nativeAd: NativeAd) {
        nativeAd.unregisterView()

        // Add the Ad view into the ad container.
        nativeAdLayout = findViewById(R.id.native_ad_container)
        val inflater = LayoutInflater.from(this@FacebookNativeAdsActivity)
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adView = inflater.inflate(R.layout.native_ad_layout, nativeAdLayout, false) as LinearLayout
        nativeAdLayout?.addView(adView)

        // Add the AdOptionsView
        val adChoicesContainer = findViewById<LinearLayout>(R.id.ad_choices_container)
        val adOptionsView = AdOptionsView(this@FacebookNativeAdsActivity, nativeAd, nativeAdLayout)
        adChoicesContainer.removeAllViews()
        adChoicesContainer.addView(adOptionsView, 0)

        // Create native UI using the ad metadata.
        val nativeAdIcon: MediaView = adView!!.findViewById(R.id.native_ad_icon)
        val nativeAdTitle: TextView = adView!!.findViewById(R.id.native_ad_title)
        val nativeAdMedia: MediaView = adView!!.findViewById(R.id.native_ad_media)
        val nativeAdSocialContext: TextView = adView!!.findViewById(R.id.native_ad_social_context)
        val nativeAdBody: TextView = adView!!.findViewById(R.id.native_ad_body)
        val sponsoredLabel: TextView = adView!!.findViewById(R.id.native_ad_sponsored_label)
        val nativeAdCallToAction: Button = adView!!.findViewById(R.id.native_ad_call_to_action)

        // Set the Text.
        nativeAdTitle.text = nativeAd.advertiserName
        nativeAdBody.text = nativeAd.adBodyText
        nativeAdSocialContext.text = nativeAd.adSocialContext
        nativeAdCallToAction.visibility = if (nativeAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE
        nativeAdCallToAction.text = nativeAd.adCallToAction
        sponsoredLabel.text = nativeAd.sponsoredTranslation

        // Create a list of clickable views
        val clickableViews: MutableList<View> = ArrayList()
        clickableViews.add(nativeAdTitle)
        clickableViews.add(nativeAdCallToAction)

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(adView, nativeAdMedia, nativeAdIcon, clickableViews)
    }
}