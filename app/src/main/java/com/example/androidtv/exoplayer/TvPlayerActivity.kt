package com.example.androidtv.exoplayer

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.Nullable
import androidx.fragment.app.FragmentActivity
import com.example.androidtv.R
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.ExoPlayer

import com.google.android.exoplayer2.source.hls.HlsMediaSource

import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

/**
 * Created by Dipak Kumar Mehta on 11/8/2021.
 */
class TvPlayerActivity : FragmentActivity(), Player.EventListener{

    companion object {
        val TAG: String = TvPlayerActivity::class.java.simpleName
        const val videoUrl: String = "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8"
    }
    private lateinit var exoRev:ImageView;
    private lateinit var exoPlay:ImageView;
    private lateinit var exoPause:ImageView;
    private lateinit var exo_ffwd:ImageView;
    private lateinit var player:ExoPlayer
    private lateinit var exoplayerView:PlayerView
    private lateinit var simpleExoplayer: SimpleExoPlayer
    private var playbackPosition: Long = 0
    private val mp4Url = "https://html5demos.com/assets/dizzy.mp4"
    private val dashUrl = "https://storage.googleapis.com/wvmedia/clear/vp9/tears/tears_uhd.mpd"
    private val urlList = listOf(mp4Url to "default", dashUrl to "dash")
    private lateinit var progressBar:ProgressBar

    private val dataSourceFactory: DataSource.Factory by lazy {
        DefaultDataSourceFactory(this, "exoplayer-sample")
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exoplayer_view)
    }

    override fun onStart() {
        super.onStart()
        //initHslPlayer()
        initializePlayer()
    }

    private fun initHslPlayer() {
        exoplayerView = findViewById(R.id.exo_playerView)
        progressBar = findViewById(R.id.progress_bar)
        exoRev= findViewById(R.id.exoRev)
        exoPlay = findViewById(R.id.exoPlay)
        exoPause = findViewById(R.id.exoPause)
        exo_ffwd = findViewById(R.id.exo_ffwd)

        // Create a data source factory.
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        // Create a HLS media source pointing to a playlist uri.
        val hlsMediaSource: HlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoUrl))
        // Create a player instance.
        val player = ExoPlayer.Builder(applicationContext).build()
        // Set the media source to be played.
        player.setMediaSource(hlsMediaSource)
        // Prepare the player.
        player.prepare()
        exoplayerView.player = player
        player.addListener(this)

        exoPlay.setOnClickListener {
            player.playWhenReady = true
        }

        exoPause.setOnClickListener {
            player.playWhenReady = false
        }

        exo_ffwd.setOnClickListener {

        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun releaseHslPlayer() {
        player.release()
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