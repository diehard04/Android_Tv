package com.example.androidtv.search

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.leanback.widget.BaseCardView
import com.example.androidtv.R

/**
 * Created by Dipak Kumar Mehta on 10/25/2021.
 */
class CustomizedCardView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    BaseCardView(context, attrs, defStyleAttr) {
    var mImageView: ImageView? = null
    var mTitleView: TextView? = null
    protected fun buildCardView() {
        if (DEBUG) Log.d(TAG, "buildCardView() : +", Throwable("MyCardView"))
        // Make sure this view is clickable and focusable
        isClickable = true
        isFocusable = true
        isFocusableInTouchMode = true
        val inflater = LayoutInflater.from(getContext())
        inflater.inflate(R.layout.custom_card_view, this)
        mImageView = findViewById(R.id.banner_icon)
        mTitleView = findViewById(R.id.tv_title)
    }

    /**
     * Sets the image drawable.
     */
    fun setMainImage(drawable: Drawable?) {
        if (DEBUG) Log.d(TAG, "setMainImage() : +", Throwable("MyCardView"))
        mImageView!!.setImageDrawable(drawable)
    }

    fun getMainImageView(): ImageView? {
        return mImageView
    }

    fun setTitleText(text: CharSequence?) {
        if (mTitleView == null) {
            return
        }
        mTitleView!!.text = text
    }

    companion object {
        var DEBUG = false
        var TAG = "CustomizedCardView"
    }

    init {
        if (DEBUG) Log.d(TAG, "MyCardView() : +", Throwable("CardListRow"))
        buildCardView()
    }
}