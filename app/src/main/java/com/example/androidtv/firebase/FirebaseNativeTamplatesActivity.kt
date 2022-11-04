package com.example.androidtv.firebase

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtv.R
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.nativead.NativeAd
import java.util.*


class FirebaseNativeTamplatesActivity : AppCompatActivity() {
    //creating Object of AdLoader
    private var adLoader: AdLoader? = null

    // simple boolean to check the status of ad
    private var adLoaded = false

    //creating Object of Buttons
    private var loadAdBtn: Button? = null
    private var showAdBtn: Button? = null
    private var hideAdBtn: Button? = null

    //creating Template View object
    var template: TemplateView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_native_tamplates)

        MobileAds.initialize(this)
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("8A7007FFA3D6F982E85926A4359515C7"))
                .build()
        )

        // Initializing the Google Admob SDK
        // Initializing the Google Admob SDK
        MobileAds.initialize(this, object : OnInitializationCompleteListener {
            override fun onInitializationComplete(initializationStatus: InitializationStatus) {
                //Showing a simple Toast Message to the user when The Google AdMob Sdk Initialization is Completed
                Toast.makeText(this@FirebaseNativeTamplatesActivity, "AdMob Sdk Initialize " + initializationStatus.toString(), Toast.LENGTH_LONG
                ).show()
            }
        })

        // Initializing the Button  objects to their respective views from activity_main.xml file

        // Initializing the Button  objects to their respective views from activity_main.xml file
        loadAdBtn = findViewById<View>(R.id.loadNativeBtn) as Button
        showAdBtn = findViewById<View>(R.id.showNativeBtn) as Button
        hideAdBtn = findViewById<View>(R.id.hideNativeBtn) as Button

        //Initializing the AdLoader   objects

        //Initializing the AdLoader   objects
        adLoader = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd(object : NativeAd.OnNativeAdLoadedListener {
                override fun onNativeAdLoaded(nativeAd: NativeAd) {
                    val styles =
                        NativeTemplateStyle.Builder().withMainBackgroundColor(ColorDrawable()).build()
                    val template = findViewById<TemplateView>(R.id.nativeTemplateView)
                    template.setStyles(styles)
                    template.setNativeAd(nativeAd)                }
            }).build()

        //OnClickListener listeners for loadAdBtn and showAdBtn buttons

        //OnClickListener listeners for loadAdBtn and showAdBtn buttons
        loadAdBtn?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //calling the loadNativeAd method to load  the Native Ad
                loadNativeAd()
            }
        })

        showAdBtn?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //calling the showNativeAd method to show the Native Ad
                showNativeAd()
            }
        })

        hideAdBtn?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //calling the showNativeAd method to hide the Native Ad
                hideNativeAd()
            }
        })
    }

    private fun loadNativeAd() {
        // Creating  an Ad Request
        val adRequest = AdRequest.Builder().build()

        // load Native Ad with the Request
        adLoader!!.loadAd(adRequest)

        // Showing a simple Toast message to user when Native an ad is Loading
        Toast.makeText(this@FirebaseNativeTamplatesActivity, "Native Ad is loading ", Toast.LENGTH_LONG).show()
    }

    private fun showNativeAd() {
        if (adLoaded) {
            template!!.visibility = View.VISIBLE
            // Showing a simple Toast message to user when an Native ad is shown to the user
            Toast.makeText(
                this@FirebaseNativeTamplatesActivity,
                "Native Ad  is loaded and Now showing ad  ",
                Toast.LENGTH_LONG
            ).show()
        } else {
            //Load the Native ad if it is not loaded
            loadNativeAd()

            // Showing a simple Toast message to user when Native ad is not loaded
            Toast.makeText(this@FirebaseNativeTamplatesActivity, "Native Ad is not Loaded ", Toast.LENGTH_LONG).show()
        }
    }

    private fun hideNativeAd() {
        if (adLoaded) {
            // hiding the Native Ad when it is loaded
            template!!.visibility = View.GONE

            // Showing a simple Toast message to user when Native ad
            Toast.makeText(this@FirebaseNativeTamplatesActivity, "Native Ad is hidden ", Toast.LENGTH_LONG).show()
        }
    }
}