package com.example.androidtv.exoplayer

import androidx.leanback.widget.ArrayObjectAdapter

import android.widget.Toast

import android.app.Activity
import android.os.Build
import android.os.Handler
import androidx.annotation.RequiresApi

import androidx.leanback.media.PlaybackTransportControlGlue

import androidx.leanback.media.PlayerAdapter
import androidx.leanback.widget.Action
import androidx.leanback.widget.PlaybackControlsRow.*


/**
 * Created by Dipak Kumar Mehta on 11/8/2021.
 */
class VideoMediaPlayerGlue<T : PlayerAdapter?>(context: Activity?, impl: T) :
    PlaybackTransportControlGlue<T?>(context, impl) {
    private val mRepeatAction: RepeatAction
    private val mThumbsUpAction: ThumbsUpAction
    private val mThumbsDownAction: ThumbsDownAction
    private val mPipAction: PictureInPictureAction
    private val mClosedCaptioningAction: ClosedCaptioningAction
    override fun onCreateSecondaryActions(adapter: ArrayObjectAdapter) {
        adapter.add(mThumbsUpAction)
        adapter.add(mThumbsDownAction)
        if (Build.VERSION.SDK_INT > 23) {
            adapter.add(mPipAction)
        }
    }

    override fun onCreatePrimaryActions(adapter: ArrayObjectAdapter) {
        super.onCreatePrimaryActions(adapter)
        adapter.add(mRepeatAction)
        adapter.add(mClosedCaptioningAction)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActionClicked(action: Action) {
        if (shouldDispatchAction(action)) {
            dispatchAction(action)
            return
        }
        super.onActionClicked(action)
    }

    private fun shouldDispatchAction(action: Action): Boolean {
        return action === mRepeatAction || action === mThumbsUpAction || action === mThumbsDownAction || action === mPipAction || action === mClosedCaptioningAction
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun dispatchAction(action: Action) {
        if (action === mPipAction) {
            (context as Activity).enterPictureInPictureMode()
        } else {
            Toast.makeText(context, action.toString(), Toast.LENGTH_SHORT).show()
            val multiAction = action as MultiAction
            multiAction.nextIndex()
            notifyActionChanged(multiAction)
        }
    }

    private fun notifyActionChanged(action: MultiAction) {
        var index = -1
        if (getPrimaryActionsAdapter() != null) {
            index = getPrimaryActionsAdapter()!!.indexOf(action)
        }
        if (index >= 0) {
            getPrimaryActionsAdapter()!!.notifyArrayItemRangeChanged(index, 1)
        } else {
            if (getSecondaryActionsAdapter() != null) {
                index = getSecondaryActionsAdapter()!!.indexOf(action)
                if (index >= 0) {
                    getSecondaryActionsAdapter()!!.notifyArrayItemRangeChanged(index, 1)
                }
            }
        }
    }

    private fun getPrimaryActionsAdapter(): ArrayObjectAdapter? {
        return if (controlsRow == null) {
            null
        } else controlsRow.primaryActionsAdapter as ArrayObjectAdapter
    }

    private fun getSecondaryActionsAdapter(): ArrayObjectAdapter? {
        return if (controlsRow == null) {
            null
        } else controlsRow.secondaryActionsAdapter as ArrayObjectAdapter
    }

    var mHandler: Handler = Handler()
    override fun onPlayCompleted() {
        super.onPlayCompleted()
        mHandler.post(Runnable {
            if (mRepeatAction.index != RepeatAction.NONE) {
                play()
            }
        })
    }

    fun setMode(mode: Int) {
        mRepeatAction.index = mode
        if (getPrimaryActionsAdapter() == null) {
            return
        }
        notifyActionChanged(mRepeatAction)
    }

    init {
        mClosedCaptioningAction = ClosedCaptioningAction(context)
        mThumbsUpAction = ThumbsUpAction(context)
        mThumbsUpAction.index = ThumbsUpAction.OUTLINE
        mThumbsDownAction = ThumbsDownAction(context)
        mThumbsDownAction.index = ThumbsDownAction.OUTLINE
        mRepeatAction = RepeatAction(context)
        mPipAction = PictureInPictureAction(context)
    }
}