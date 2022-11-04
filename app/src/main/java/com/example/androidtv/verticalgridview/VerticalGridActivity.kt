package com.example.androidtv.verticalgridview

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.androidtv.R


class VerticalGridActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vertical_grid)
        window.setBackgroundDrawableResource(R.drawable.movie);
    }

    companion object {
        const val SHARED_ELEMENT_NAME = "Vertical Grid View"
        const val MOVIE = "Movie companion object"
    }
}