package com.example.androidtv.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidtv.data.db.MovieDbModel
import com.example.androidtv.data.model.Movie
import com.example.androidtv.data.repository.MainFragmentRepository

/**
 * Created by Dipak Kumar Mehta on 10/21/2021.
 */
class MainFragmentViewModel:ViewModel() {

    private var movieList:MutableLiveData<List<MovieDbModel>>?=null

    fun getMovieList(context: Context):LiveData<List<MovieDbModel>> {
        MainFragmentRepository.init(context)
        movieList = MainFragmentRepository.getMovieListFromDb()
        Log.d("MainFragmentViewModel ", " ${movieList?.value?.size}")
        return movieList as MutableLiveData<List<MovieDbModel>>
    }

    fun addMovieList(context: Context){
        MainFragmentRepository.init(context)
        MainFragmentRepository.addMovieList()
    }
}