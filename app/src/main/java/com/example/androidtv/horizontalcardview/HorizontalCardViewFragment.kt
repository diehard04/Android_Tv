package com.example.androidtv.horizontalcardview

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.androidtv.*
import com.example.androidtv.data.api.HttpHandler
import com.example.androidtv.data.model.Movie
import com.example.androidtv.data.model.MovieList
import com.example.androidtv.verticalgridview.VerticalGridActivity
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class HorizontalCardViewFragment : BrowseSupportFragment() {
    private lateinit var mBackgroundManager: BackgroundManager
    private var mDefaultBackground: Drawable? = null
    private lateinit var mMetrics: DisplayMetrics
    private var mBackgroundUri: String? = null
    private var mBackgroundTimer: Timer? = null
    private val mHandler = Handler()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prepareBackgroundManager()
        setupUIElements()
        loadRows()
        setupEventListeners()
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

    private fun setupEventListeners() {
        setOnSearchClickedListener {
            Toast.makeText(activity!!, "Implement your own in-app search", Toast.LENGTH_LONG)
                .show()
        }

        onItemViewClickedListener = ItemViewClickedListener()
        onItemViewSelectedListener = ItemViewSelectedListener()
    }

    private inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?, item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?, row: Row?) {
            if (item is Movie) {
                mBackgroundUri = item.backgroundImageUrl
            }
        }
    }

    @SuppressLint("LongLogTag")
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: " + mBackgroundTimer?.toString())
        mBackgroundTimer?.cancel()
    }

    private fun startBackgroundTimer() {
        mBackgroundTimer?.cancel()
        mBackgroundTimer = Timer()
        mBackgroundTimer?.schedule(
            UpdateBackgroundTask(),
            HorizontalCardViewFragment.BACKGROUND_UPDATE_DELAY.toLong()
        )
    }

    private inner class UpdateBackgroundTask : TimerTask() {
        override fun run() {
            mHandler.post { updateBackground(mBackgroundUri) }
        }
    }

    private fun updateBackground(Uri: String?) {
        val width = mMetrics.widthPixels
        val height = mMetrics.heightPixels
        Glide.with(activity!!).load(String()).centerCrop().error(mDefaultBackground)
            .into<SimpleTarget<Drawable>>(
                object : SimpleTarget<Drawable>(width, height) {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        mBackgroundManager.drawable = resource
                    }
                }
            )
        mBackgroundTimer?.cancel()
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder, item: Any,
            rowViewHolder: RowPresenter.ViewHolder, row: Row
        ) {

            Toast.makeText(activity, item.toString(), Toast.LENGTH_SHORT).show()

            if (item is Movie) {
                Log.d(TAG, "Item: " + item.toString())
                val intent = Intent(activity!!, VerticalGridActivity::class.java)
                intent.putExtra(VerticalGridActivity.MOVIE, item)

                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity!!,
                    (itemViewHolder.view as ImageCardView).mainImageView,
                    VerticalGridActivity.SHARED_ELEMENT_NAME
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

    private fun loadRows() {
        val list = MovieList.list

        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val cardPresenter = CardPresenter()

        for (i in 0 until HorizontalCardViewFragment.NUM_ROWS) {
            if (i != 0) {
                Collections.shuffle(list)
            }
            val listRowAdapter = ArrayObjectAdapter(cardPresenter)
            for (j in 0 until HorizontalCardViewFragment.NUM_COLS) {
                listRowAdapter.add(list[j % 5])
            }
            val header = HeaderItem(i.toLong(), MovieList.MOVIE_CATEGORY[i])
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }

        val gridHeader = HeaderItem(HorizontalCardViewFragment.NUM_ROWS.toLong(), "PREFERENCES")

        val mGridPresenter = HorizontalGridItemPresenter(this)
        val gridRowAdapter = ArrayObjectAdapter(mGridPresenter)
        gridRowAdapter.add(resources.getString(R.string.grid_view))
        gridRowAdapter.add(getString(R.string.error_fragment))
        gridRowAdapter.add(resources.getString(R.string.personal_settings))
        rowsAdapter.add(ListRow(gridHeader, gridRowAdapter))
        adapter = rowsAdapter
    }

    companion object {
        private val TAG = "HorizontalCardView"
        private val BACKGROUND_UPDATE_DELAY = 300
        val GRID_ITEM_WIDTH = 200
        val GRID_ITEM_HEIGHT = 200
        private val NUM_ROWS = 6
        private val NUM_COLS = 15
    }

    private class getMovieList(val activity:Activity) :AsyncTask<Void?, Void?, Void?>() {
        var date: String? = null
        var time: String? = null
        var jsonStr: String? = null

        override fun onPreExecute() {
            super.onPreExecute()
//              Toast.makeText(getActivity(),"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        override fun doInBackground(vararg params: Void?): Void? {
            val sh = HttpHandler()
            // Making a request to url and getting response
            val url = "http://worldtimeapi.org/api/timezone/Asia/Kolkata"
            jsonStr = sh.makeServiceCall(url)
            Log.e("JSONSTR", jsonStr!!)
//            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    val jsonObj = JSONObject(jsonStr)
//                    // Getting JSON Array node
                    val dateAndTime = jsonObj.getString("datetime")
                    date = dateAndTime.substring(0, 10)
                    time = dateAndTime.substring(11, 19)
                    Log.d("DateTimeFragmnet", "value :$time")
                    Log.d("DateTimeFragmnet", "value :$date")
                } catch (e: JSONException) {
                    Log.e("Error", "Json parsing error: " + e.message)
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getActivity().getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    });
//
                }
            } else {
                Log.e("TAG", "Couldn't get json from server.")
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG).show();
//                    }
//                });
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
//            ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList,
//                    R.layout.list_item, new String[]{ "email","mobile"},
//                    new int[]{R.id.email, R.id.mobile});
//            lv.setAdapter(adapter);
            if (jsonStr != null && date != null) {
//                tv.setText(date + "    " + time);
                val c = Calendar.getInstance()
                val year = date!!.substring(0, 4)
                val month = date!!.substring(5, 7)
                val day = date!!.substring(8, 10)
                val hour = time!!.substring(0, 2)
                val min = time!!.substring(3, 5)
                val sec = time!!.substring(6, 8)
                c[year.toInt(), month.toInt() - 1, day.toInt(), hour.toInt(), min.toInt()] =
                    sec.toInt()
                val am = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                //am.setTime(c.timeInMillis)
//                tv.setText("Year = " + year + "  Month = " + month + "Day = " + day);
            }
        }
    }
}