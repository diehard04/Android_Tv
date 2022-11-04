package com.example.androidtv.searchrv

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView

import android.widget.TextView
import androidx.annotation.NonNull

import androidx.recyclerview.widget.RecyclerView
import com.example.androidtv.R

/**
 * Created by Dipak Kumar Mehta on 10/29/2021.
 */
class ExampleAdapter (exampleList: MutableList<ExampleItem>) : RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder?>(), Filterable {
    private var exampleList: List<ExampleItem>

    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var textView1: TextView
        var textView2: TextView

        init {
            imageView = itemView.findViewById(R.id.image_view)
            textView1 = itemView.findViewById(R.id.text_view1)
            textView2 = itemView.findViewById(R.id.textView2)
        }
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.example_item, parent, false)
        return ExampleViewHolder(v)
    }

    override fun onBindViewHolder(@NonNull holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.imageView.setImageResource(currentItem.getImageResource())
        holder.textView1.text = currentItem.getText1()
        holder.textView2.text = currentItem.getText2()
    }

    override fun getItemCount(): Int {
        return exampleList.size
    }

    override fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var filteredList: MutableList<ExampleItem> = ArrayList()

            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(exampleList)
            } else {
                val filterPattern: String = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in exampleList) {
                    if (item.getText2().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            exampleList.clear()
            exampleList.addAll(results.values as Collection<ExampleItem>)
            notifyDataSetChanged()
        }
    }

    init {
        this.exampleList = exampleList
    }
}