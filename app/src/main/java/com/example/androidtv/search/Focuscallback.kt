package com.example.androidtv.search

/**
 * Created by Dipak Kumar Mehta on 10/25/2021.
 */
interface Focuscallback {

    fun movefocus(clsname: String?)
    fun onFocusInstalled()
    fun Focustodownrow()
    fun Focusleft(classname: String?)
    fun foucusright(classname: String?)
}