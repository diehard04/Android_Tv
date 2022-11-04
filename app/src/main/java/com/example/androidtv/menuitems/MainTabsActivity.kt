package com.example.androidtv.menuitems

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.leanback.widget.BrowseFrameLayout
import coil.load
import coil.transform.BlurTransformation
import com.example.androidtv.R
import kotlin.math.roundToInt

/**
 * Created by Dipak Kumar Mehta on 10/28/2021.
 */

fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).roundToInt()
}

class MainTabsActivity : FragmentActivity(), LeftMenuView.MenuItemClickListener {

    private var leftMenusShown: Boolean = false
    private val maxExpandWidth = dpToPx(188)
    private val minExpandWidth = dpToPx(84)
    private var rlLeftMenu:LeftMenuView ?= null
    private var ivBg:ImageView ?= null
    private var browseFrameLayout:BrowseFrameLayout ?= null
    private  var transView:View ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintabs)
        rlLeftMenu = findViewById(R.id.rlLeftMenu)
        ivBg = findViewById(R.id.ivBg)
        browseFrameLayout = findViewById(R.id.browseFrameLayout)
        transView = findViewById(R.id.transView)

        rlLeftMenu?.setupDefaultMenu(LeftMenuView.HOME_MENU)
        replaceSelectedFragment(HomeFragment.getInstance(), LeftMenuView.TAG_HOME)

        setupFocusListener()

        ivBg?.load(R.drawable.splash_bg) {
            transformations(BlurTransformation(baseContext, 15f, 2f))
        }
    }

    companion object {
        fun getNewIntent(context: Context): Intent {
            return Intent(context, MainTabsActivity::class.java)
        }
    }

    override fun menuItemClick(menuId: Int) {
        if (rlLeftMenu?.getCurrentSelected() == menuId) {
            return
        }
        val fragment: Fragment?
        val tag: String
        when (menuId) {
            LeftMenuView.SEARCH_MENU -> {
                rlLeftMenu?.setCurrentSelected(LeftMenuView.SEARCH_MENU)
                fragment = SearchFragment.getInstance()
                tag = LeftMenuView.TAG_SEARCH
            }
            LeftMenuView.SETTING_MENU -> {
                rlLeftMenu?.setCurrentSelected(LeftMenuView.SETTING_MENU)
                fragment = SettingsFragment.getInstance()
                tag = LeftMenuView.TAG_SETTINGS
            }
            else -> {
                rlLeftMenu?.setCurrentSelected(LeftMenuView.HOME_MENU)
                fragment = HomeFragment.getInstance()
                tag = LeftMenuView.TAG_HOME
            }
        }

        leftMenusShown = false
        rlLeftMenu?.let { hideLeftMenu(it) }
        Handler(Looper.getMainLooper()).postDelayed({
            replaceSelectedFragment(fragment, tag)
        }, 100)
        Handler(Looper.getMainLooper()).postDelayed({
            setupFocusListener()
        }, 300)
    }

    private fun replaceSelectedFragment(fragment: Fragment?, tag: String) {
        browseFrameLayout?.removeAllViewsInLayout()
        supportFragmentManager.beginTransaction()
            .replace(R.id.browseFrameLayout, fragment!!)
            .disallowAddToBackStack()
            .commit()

        browseFrameLayout?.requestFocus()
    }

    private fun setupFocusListener() {
        rlLeftMenu?.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                rlLeftMenu?.onFocusChangeListener = null
                leftMenusShown = true
                showLeftMenu(view)
            } else {
                leftMenusShown = false
                hideLeftMenu(view)
            }
        }
    }

    private fun showLeftMenu(view: View) {
        val width = rlLeftMenu?.measuredWidth
        val valueAnimator = ValueAnimator.ofInt(width!!, maxExpandWidth)
        rlLeftMenu?.setupMenuExpandedUI()
        transView?.visibility = View.VISIBLE
        LeftMenuView.animateView(view, valueAnimator)
    }

    private fun hideLeftMenu(view: View) {
        val width = rlLeftMenu?.measuredWidth
        val valueAnimator = ValueAnimator.ofInt(width!!, minExpandWidth)
        rlLeftMenu?.setupMenuClosedUI()

        transView?.visibility = View.GONE
        LeftMenuView.animateView(view, valueAnimator)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return if (leftMenusShown) {
                resetLeftMenuUI()
            } else {
                onBackPressed()
                return false
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT && leftMenusShown) {
            return resetLeftMenuUI()
        }
        return false
    }

    private fun resetLeftMenuUI(): Boolean {
        leftMenusShown = false
        browseFrameLayout?.requestFocus()
        hideLeftMenu(rlLeftMenu!!)
        setupFocusListener()
        return true
    }

    override fun onDestroy() {
        rlLeftMenu?.onFocusChangeListener = null
        super.onDestroy()
    }
}
