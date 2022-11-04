package com.example.androidtv.exoplayer

import android.content.pm.PackageManager

import androidx.core.os.BuildCompat

import android.content.Intent

import android.os.Bundle

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentTransaction
import com.example.androidtv.R


/**
 * Created by Dipak Kumar Mehta on 11/8/2021.
 */
class VideoExampleWithExoPlayerActivity : Activity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_example)
        val ft: android.app.FragmentTransaction? = fragmentManager.beginTransaction()
//        ft?.add(R.id.videoFragment, VideoConsumptionExampleWithExoPlayerFragment(),
//            VideoConsumptionExampleWithExoPlayerFragment.TAG
//        )
//        ft?.commit()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // This part is necessary to ensure that getIntent returns the latest intent when
        // VideoExampleActivity is started. By default, getIntent() returns the initial intent
        // that was set from another activity that started VideoExampleActivity. However, we need
        // to update this intent when for example, user clicks on another video when the currently
        // playing video is in PIP mode, and a new video needs to be started.
        setIntent(intent)
    }

    companion object {
        const val TAG = "VideoExampleWithExoPlayerActivity"
        fun supportsPictureInPicture(context: Context): Boolean {
            return BuildCompat.isAtLeastN() &&
                    context.getPackageManager().hasSystemFeature(
                        PackageManager.FEATURE_PICTURE_IN_PICTURE
                    )
        }
    }
}