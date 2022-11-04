package com.example.androidtv.search

import android.os.Parcel

import android.os.Parcelable
/**
 * Created by Dipak Kumar Mehta on 10/20/2021.
 */

class PhotoItem : Parcelable {
    private var mTitle: String
    private var mContent: String? = null
    private var mImageResourceId: Int

    constructor(title: String, imageResourceId: Int) : this(title, null, imageResourceId) {}
    constructor(title: String, content: String?, imageResourceId: Int) {
        mTitle = title
        mContent = content
        mImageResourceId = imageResourceId
    }

    fun getImageResourceId(): Int {
        return mImageResourceId
    }

    fun getTitle(): String {
        return mTitle
    }

    fun getContent(): String? {
        return mContent
    }

    override fun toString(): String {
        return mTitle
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(mTitle)
        dest.writeInt(mImageResourceId)
    }

    private constructor(`in`: Parcel) {
        mTitle = `in`.readString()!!
        mImageResourceId = `in`.readInt()
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PhotoItem?> = object : Parcelable.Creator<PhotoItem?> {
            override fun createFromParcel(`in`: Parcel): PhotoItem? {
                return PhotoItem(`in`)
            }

            override fun newArray(size: Int): Array<PhotoItem?> {
                return arrayOfNulls(size)
            }
        }
    }
}