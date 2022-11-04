package com.example.androidtv.search

import android.text.Editable

import android.text.TextWatcher

import android.widget.ArrayAdapter

import android.widget.EditText

import android.os.Bundle

import android.app.Activity
import android.view.View
import android.widget.ListView
import com.example.androidtv.R


/**
 * Created by Dipak Kumar Mehta on 10/20/2021.
 */
class SearchListActivity : Activity() {
    // List view
    private var lv: ListView? = null

    // Listview Adapter
    var adapter: ArrayAdapter<String>? = null

    // Search EditText
    var inputSearch: EditText? = null

    // ArrayList for Listview
    var productList: ArrayList<HashMap<String, String>>? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_list)

        // Listview Data
        val products = arrayOf(
            "Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
            "iPhone 4S", "Samsung Galaxy Note 800",
            "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"
        )
        lv = findViewById<View>(R.id.list_view) as ListView
        inputSearch = findViewById<View>(R.id.inputSearch) as EditText

        // Adding items to listview
        adapter = ArrayAdapter(this, R.layout.list_items, R.id.product_name, products)
        lv!!.setAdapter(adapter)
        /**
         * Enabling Search Filter
         */
        inputSearch!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                // When user changed the Text
                adapter!!.filter.filter(cs)
            }

            override fun beforeTextChanged(
                arg0: CharSequence, arg1: Int, arg2: Int,
                arg3: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(arg0: Editable) {
                // TODO Auto-generated method stub
            }
        })
    }
}