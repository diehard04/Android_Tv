package com.example.androidtv.pagination

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.leanback.app.BackgroundManager
import androidx.leanback.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.androidtv.*
import com.example.androidtv.data.api.TVLauncherApiClient
import com.example.androidtv.data.api.TVLauncherApiInterface
import com.example.androidtv.tvlaunchermodel.HeaderList
import com.example.androidtv.utils.Progress
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtv.data.model.Movie
import com.example.androidtv.data.model.MovieList
import kotlin.collections.ArrayList


/**
 * Created by Dipak Kumar Mehta on 10/18/2021.
 */
class PagingSampleFragment:Fragment() {

    private val mHandler = Handler()
    private lateinit var mBackgroundManager: BackgroundManager
    private var mDefaultBackground: Drawable? = null
    private lateinit var mMetrics: DisplayMetrics
    private var mBackgroundTimer: Timer? = null
    private var mBackgroundUri: String? = null
    private var disposable: Disposable? = null
    private var headerList: HeaderList? = null
    private var rvPagination:RecyclerView ?= null


    private var loading = true
    var pastVisiblesItems = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prepareBackgroundManager()
        //setupUIElements()

        //internetConnection()

        //loadRows()
        //setupEventListeners()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_paging, container, false);
        setUpView(view)
        return view
    }

    private fun setUpView(view: View) {
        rvPagination = view.findViewById(R.id.recyclerView)
        val myListData = getItems()
        val adapter = context?.let { MyListAdapter(myListData, it) }
        rvPagination?.setHasFixedSize(true)
        val mLayoutManager: LinearLayoutManager = LinearLayoutManager(context)
        rvPagination?.layoutManager = mLayoutManager
        rvPagination?.adapter = adapter

        rvPagination?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.childCount;
                    totalItemCount = mLayoutManager.itemCount;
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            // Do pagination.. i.e. fetch new data
                            Handler().postDelayed(object : Runnable{
                                override fun run() {
                                    var newData = arrayListOf<MyListData>(MyListData("new 1"), MyListData("new 2"))
                                    adapter?.addItems(newData)
                                }
                            }, 1500)

                            loading = true;
                        }
                    }
                }
            }
        })

    }

    private fun getItems(): ArrayList<MyListData> {
        val list = arrayListOf<MyListData>(MyListData("111"), MyListData("2222"), MyListData("3333"), MyListData("44444"),
            MyListData("555"), MyListData("66666"), MyListData("77777"), MyListData("8888"),
            MyListData("9999"), MyListData("10"), MyListData("11"), MyListData("12")
        )
        return list
    }

    private fun internetConnection() {
        context?.let { Progress.showProgressBar(it) }
        val tvLauncherApiInterface: TVLauncherApiInterface? = TVLauncherApiClient.getRetrofit()?.create(TVLauncherApiInterface::class.java)
        val responseSingleHeaderList: Single<Response<HeaderList>>
        tvLauncherApiInterface?.let {
            responseSingleHeaderList = it.getHeaderList()
            Log.d(TAG, " $responseSingleHeaderList")
            responseSingleHeaderList.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Response<HeaderList>> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "onSubscribe")
                        disposable = d
                    }

                    override fun onSuccess(t: Response<HeaderList>) {
                        Log.d(TAG, "onSuccess")
                        headerList = t.body()
                        Progress.cancelProgressBar()
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "onError")
                        Progress.cancelProgressBar()
                    }
                })
        }
    }

    private fun prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager.attach(activity!!.window)
        mDefaultBackground = ContextCompat.getDrawable(activity!!, R.drawable.default_background)
        mMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(mMetrics)
    }

//    private fun setupUIElements() {
//        title = "Pagination Activity"
//        // over title
//        headersState = BrowseSupportFragment.HEADERS_ENABLED
//        isHeadersTransitionOnBackEnabled = true
//
//        // set fastLane (or headers) background color
//        brandColor = ContextCompat.getColor(activity!!, R.color.fastlane_background)
//        // set search icon color
//        searchAffordanceColor = ContextCompat.getColor(activity!!, R.color.search_opaque)
//    }

    private fun loadRows() {
        val list = MovieList.list
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val cardPresenter = CardPresenter()
        for (i in 0 until PagingSampleFragment.NUM_ROWS) {
            val listRowAdapter = ArrayObjectAdapter(cardPresenter)
            for (j in 0 until PagingSampleFragment.NUM_COLS) {
                listRowAdapter.add(list[j % 5])
            }
            val header = HeaderItem(i.toLong(), MovieList.MOVIE_CATEGORY[i])
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }



        //val gridHeader = HeaderItem(PagingSampleFragment.NUM_ROWS.toLong(), "PREFERENCES")
        //val mGridPresenter = GridItemPresenter()
        //val gridRowAdapter = ArrayObjectAdapter(mGridPresenter)

//        gridRowAdapter.add(resources.getString(R.string.grid_view))
//        gridRowAdapter.add(getString(R.string.error_fragment))
//        gridRowAdapter.add(resources.getString(R.string.personal_settings))

        //rowsAdapter.add(ListRow(gridHeader, gridRowAdapter))
       // adapter = rowsAdapter

    }

//    private fun setupEventListeners() {
//        setOnSearchClickedListener {
//            Toast.makeText(activity!!, "Implement your own in-app search", Toast.LENGTH_LONG)
//                .show()
//        }
//
//        onItemViewClickedListener = ItemViewClickedListener()
//        onItemViewSelectedListener = ItemViewSelectedListener()
//    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(itemViewHolder: Presenter.ViewHolder, item: Any, rowViewHolder: RowPresenter.ViewHolder, row: Row) {
            if (item is Movie) {
                Log.d(PagingSampleFragment.TAG, "ItemViewClickedListener: " + item.toString())
                val intent = Intent(activity!!, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.MOVIE, item)
                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!,
                    (itemViewHolder.view as ImageCardView).mainImageView, DetailsActivity.SHARED_ELEMENT_NAME
                ).toBundle()
                startActivity(intent, bundle)
            } else if (item is String) {
                if (item.contains(getString(R.string.error_fragment))) {
                    val intent = Intent(activity!!, BrowseErrorActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(activity!!, item, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?, item: Any?,
            rowViewHolder: RowPresenter.ViewHolder, row: Row
        ) {
            if (item is Movie) {
                Log.d(PagingSampleFragment.TAG, "ItemViewSelectedListener: " + item.toString())
                mBackgroundUri = item.backgroundImageUrl
                startBackgroundTimer()
            }
        }
    }

    private fun updateBackground(uri: String?) {
        val width = mMetrics.widthPixels
        val height = mMetrics.heightPixels
        Glide.with(activity!!)
            .load(uri)
            .centerCrop()
            .error(mDefaultBackground)
            .into<SimpleTarget<Drawable>>(
                object : SimpleTarget<Drawable>(width, height) {
                    override fun onResourceReady(
                        drawable: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        mBackgroundManager.drawable = drawable
                    }
                })
        mBackgroundTimer?.cancel()
    }

    private fun startBackgroundTimer() {
        mBackgroundTimer?.cancel()
        mBackgroundTimer = Timer()
        mBackgroundTimer?.schedule(UpdateBackgroundTask(), PagingSampleFragment.BACKGROUND_UPDATE_DELAY.toLong())
    }

    private inner class UpdateBackgroundTask : TimerTask() {
        override fun run() {
            mHandler.post { updateBackground(mBackgroundUri) }
        }
    }

    private inner class GridItemPresenter : Presenter() {
        override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
            val view = TextView(parent.context)
            view.layoutParams = ViewGroup.LayoutParams(
                PagingSampleFragment.GRID_ITEM_WIDTH,
                PagingSampleFragment.GRID_ITEM_HEIGHT
            )
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            view.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.default_background))
            view.setTextColor(Color.WHITE)
            view.gravity = Gravity.CENTER
            return Presenter.ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any) {
            (viewHolder.view as TextView).text = item as String
        }

        override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder) {}
    }

    companion object {
        private val TAG = "PagingSampleFragment"
        private val BACKGROUND_UPDATE_DELAY = 300
        private val GRID_ITEM_WIDTH = 200
        private val GRID_ITEM_HEIGHT = 200
        private val NUM_ROWS = 6
        private val NUM_COLS = 15
    }
}