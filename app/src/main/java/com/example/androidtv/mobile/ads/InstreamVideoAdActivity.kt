package com.example.androidtv.mobile.ads

import android.os.Bundle
import android.widget.LinearLayout
import android.app.Activity
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.Nullable
import androidx.fragment.app.FragmentActivity
import com.example.androidtv.R
import com.example.androidtv.exoplayer.TvPlayerActivity
import com.facebook.ads.*
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

/**
 * Created by Dipak Kumar Mehta on 12/23/2021.
 */
class InstreamVideoAdActivity : FragmentActivity(), Player.EventListener {

    private val TAG = InstreamVideoAdActivity::class.java.simpleName
    private var adContainer: LinearLayout? = null
    //private var adView: InstreamVideoAdView? = null

    // exoplayer code
    companion object {
        val TAG: String = TvPlayerActivity::class.java.simpleName
        const val videoUrl: String = "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8"
    }
    private lateinit var exoRev: ImageView;
    private lateinit var exoPlay: ImageView;
    private lateinit var exoPause: ImageView;
    private lateinit var exo_ffwd: ImageView;
    private lateinit var player: ExoPlayer
    private lateinit var exoplayerView: PlayerView
    private lateinit var simpleExoplayer: SimpleExoPlayer
    private var playbackPosition: Long = 0
    private val mp4Url = "https://html5demos.com/assets/dizzy.mp4"
    private val dashUrl = "https://storage.googleapis.com/wvmedia/clear/vp9/tears/tears_uhd.mpd"
    private val urlList = listOf(mp4Url to "default", dashUrl to "dash")
    private lateinit var progressBar: ProgressBar

    private val dataSourceFactory: DataSource.Factory by lazy {
        DefaultDataSourceFactory(this, "exoplayer-sample")
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_video_ads)
        AdSettings.addTestDevice("1e0246cb-bcfb-45e3-a20e-39aa502a1698")
        Log.d(TAG, " onCreate")
        loadInstreamAd()
    }

    private fun pxToDP(px: Int): Int {
        return (px / this.resources.displayMetrics.density).toInt()
    }

    private fun loadInstreamAd() {
        // Instantiate an InstreamVideoAdView object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        adContainer = findViewById(R.id.banner_container)
//        adView = InstreamVideoAdView(
//            this,
//            "YOUR_PLACEMENT_ID",
//            AdSize(
//                pxToDP(adContainer!!.measuredWidth),
//                pxToDP(adContainer!!.measuredHeight)
//            )
//        )
//
//        // set ad listener to handle events
//        adView!!.setAdListener(object : InstreamVideoAdListener {
//            override fun onError(ad: Ad?, adError: AdError) {
//                // Instream video ad failed to load
//                Log.e(TAG, "Instream video ad failed to load: " + adError.errorMessage)
//            }
//
//            override fun onAdLoaded(ad: Ad?) {
//                // Instream video ad is loaded and ready to be displayed
//                Log.d(TAG, "Instream video ad is loaded and ready to be displayed!")
//                // Race condition, load() called again before last ad was displayed
//                if (adView == null || !adView!!.isAdLoaded()) {
//                    return
//                }
//
//                // Inflate Ad into container and show it
//                adContainer?.removeAllViews()
//                adContainer?.addView(adView)
//                adView!!.show()
//            }
//
//            override fun onAdVideoComplete(ad: Ad?) {
//                // Instream Video View Complete - the video has been played to the end.
//                // You can use this event to continue your video playing
//                Log.d(TAG, "Instream video completed!")
//                exoplayerView.visibility = View.VISIBLE
//                adContainer!!.visibility = View.GONE
//                initializePlayer()
//            }
//
//            override fun onAdClicked(ad: Ad?) {
//                Log.d(TAG, "Instream video ad clicked!")
//            }
//
//            override fun onLoggingImpression(ad: Ad?) {
//                // Instream Video ad impression - the event will fire when the
//                // video starts playing
//                Log.d(TAG, "Instream video ad impression logged!")
//            }
//        })
//        adView!!.loadAd()
    }

    override fun onDestroy() {
//        if (adView != null) {
//            adView!!.destroy()
//        }
//        super.onDestroy()
    }

    private fun initializePlayer() {
        simpleExoplayer = SimpleExoPlayer.Builder(this).build()
        exoplayerView = findViewById(R.id.exo_playerView)
        exoRev= findViewById(R.id.exoRev)
        exoPlay = findViewById(R.id.exoPlay)
        exoPause = findViewById(R.id.exoPause)
        exo_ffwd = findViewById(R.id.exo_ffwd)

        val randomUrl = urlList.random()
        progressBar = findViewById(R.id.progress_bar)
        preparePlayer(randomUrl.first, randomUrl.second)
        exoplayerView.player = simpleExoplayer
        simpleExoplayer.seekTo(playbackPosition)
        simpleExoplayer.playWhenReady = true
        simpleExoplayer.addListener(this)

        exoPlay.setOnClickListener {
            simpleExoplayer.playWhenReady = true
        }

        exoPause.setOnClickListener {
            simpleExoplayer.playWhenReady = false
        }

        exo_ffwd.setOnClickListener {
            simpleExoplayer.seekTo(10000)
        }

        exoRev.setOnClickListener {
            simpleExoplayer.seekTo(-10000)
        }
    }

    private fun buildMediaSource(uri: Uri, type: String): MediaSource {
        return if (type == "dash") {
            DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
        }

        else {
            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
        }
    }

    private fun preparePlayer(videoUrl: String, type: String) {
        val uri = Uri.parse(videoUrl)
        val mediaSource = buildMediaSource(uri, type)
        simpleExoplayer.prepare(mediaSource)
    }

    private fun releasePlayer() {
        playbackPosition = simpleExoplayer.currentPosition
        simpleExoplayer.release()
    }

    fun onPlayerError(error: ExoPlaybackException) {

    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_BUFFERING) {
            progressBar.visibility = View.VISIBLE
        }

        else if (playbackState == Player.STATE_READY || playbackState == Player.STATE_ENDED) {
            progressBar.visibility = View.INVISIBLE
        }
    }
}