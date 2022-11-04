package com.example.androidtv.search

import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import androidx.leanback.widget.Presenter

/**
 * Created by Dipak Kumar Mehta on 10/25/2021.
 */
class AppCardPresenter : Presenter() {
    var mContext: Context? = null
    private var appBean: AppModel? = null
    var count = 0
    var focuscallback: Focuscallback? = null

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        if (DEBUG) Log.d(TAG, "onCreateViewHolder() : +", Throwable("AppCardPresenter"))
        mContext = parent.context
        return if (count == 0) {
            count = 1
            cardView = CustomizedCardView(parent.context, null, 0)
            cardView!!.isSelected = false
            cardView!!.isFocusable = true
            cardView!!.isFocusableInTouchMode = true
            cardView!!.cardType = 0
            cardView!!.setOnKeyListener(View.OnKeyListener { view, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_DPAD_UP -> {
                            focuscallback?.movefocus("")
                            return@OnKeyListener true
                        }
                        KeyEvent.KEYCODE_DPAD_DOWN -> return@OnKeyListener false
                        KeyEvent.KEYCODE_DPAD_CENTER -> return@OnKeyListener false
                        KeyEvent.KEYCODE_DPAD_LEFT -> return@OnKeyListener false
                        KeyEvent.KEYCODE_DPAD_RIGHT -> return@OnKeyListener false
                    }
                }
                false
            })
            ViewHolder(cardView)
        } else {
            val cardView = CustomizedCardView(parent.context, null, 0)
            cardView.isSelected = false
            cardView.isFocusable = true
            cardView.isFocusableInTouchMode = true
            cardView.cardType = 0
            cardView.setOnKeyListener(View.OnKeyListener { view, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_DPAD_UP -> {
                            focuscallback?.movefocus("")
                            return@OnKeyListener false
                        }
                        KeyEvent.KEYCODE_DPAD_DOWN -> return@OnKeyListener false
                        KeyEvent.KEYCODE_DPAD_CENTER -> return@OnKeyListener false
                        KeyEvent.KEYCODE_DPAD_LEFT -> return@OnKeyListener false
                        KeyEvent.KEYCODE_DPAD_RIGHT -> return@OnKeyListener false
                    }
                }
                false
            })
            ViewHolder(cardView)
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any?) {
        if (DEBUG) Log.d(TAG, "onBindViewHolder() : +", Throwable("AppCardPresenter"))
        val cardView: CustomizedCardView = viewHolder.view as CustomizedCardView
        appBean = item as AppModel?
        if (appBean?.getBanner() == null) {
            cardView.setMainImage(appBean?.getIcon())
            cardView.setTitleText(appBean?.getName())
        } else {
            cardView.setMainImage(appBean?.getBanner())
            cardView.setTitleText(appBean?.getName())
        }
        //cardView.setBackgroundColor(appBean?.getLaunchColor())
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        if (DEBUG) Log.d(TAG, "onUnbindViewHolder() : +", Throwable("AppCardPresenter"))
        val cardView: CustomizedCardView = viewHolder.view as CustomizedCardView
        cardView.setMainImage(null)
        cardView.setTitleText(null)
        //        cardView.setBackgroundColor(Integer.parseInt(null));
    }

    fun setfocustofisrtitem() {
        //Toast.makeText(mContext,"count",Toast.LENGTH_SHORT).show();
        if (cardView != null) {
            Log.d("AppCardPresenter", "isNotNull" + cardView)
            cardView?.requestFocus()
        } else Log.d("AppCardPresenter", "isNull")
    }

    fun setlistner(obj: Any?) {
        focuscallback = obj as Focuscallback?
    }

    companion object {
        var TAG = "AppCardPresenter"
        var DEBUG = false
        var cardView: CustomizedCardView? = null
    }
}