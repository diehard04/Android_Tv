package com.example.androidtv.pagination

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.androidtv.R

/**
 * Created by Dipak Kumar Mehta on 10/18/2021.
 */
class PagingSampleActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging_sample)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.paging_sample_fragment, PagingSampleFragment())
                .commitNow()
        }
    }


}