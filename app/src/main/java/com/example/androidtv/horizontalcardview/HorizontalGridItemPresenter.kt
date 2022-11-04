package com.example.androidtv.horizontalcardview

import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.leanback.widget.Presenter
import com.example.androidtv.R

class HorizontalGridItemPresenter(horizontalCardViewFragment: HorizontalCardViewFragment): Presenter() {

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view = TextView(parent?.context)
        view.layoutParams = ViewGroup.LayoutParams(HorizontalCardViewFragment.GRID_ITEM_WIDTH, HorizontalCardViewFragment.GRID_ITEM_HEIGHT)
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.setBackgroundColor(ContextCompat.getColor(parent?.context!!, R.color.default_background))
        view.setTextColor(Color.WHITE)
        view.gravity = Gravity.CENTER
        return Presenter.ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        (viewHolder?.view as TextView).text = item as String
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        TODO("Not yet implemented")
    }
}