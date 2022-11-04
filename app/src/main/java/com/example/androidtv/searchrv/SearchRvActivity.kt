package com.example.androidtv.searchrv

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtv.R

/**
 * Created by Dipak Kumar Mehta on 10/29/2021.
 */
class SearchRvActivity: AppCompatActivity() {

    private var adapter: ExampleAdapter? = null
    private var exampleList: ArrayList<ExampleItem>?= ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_rv)
        fillExampleList()
        setUpRecyclerView()
    }

    private fun fillExampleList() {
        exampleList?.add(ExampleItem(R.drawable.ic_app_default, "One", "Ten"))
        exampleList?.add(ExampleItem(R.drawable.ic_baseline_home_24, "Two", "Eleven"))
        exampleList?.add(ExampleItem(R.drawable.lb_ic_sad_cloud, "Three", "Twelve"))
        exampleList?.add(ExampleItem(R.drawable.lb_ic_thumb_up, "Four", "Thirteen"))
        exampleList?.add(ExampleItem(R.drawable.lb_ic_stop, "Five", "Fourteen"))
        exampleList?.add(ExampleItem(R.drawable.lb_ic_replay, "Six", "Fifteen"))
        exampleList?.add(ExampleItem(R.drawable.lb_ic_more, "Seven", "Sixteen"))
        exampleList?.add(ExampleItem(R.drawable.lb_ic_loop_one, "Eight", "Seventeen"))
        exampleList?.add(ExampleItem(R.drawable.ic_title, "Nine", "Eighteen"))
    }

    private fun setUpRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        adapter = exampleList?.let { ExampleAdapter(it) }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

}