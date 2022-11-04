package com.example.androidtv.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.androidtv.*
import com.example.androidtv.data.api.TVLauncherApiClient
import com.example.androidtv.data.api.TVLauncherApiInterface
import com.example.androidtv.data.db.MovieDbModel
import com.example.androidtv.data.model.Movie
import com.example.androidtv.data.model.MovieList
import com.example.androidtv.exoplayer.TvPlayerActivity
import com.example.androidtv.menuitems.MainTabsActivity
import com.example.androidtv.search.SearchActivity
import com.example.androidtv.tvlaunchermodel.HeaderList
import com.example.androidtv.ui.user.UserActivity
import com.example.androidtv.utils.Progress
import com.example.androidtv.verticalgridview.VerticalGridActivity
import com.example.androidtv.viewmodel.MainFragmentViewModel
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.*

/**
 * Loads a grid of cards with movies to browse.
 */
class MainFragment : BrowseSupportFragment() {
    private val mHandler = Handler()
    private lateinit var mBackgroundManager: BackgroundManager
    private var mDefaultBackground: Drawable? = null
    private lateinit var mMetrics: DisplayMetrics
    private var mBackgroundTimer: Timer? = null
    private var mBackgroundUri: String? = null
    private var disposable: Disposable? = null
    private var headerList: HeaderList? = null
    private lateinit var mainFragmentViewModel: MainFragmentViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            mainFragmentViewModel = ViewModelProvider(activity!!).get(MainFragmentViewModel::class.java)
        }

        prepareBackgroundManager()
        setupUIElements()
        //internetConnection()
        addMockyMovies()
        loadRows()
        setupEventListeners()
    }

    private fun addMockyMovies() {
        context?.let { mainFragmentViewModel.addMovieList(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun internetConnection() {
        context?.let { Progress.showProgressBar(it) }
        val tvLauncherApiInterface: TVLauncherApiInterface? =
            TVLauncherApiClient.getRetrofit()?.create(TVLauncherApiInterface::class.java)
        val responseSingleHeaderList: Single<Response<HeaderList>>
        tvLauncherApiInterface?.let {
            responseSingleHeaderList = it.getHeaderList()
            Log.d(TAG, " $responseSingleHeaderList")
            responseSingleHeaderList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Response<HeaderList>> {
                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                    }

                    override fun onSuccess(t: Response<HeaderList>) {
                        headerList = t.body()
                        Progress.cancelProgressBar()
                    }

                    override fun onError(e: Throwable) {
                        Progress.cancelProgressBar()
                    }
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: " + mBackgroundTimer?.toString())
        mBackgroundTimer?.cancel()
    }

    private fun prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager.attach(activity!!.window)
        mDefaultBackground = ContextCompat.getDrawable(activity!!, R.drawable.default_background)
        mMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(mMetrics)
    }

    private fun setupUIElements() {
        title = getString(R.string.browse_title)
        // over title
        headersState = BrowseSupportFragment.HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true

        // set fastLane (or headers) background color
        brandColor = ContextCompat.getColor(activity!!, R.color.fastlane_background)
        // set search icon color
        searchAffordanceColor = ContextCompat.getColor(activity!!, R.color.search_opaque)
    }

    private fun loadRows() {
        var list: List<MovieDbModel>? = null
        mainFragmentViewModel.getMovieList(context!!).observe(this, androidx.lifecycle.Observer {
            list = it
            if (list?.isNotEmpty() == true) {
                val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
                val cardPresenter = CardPresenter()

                for (i in 0 until NUM_ROWS) {
                    if (i != 0) {
                        Collections.shuffle(list)
                    }
                    val listRowAdapter = ArrayObjectAdapter(cardPresenter)
                    for (j in 0 until NUM_COLS) {
                        listRowAdapter.add(list!![j % 5])
                    }
                    val header = HeaderItem(i.toLong(), MovieList.MOVIE_CATEGORY[i])
                    rowsAdapter.add(ListRow(header, listRowAdapter))
                }

                val gridHeader = HeaderItem(NUM_ROWS.toLong(), "PREFERENCES")

                val mGridPresenter = GridItemPresenter()
                val gridRowAdapter = ArrayObjectAdapter(mGridPresenter)
                gridRowAdapter.add(resources.getString(R.string.grid_view))
                gridRowAdapter.add(getString(R.string.error_fragment))
                gridRowAdapter.add(resources.getString(R.string.nev_menu))
                rowsAdapter.add(ListRow(gridHeader, gridRowAdapter))
                adapter = rowsAdapter

            }
        })
    }

    private fun setupEventListeners() {
        setOnSearchClickedListener {
            val intent = Intent(activity!!, SearchActivity::class.java)
            startActivity(intent)
        }

        onItemViewClickedListener = ItemViewClickedListener()
        onItemViewSelectedListener = ItemViewSelectedListener()
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(itemViewHolder: Presenter.ViewHolder, item: Any, rowViewHolder: RowPresenter.ViewHolder, row: Row) {
            if (item is Movie) {
                Log.d(TAG, "Item: " + item.toString())
                val intent = Intent(activity!!, MainTabsActivity::class.java)
                intent.putExtra(DetailsActivity.MOVIE, item)
                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity!!, (itemViewHolder.view as ImageCardView).mainImageView,
                    DetailsActivity.SHARED_ELEMENT_NAME
                ).toBundle()
                startActivity(intent, bundle)
            } else if (item is String) {
                if (item.contains(getString(R.string.error_fragment))) {
                    val intent = Intent(activity!!, TvPlayerActivity::class.java)
                    startActivity(intent)
                } else if(item.contains(getString(R.string.grid_view))) {
                    val intent = Intent(activity!!, VerticalGridActivity::class.java)
                    startActivity(intent)
                } else if (item.contains(getString(R.string.nev_menu))) {
                    val intent = Intent(activity!!, MainTabsActivity::class.java)
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
        mBackgroundTimer?.schedule(UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY.toLong())
    }

    private inner class UpdateBackgroundTask : TimerTask() {
        override fun run() {
            mHandler.post { updateBackground(mBackgroundUri) }
        }
    }

    private inner class GridItemPresenter : Presenter() {
        override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
            val view = TextView(parent.context)
            view.layoutParams = ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT)
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
        private val TAG = "MainFragment"
        private val BACKGROUND_UPDATE_DELAY = 300
        private val GRID_ITEM_WIDTH = 200
        private val GRID_ITEM_HEIGHT = 200
        private val NUM_ROWS = 6
        private val NUM_COLS = 15
    }
}