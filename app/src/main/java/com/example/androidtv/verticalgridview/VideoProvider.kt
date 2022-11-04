package com.example.androidtv.verticalgridview

import android.content.Context
import android.util.Log
import com.example.androidtv.data.model.Movie
import com.example.androidtv.R
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.URL
import java.net.URLConnection


class VideoProvider() {
    protected fun parseUrl(urlString: String): JSONObject? {
        Log.d(TAG, "Parse URL: $urlString")
        var `is`: InputStream? = null
        mPrefixUrl = mContext?.getResources()?.getString(R.string.error_fragment)
        try {
            val url = URL(urlString)
            val urlConnection: URLConnection = url.openConnection()
            `is` = BufferedInputStream(urlConnection.getInputStream())
            val reader = BufferedReader(
                InputStreamReader(
                    urlConnection.getInputStream(), "iso-8859-1"
                ), 8
            )
            val sb = StringBuilder()
            var line: String? = null
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            val json = sb.toString()
            return JSONObject(json)
        } catch (e: Exception) {
            Log.d(TAG, "Failed to parse the json for media list", e)
            return null
        } finally {
            if (null != `is`) {
                try {
                    `is`.close()
                } catch (e: IOException) {
                    Log.d(TAG, "JSON feed closed", e)
                }
            }
        }
    }

    companion object {
        private val TAG = "VideoProvider"
        private val TAG_MEDIA = "videos"
        private val TAG_GOOGLE_VIDEOS = "googlevideos"
        private val TAG_CATEGORY = "category"
        private val TAG_STUDIO = "studio"
        private val TAG_SOURCES = "sources"
        private val TAG_DESCRIPTION = "description"
        private val TAG_CARD_THUMB = "card"
        private val TAG_BACKGROUND = "background"
        private val TAG_TITLE = "title"
        private var mMovieList: HashMap<String, List<Movie>>? = null
        private var mContext: Context? = null
        private var mPrefixUrl: String? = null
        fun setContext(context: Context?) {
            if (mContext == null) mContext = context
        }

        val movieList: HashMap<String, List<Movie>>?
            get() = mMovieList

        @Throws(JSONException::class)
        fun buildMedia(ctx: Context?, url: String): HashMap<String, List<Movie>>? {
            if (null != mMovieList) {
                return mMovieList
            }
            mMovieList = HashMap<String, List<Movie>>()
            val jsonObj = VideoProvider().parseUrl(url)
            val categories = jsonObj!!.getJSONArray(TAG_GOOGLE_VIDEOS)
            if (null != categories) {
                Log.d(TAG, "category #: " + categories.length())
                var title = String()
                var videoUrl = String()
                var bgImageUrl = String()
                var cardImageUrl = String()
                var studio = String()
                for (i in 0 until categories.length()) {
                    val category = categories.getJSONObject(i)
                    val category_name = category.getString(TAG_CATEGORY)
                    val videos = category.getJSONArray(TAG_MEDIA)
                    Log.d(
                        TAG,
                        "category: " + i + " Name:" + category_name + " video length: "
                                + videos.length()
                    )
                    val categoryList: MutableList<Movie> = ArrayList<Movie>()
                    if (null != videos) {
                        for (j in 0 until videos.length()) {
                            val video = videos.getJSONObject(j)
                            val description = video.getString(TAG_DESCRIPTION)
                            val videoUrls = video.getJSONArray(TAG_SOURCES)
                            if (null == videoUrls || videoUrls.length() == 0) {
                                continue
                            }
                            title = video.getString(TAG_TITLE)
                            videoUrl = getVideoPrefix(category_name, videoUrls.getString(0))
                            bgImageUrl = getThumbPrefix(
                                category_name, title,
                                video.getString(TAG_BACKGROUND)
                            )
                            cardImageUrl = getThumbPrefix(
                                category_name, title,
                                video.getString(TAG_CARD_THUMB)
                            )
                            studio = video.getString(TAG_STUDIO)
                            categoryList.add(
                                buildMovieInfo(
                                    category_name, title, description, studio,
                                    videoUrl, cardImageUrl,
                                    bgImageUrl
                                )
                            )
                        }
                        mMovieList!![category_name] = categoryList
                    }
                }
            }
            return mMovieList
        }

        private fun buildMovieInfo(
            category: String, title: String,
            description: String, studio: String, videoUrl: String, cardImageUrl: String,
            bgImageUrl: String
        ): Movie {
            val movie = Movie()
//            movie.setId(Movie.getCount())
//            Movie.incCount()
//            movie.setTitle(title)
//            movie.setDescription(description)
//            movie.setStudio(studio)
//            movie.setCategory(category)
//            movie.setCardImageUrl(cardImageUrl)
//            movie.setBackgroundImageUrl(bgImageUrl)
//            movie.setVideoUrl(videoUrl)
            return movie
        }

        private fun getVideoPrefix(category: String, videoUrl: String): String {
            var ret: String = ""
            ret = (mPrefixUrl + category.replace(" ", "%20") + '/' +
                    videoUrl.replace(" ", "%20"))
            return ret
        }

        private fun getThumbPrefix(category: String, title: String, imageUrl: String): String {
            var ret: String = ""
            ret = (mPrefixUrl + category.replace(" ", "%20") + '/' +
                    title.replace(" ", "%20") + '/' +
                    imageUrl.replace(" ", "%20"))
            return ret
        }
    }
}