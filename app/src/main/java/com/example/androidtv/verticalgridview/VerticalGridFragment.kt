package com.example.androidtv.verticalgridview

import android.os.Bundle
import androidx.leanback.app.VerticalGridSupportFragment
import androidx.leanback.widget.VerticalGridPresenter
import androidx.leanback.widget.ArrayObjectAdapter
import kotlin.collections.HashMap

import androidx.leanback.widget.Row
import androidx.leanback.widget.RowPresenter

import androidx.leanback.widget.Presenter

import androidx.leanback.widget.OnItemViewSelectedListener

import androidx.leanback.widget.OnItemViewClickedListener
import android.widget.Toast
import com.example.androidtv.*
import com.example.androidtv.data.model.Movie


class VerticalGridFragment : VerticalGridSupportFragment() {

    private val TAG = "VerticalGridFragment"
    private val NUM_COLUMNS = 5
    private var mAdapter: ArrayObjectAdapter? = null
    private var mMovieList: HashMap<String, List<Movie>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.vertical_grid_titles);
        setupFragment();
    }

    private fun setupFragment() {
        val verticalGridPresenter = VerticalGridPresenter()
        verticalGridPresenter.numberOfColumns = 2
        gridPresenter = verticalGridPresenter

        mAdapter = ArrayObjectAdapter(CardPresenter())
        val seed = System.nanoTime()

        val movies: HashMap<String, List<Movie>>? = getMovieList()

        for (entry in movies!!.entries) {
            val list: List<Movie> = entry.value
            //Collections.shuffle(list, Random(seed))
            for (j in list.indices) {
                mAdapter!!.add(list[j])
            }
        }

        adapter = mAdapter

        setOnItemViewSelectedListener(object : OnItemViewSelectedListener {
            override fun onItemSelected(itemViewHolder: Presenter.ViewHolder?, item: Any?,
                                        rowViewHolder: RowPresenter.ViewHolder?, row: Row?) {
                Toast.makeText(context, "item name " + item, Toast.LENGTH_SHORT).show()
            }
        })

        setOnItemViewClickedListener(object : OnItemViewClickedListener {
            override fun onItemClicked(itemViewHolder: Presenter.ViewHolder?, item: Any?,
                rowViewHolder: RowPresenter.ViewHolder?, row: Row?) {
                Toast.makeText(context, "item name " + item, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getMovieList(): HashMap<String, List<Movie>>? {
        mMovieList = HashMap<String, List<Movie>>()
        val movie1 = Movie(1, "Avenger1", "first scene1", " ", "", "", "studio 1")
        val movie2 = Movie(1, "Avenger2", "first scene2", " ", "", "", "studio 2")
        val movie3 = Movie(1, "Avenger3", "first scene3", " ", "", "", "studio 3")
        val movie4 = Movie(1, "Avenger4", "first scene4", " ", "", "", "studio 4")
        val list = arrayListOf<Movie>(movie1, movie2, movie3, movie4)
        mMovieList!!.put("category_name", list)
        return mMovieList
    }
}