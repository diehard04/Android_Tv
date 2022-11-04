package com.example.androidtv.alignment

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.androidtv.R

/**
 * Created by Dipak Kumar Mehta on 10/19/2021.
 */
class AlignmentActivity: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alignment)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.alignment_fragment, AlignmentFragment())
                .commitNow()
        }
    }
}