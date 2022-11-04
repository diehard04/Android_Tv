package com.example.androidtv.alignment

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.leanback.widget.*
import com.bumptech.glide.Glide
import com.example.androidtv.data.model.Movie
import com.example.androidtv.R
import kotlin.properties.Delegates

/**
 * Created by Dipak Kumar Mehta on 10/19/2021.
 */
class AlignCardPresenter: Presenter() {
    private var mDefaultCardImage: Drawable? = null
    private var sSelectedBackgroundColor: Int by Delegates.notNull()
    private var sDefaultBackgroundColor: Int by Delegates.notNull()

    override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
        //Log.d(TAG, "onCreateViewHolder")

        sDefaultBackgroundColor = ContextCompat.getColor(parent.context, R.color.default_background)
        sSelectedBackgroundColor = ContextCompat.getColor(parent.context, R.color.selected_background)
        mDefaultCardImage = ContextCompat.getDrawable(parent.context, R.drawable.movie)

        val cardView = object : ImageCardView(parent.context) {
            override fun setSelected(selected: Boolean) {
                updateCardBackgroundColor(this, selected)
                super.setSelected(selected)
            }
        }
        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true
        updateCardBackgroundColor(cardView, false)
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any) {
        val movie = item as Movie
        val cardView = viewHolder.view as ImageCardView

        //Log.d(TAG, "onBindViewHolder")
        if (movie.cardImageUrl != null) {
            cardView.titleText = movie.title
            cardView.contentText = movie.studio
            cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT)
            Glide.with(viewHolder.view.context)
                .load(movie.cardImageUrl)
                .centerCrop()
                .error(mDefaultCardImage)
                .into(cardView.mainImageView)
        }
    }

    override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder) {
        Log.d(TAG, "onUnbindViewHolder")
        val cardView = viewHolder.view as ImageCardView
        // Remove references to images so that the garbage collector can free up memory
        cardView.badgeImage = null
        cardView.mainImage = null
    }

    private fun updateCardBackgroundColor(view: ImageCardView, selected: Boolean) {
        val color = if (selected) sSelectedBackgroundColor else sDefaultBackgroundColor
        // Both background colors should be set because the view"s background is temporarily visible
        // during animations.
        view.setBackgroundColor(color)
        view.setInfoAreaBackgroundColor(color)
    }

    companion object {
        private val TAG = "AlignCardPresenter"
        private val CARD_WIDTH = 313
        private val CARD_HEIGHT = 176
    }

    class FocusedItemAtStartListRowPresenter(focusZoomFactor: Int) : ListRowPresenter(focusZoomFactor) {
        override fun createRowViewHolder(parent: ViewGroup): RowPresenter.ViewHolder {
            val viewHolder = super.createRowViewHolder(parent)

            with((viewHolder.view as ListRowView).gridView) {
                windowAlignment = BaseGridView.WINDOW_ALIGN_NO_EDGE
                windowAlignmentOffsetPercent = 20f
                windowAlignmentOffset = parent.resources.getDimensionPixelSize(R.dimen.lb_browse_padding_start)
                itemAlignmentOffsetPercent = 15f
            }

            return viewHolder
        }
    }
}