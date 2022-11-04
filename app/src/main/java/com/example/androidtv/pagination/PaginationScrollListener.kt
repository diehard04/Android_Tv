package com.example.androidtv.pagination

import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.LinearLayoutManager




/**
 * Created by Dipak Kumar Mehta on 10/19/2021.
 */
abstract class PaginationScrollListener(layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    var layoutManager: LinearLayoutManager

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView!!, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= getTotalPageCount()) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract fun getTotalPageCount(): Int
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean

    /**
     * Supporting only LinearLayoutManager for now.
     *
     * @param layoutManager
     */
    init {
        this.layoutManager = layoutManager
    }
}