package com.example.androidtv.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.SearchSupportFragment
import com.example.androidtv.R
import com.example.androidtv.ui.MainActivity

/**
 * Created by Dipak Kumar Mehta on 10/20/2021.
 */

class SearchActivity : FragmentActivity() {
    var DEBUG = false
    var TAG = "SearchActivity"
    var mFragment: SearchFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (DEBUG) {
            Log.d(TAG, "onCreate() : +", Throwable("SearchActivity"))
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_support)
        mFragment = fragmentManager.findFragmentById(R.id.search_fragment) as SearchFragment
    }

//    override fun onSearchRequested(): Boolean {
//        if (DEBUG) {
//            Log.d(TAG, "onSearchRequested() : +", Throwable("SearchActivity"))
//        }
//        startActivity(Intent(this, SearchActivity::class.java))
//        return true
//    }
//
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_HOME) {
//            val home_intent = Intent(this, MainActivity::class.java)
//            startActivity(home_intent)
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }
}

