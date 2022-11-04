package com.example.androidtv.menuitems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.androidtv.R

/**
 * Created by Dipak Kumar Mehta on 10/28/2021.
 */
class SettingsFragment : Fragment() {
    private var txtView:TextView ?= null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        txtView = view.findViewById(R.id.txtView)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        txtView?.requestFocus()
    }

    companion object {
        fun getInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}