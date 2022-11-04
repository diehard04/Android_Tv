package com.example.androidtv.search

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.leanback.app.SearchFragment
import androidx.leanback.widget.*
import com.example.androidtv.R
import java.util.*

/**
 * Created by Dipak Kumar Mehta on 10/20/2021.
 */

class SearchFragment : SearchFragment(), SearchFragment.SearchResultProvider {
    var mAppDataList: ArrayList<AppModel> = ArrayList<AppModel>()
    var mSearchedList: ArrayList<Any?> = ArrayList<Any?>()

    private var mRowsAdapter: ArrayObjectAdapter? = null
    private val mHandler = Handler()
    private var mDelayedLoad: SearchRunnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (DEBUG) Log.d(TAG, "onCreate() : +", Throwable("SearchFragment"))
        super.onCreate(savedInstanceState)
        mRowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        setSearchResultProvider(this)
        setOnItemViewClickedListener(getDefaultItemClickedListener())
        mDelayedLoad = SearchRunnable()
    }

    override fun getResultsAdapter(): ObjectAdapter? {
        if (DEBUG) Log.d(TAG, "getResultsAdapter() : +", Throwable("SearchFragment"))
        return mRowsAdapter
    }

    private fun searchFor(query: String) {
        if (DEBUG) Log.d(TAG, "searchFor() : +", Throwable("SearchFragment"))
        mRowsAdapter!!.clear()
        if (!TextUtils.isEmpty(query)) {
            mDelayedLoad!!.setSearchQuery(query)
            mHandler.removeCallbacks(mDelayedLoad!!)
            mHandler.postDelayed(mDelayedLoad!!, SEARCH_DELAY_MS.toLong())
        }
    }

    override fun onQueryTextChange(newQuery: String): Boolean {
        if (DEBUG) {
            Log.d(TAG, "onQueryTextChange() : +", Throwable("SearchFragment"))
        }
        if (newQuery.isEmpty()) {
            mRowsAdapter!!.clear()
        } else {
            searchFor(newQuery)
            mSearchedList.clear()
        }
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        if (DEBUG) {
            Log.d(TAG, "onQueryTextSubmit() : +", Throwable("SearchFragment"))
        }
        searchFor(query)
        mSearchedList.clear()
        return true
    }

    private fun getDefaultItemClickedListener(): OnItemViewClickedListener {
        if (DEBUG) {
            Log.d(TAG, "getDefaultItemClickedListener() : +", Throwable("SearchFragment"))
        }
        return OnItemViewClickedListener { itemViewHolder, item, rowViewHolder, row ->
            if (DEBUG) {
                Log.d(
                    TAG,
                    "onItemClicked() : +",
                    Throwable("SearchFragment")
                )
            }
            if (item is AppModel) {
//                val appBean: AppModel = item as AppModel
//                val launchIntent: Intent = getActivity().getPackageManager().getLeanbackLaunchIntentForPackage(
//                        appBean.getPackageName()
//                )
//                if (launchIntent != null) {
//                    getActivity().startActivity(launchIntent)
//                }
            }
        }
    }

    private class AddPlaylistButtonPresenter : Presenter() {
        override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
            if (DEBUG) {
                Log.d(TAG, "onCreateViewHolder() : +", Throwable("SearchFragment"))
            }
            val inflater = LayoutInflater.from(parent.context)
            val v: View = inflater.inflate(R.layout.add_playlist, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
            if (DEBUG) {
                Log.d(TAG, "onBindViewHolder() : +", Throwable("SearchFragment"))
            }
        }

        override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
            if (DEBUG) {
                Log.d(TAG, "onUnbindViewHolder() : +", Throwable("SearchFragment"))
            }
        }
    }

    private class SearchPresenterSelector : PresenterSelector() {
        private val mCardPresenter: AppCardPresenter = AppCardPresenter()
        private val mAddChannelButtonPresenter = AddPlaylistButtonPresenter()

        override fun getPresenter(item: Any?): Presenter {
            if (DEBUG) {
                Log.d(TAG, "getPresenter() : +", Throwable("SearchFragment"))
            }
            return if (item is AppModel) {
                mCardPresenter
            } else {
                mAddChannelButtonPresenter
            }
        }
    }

    private inner class SearchRunnable : Runnable {

        private var mQuery: String? = null
        fun setSearchQuery(query: String?) {
            if (DEBUG) {
                Log.d(TAG, "setSearchQuery() : +", Throwable("SearchFragment"))
            }
            mQuery = query
        }

        override fun run() {
            if (DEBUG) {
                Log.d(TAG, "run() : +", Throwable("SearchFragment"))
            }

            mRowsAdapter?.clear()
            //mAppDataList = AppDataManage.launchAppList
            mAppDataList = getMockyData()
            Log.d(TAG , " item size=  $mQuery")

            for (item in mAppDataList) {
                val name = item.getName()
                if (name != null) {
                    if (name.contains(mQuery.toString())) {
                        mSearchedList.add(item)
                    }
                }
            }

            val searchRowAdapter = ArrayObjectAdapter(SearchPresenterSelector());
            searchRowAdapter.addAll(0, mSearchedList);
            val header = HeaderItem(0, "search results for the input : " + mQuery)
            mRowsAdapter?.add(ListRow(header, searchRowAdapter))

//            val intent = Intent(Intent.ACTION_ASSIST)
//            intent.putExtra(SearchManager.QUERY, "Search query")
//            startActivity(intent)
        }
    }

    private fun getMockyData(): ArrayList<AppModel> {
        val list = arrayListOf<AppModel>()
        val app1 = AppModel()
        app1.setName("name 1")
        app1.setDesc("desc 1")
        app1.setIcon(resources.getDrawable(R.drawable.ic_title))
        list.add(app1)

        val app2 = AppModel()
        app2.setName("name 2")
        app2.setDesc("desc 2")
        app2.setIcon(resources.getDrawable(R.drawable.lb_ic_loop))
        list.add(app2)

        val app3 = AppModel()
        app3.setName("name 3")
        app3.setDesc("desc 3")
        app3.setIcon(resources.getDrawable(R.drawable.lb_ic_sad_cloud))
        list.add(app3)

        return list
    }

    companion object {
        private const val SEARCH_DELAY_MS = 10
        var DEBUG = false
        var TAG = "SearchFragment"
    }
}