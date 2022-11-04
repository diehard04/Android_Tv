package com.example.androidtv.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

/**
 * Created by Dipak Kumar Mehta on 11/2/2021.
 */
@Dao
interface MovieDao {

    @Insert(onConflict = REPLACE)
    fun addMovies(vararg model: MovieDbModel?)

    @Delete
    fun deleteMovieItem(model: MovieDbModel?)

    @Query("SELECT * FROM movie")
    fun getLiveDataAllMovie(): LiveData<List<MovieDbModel?>?>?

    @Query("SELECT * FROM movie")
    fun getAllMovie(): List<MovieDbModel?>?


}