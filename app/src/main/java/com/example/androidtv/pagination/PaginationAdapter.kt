package com.example.androidtv.pagination
import android.content.Context
import android.widget.RelativeLayout

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import android.widget.Toast

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import com.example.androidtv.R

/**
 * Created by Dipak Kumar Mehta on 10/18/2021.
 */
class MyListAdapter(listdata: ArrayList<MyListData>, context:Context) : RecyclerView.Adapter<MyListAdapter.ViewHolder?>() {
    private var mListData: ArrayList<MyListData>
    private lateinit var context:Context

    private val ITEM = 0
    private val LOADING = 1
    private var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myListData = mListData[position]
        holder.textView.setText(mListData[position].name)
        holder.relativeLayout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                Toast.makeText(
                    view.getContext(),
                    "click on item: " + myListData.name,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun getItemCount(): Int {
        return mListData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView
        var relativeLayout: RelativeLayout

        init {
            textView = itemView.findViewById(R.id.textView)
            relativeLayout = itemView.findViewById(R.id.relativeLayout)
        }
    }

    public fun addItems(listdata: ArrayList<MyListData>) {
        mListData.addAll(listdata)
        notifyDataSetChanged()
    }

    // RecyclerView recyclerView;
    init {
        this.mListData = listdata
        this.context = context
    }
}