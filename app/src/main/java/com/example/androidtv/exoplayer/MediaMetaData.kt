package com.example.androidtv.exoplayer

import android.net.Uri
import android.os.Parcel

import android.os.Parcelable

/**
 * Created by Dipak Kumar Mehta on 11/8/2021.
 */
class MediaMetaData : Parcelable {
    private var mMediaSourceUri: Uri? = null
    private var mMediaSourcePath: String? = null
    private var mMediaTitle: String? = null
    private var mMediaArtistName: String? = null
    private var mMediaAlbumName: String? = null
    private var mMediaAlbumArtResId = 0
    private var mMediaAlbumArtUrl: String? = null

    internal constructor(
        mediaSourceUri: Uri?, mediaSourcePath: String?, mediaTitle: String?,
        mediaArtistName: String?, mediaAlbumName: String?, mediaAlbumArtResId: Int,
        mediaAlbumArtUrl: String?
    ) {
        mMediaSourceUri = mediaSourceUri
        mMediaSourcePath = mediaSourcePath
        mMediaTitle = mediaTitle
        mMediaArtistName = mediaArtistName
        mMediaAlbumName = mediaAlbumName
        mMediaAlbumArtResId = mediaAlbumArtResId
        mMediaAlbumArtUrl = mediaAlbumArtUrl
    }

    constructor() {}
    constructor(`in`: Parcel) {
        mMediaSourceUri = `in`.readParcelable(null)
        mMediaSourcePath = `in`.readString()
        mMediaTitle = `in`.readString()
        mMediaArtistName = `in`.readString()
        mMediaAlbumName = `in`.readString()
        mMediaAlbumArtResId = `in`.readInt()
        mMediaAlbumArtUrl = `in`.readString()
    }

    fun getMediaSourceUri(): Uri? {
        return mMediaSourceUri
    }

    fun setMediaSourceUri(mediaSourceUri: Uri?) {
        mMediaSourceUri = mediaSourceUri
    }

    fun getMediaSourcePath(): String? {
        return mMediaSourcePath
    }

    fun setMediaSourcePath(mediaSourcePath: String?) {
        mMediaSourcePath = mediaSourcePath
    }

    fun getMediaTitle(): String? {
        return mMediaTitle
    }

    fun setMediaTitle(mediaTitle: String?) {
        mMediaTitle = mediaTitle
    }

    fun getMediaArtistName(): String? {
        return mMediaArtistName
    }

    fun setMediaArtistName(mediaArtistName: String?) {
        mMediaArtistName = mediaArtistName
    }

    fun getMediaAlbumName(): String? {
        return mMediaAlbumName
    }

    fun setMediaAlbumName(mediaAlbumName: String?) {
        mMediaAlbumName = mediaAlbumName
    }

    fun getMediaAlbumArtResId(): Int {
        return mMediaAlbumArtResId
    }

    fun setMediaAlbumArtResId(mediaAlbumArtResId: Int) {
        mMediaAlbumArtResId = mediaAlbumArtResId
    }

    fun getMediaAlbumArtUrl(): String? {
        return mMediaAlbumArtUrl
    }

    fun setMediaAlbumArtUrl(mediaAlbumArtUrl: String?) {
        mMediaAlbumArtUrl = mediaAlbumArtUrl
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelable(mMediaSourceUri, flags)
        dest.writeString(mMediaSourcePath)
        dest.writeString(mMediaTitle)
        dest.writeString(mMediaArtistName)
        dest.writeString(mMediaAlbumName)
        dest.writeInt(mMediaAlbumArtResId)
        dest.writeString(mMediaAlbumArtUrl)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MediaMetaData?> =
            object : Parcelable.Creator<MediaMetaData?> {
                override fun createFromParcel(parcel: Parcel): MediaMetaData? {
                    return MediaMetaData(parcel)
                }

                override fun newArray(size: Int): Array<MediaMetaData?> {
                    return arrayOfNulls(size)
                }
            }
    }
}