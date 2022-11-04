package com.example.androidtv.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.androidtv.data.db.AppDatabase
import com.example.androidtv.data.db.MovieDao
import com.example.androidtv.data.db.MovieDbModel
import com.example.androidtv.data.model.Movie
import com.example.androidtv.data.model.MovieList.list

/**
 * Created by Dipak Kumar Mehta on 10/21/2021.
 */
object MainFragmentRepository {

    private var movieList = MutableLiveData<List<MovieDbModel>>()
    private var db: AppDatabase ?= null

    fun init(context: Context) {
        db= AppDatabase.getAppDatabase(context)
    }

//    fun getMovieList():MutableLiveData<List<Movie>>  {
//        movieList.value = list
//        return movieList
//    }

    fun getMovieListFromDb():MutableLiveData<List<MovieDbModel>> {
        movieList.value = db?.getMovieDao()?.getAllMovie() as List<MovieDbModel>?
        Log.d("getMovieListFromDb ", "${movieList.value.toString()}")
        return movieList
    }

    fun addMovieList() {
        var size:Int ?=null
        for (item in list) {
            Log.d("list ", " ${list.toString()}")
            db?.getMovieDao()?.addMovies(item)
            size = db?.getMovieDao()?.getAllMovie()?.size
        }
        Log.d("No of movies " ,  " $size")
    }
}