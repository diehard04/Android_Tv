package com.example.androidtv.data.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

/**
 * Created by Dipak Kumar Mehta on 11/2/2021.
 */
@Entity(tableName = "movie")
data class MovieDbModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    var id:Int,

    @ColumnInfo(name = "title")
    @NonNull
    var title:String,

    @ColumnInfo(name = "desc")
    @NonNull
    var desc:String,

    @ColumnInfo(name = "backgroundImageUrl")
    @NonNull
    var backgroundImageUrl:String,

    @ColumnInfo(name = "cardImageUrl")
    @NonNull
    var cardImageUrl:String,

    @ColumnInfo(name = "videoUrl")
    @NotNull
    var videoUrl:String,

    @ColumnInfo(name = "studio")
    @NotNull
    var studio:String) {

    override fun toString(): String {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", backgroundImageUrl='" + backgroundImageUrl + '\'' +
                ", cardImageUrl='" + cardImageUrl + '\'' +
                '}'
    }
}