package com.example.androidtv.search

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log

/**
 * Created by Dipak Kumar Mehta on 10/25/2021.
 */
class AppModel {
    var mLaunchColor = 0
    private var mDataDir: String? = null
    private var mIcon: Drawable? = null
    private var mBanner: Drawable? = null
    private var mId: String? = null
    private var mName: String? = null
    private var mLauncherName: String? = null
    private var mPackageName: String? = null
    private var mSysApp = false
    private var vId: String? = null
    private var imageurl: String? = null
    private var videourl: String? = null
    private var title: String? = null
    private var adUrl: String? = null
    private var mPackageMgr: PackageManager? = null
    private var mDescrption: String? = null
    private var time: String? = null
    private var preview_desc: String? = null
    private var rating: String? = null
    private var isFirstPosition = false

    fun isFirstPosition(): Boolean {
        return isFirstPosition
    }

    fun setFirstPosition(firstPosition: Boolean) {
        isFirstPosition = firstPosition
    }

    fun getCardposition(): Int {
        return cardposition
    }

    fun setCardposition(cardposition: Int) {
        this.cardposition = cardposition
    }

    private var cardposition = 0
    fun getIcon(): Drawable? {
        if (DEBUG) Log.d(TAG, "getIcon() : +", Throwable("AppModel"))
        return mIcon
    }

    fun setIcon(paramDrawable: Drawable?) {
        if (DEBUG) Log.d(TAG, "setIcon() : +", Throwable("AppModel"))
        mIcon = paramDrawable
    }

    fun getBanner(): Drawable? {
        if (DEBUG) Log.d(TAG, "getIcon() : +", Throwable("AppModel"))
        return mBanner
    }

    fun setBanner(paramDrawable: Drawable?) {
        if (DEBUG) Log.d(TAG, "setIcon() : +", Throwable("AppModel"))
        mBanner = paramDrawable
    }

    fun getId(): String? {
        if (DEBUG) Log.d(TAG, "getId() : +", Throwable("AppModel"))
        return mId
    }

    fun setId(paramString: String?) {
        if (DEBUG) Log.d(TAG, "setId() : +", Throwable("AppModel"))
        mId = paramString
    }

    fun getName(): String? {
        if (DEBUG) Log.d(TAG, "getName() : +", Throwable("AppModel"))
        return mName
    }

    fun setName(paramString: String?) {
        if (DEBUG) Log.d(TAG, "setName() : +", Throwable("AppModel"))
        mName = paramString
    }

    fun getDesc(): String? {
        if (DEBUG) Log.d(TAG, "getDesc() : +", Throwable("AppModel"))
        return mDescrption
    }

    fun getPackageName(): String? {
        if (DEBUG) Log.d(TAG, "getPackageName() : +", Throwable("AppModel"))
        return mPackageName
    }

    fun setPackageName(paramString: String?) {
        if (DEBUG) Log.d(TAG, "setPackageName() : +", Throwable("AppModel"))
        mPackageName = paramString
    }

    fun getPackageManager(): PackageManager? {
        if (DEBUG) Log.d(TAG, "getPackageManager() : +", Throwable("AppModel"))
        return mPackageMgr
    }

    fun setDataDir(paramString: String?) {
        if (DEBUG) Log.d(TAG, "setDataDir() : +", Throwable("AppModel"))
        mDataDir = paramString
    }

    fun getLaunchColor(): Int {
        if (DEBUG) Log.d(TAG, "getLaunchColor() : +", Throwable("AppModel"))
        return mLaunchColor
    }

    fun setLaunchColor(launchColor: Int) {
        if (DEBUG) Log.d(TAG, "setLaunchColor() : +", Throwable("AppModel"))
        mLaunchColor = launchColor
    }

    fun setDesc(paramString: String): String {
        if (DEBUG) Log.d(TAG, "setDesc() : +", Throwable("AppModel"))
        return paramString.also { mDescrption = it }
    }

    fun setPackageManger(paramString: PackageManager?) {
        if (DEBUG) Log.d(TAG, "setPackageManger() : +", Throwable("AppModel"))
        mPackageMgr = paramString
    }

    override fun toString(): String {
        if (DEBUG) Log.d(TAG, "toString() : +", Throwable("AppModel"))
        return ("AppBean [packageName=" + mPackageName + ", name="
                + mName + ", dataDir=" + mDataDir + "]")
    }

    fun setSysApp(sysApp: Boolean) {
        if (DEBUG) Log.d(TAG, "setSysApp() : +", Throwable("AppModel"))
        mSysApp = sysApp
    }

    fun getLauncherName(): String? {
        if (DEBUG) Log.d(TAG, "getLauncherName() : +", Throwable("AppModel"))
        return mLauncherName
    }

    fun setLauncherName(launcherName: String?) {
        if (DEBUG) Log.d(TAG, "setLauncherName() : +", Throwable("AppModel"))
        mLauncherName = launcherName
    }

    fun getAdUrl(): String? {
        return adUrl
    }

    fun setAdUrl(adUrl: String?) {
        this.adUrl = adUrl
    }

    fun getvId(): String? {
        return vId
    }

    fun setvId(vId: String?) {
        this.vId = vId
    }

    fun getImageurl(): String? {
        return imageurl
    }

    fun setImageurl(imageurl: String?) {
        this.imageurl = imageurl
    }

    fun getVideourl(): String? {
        return videourl
    }

    fun setVideourl(videourl: String?) {
        this.videourl = videourl
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun setDurationText(time: String?) {
        this.time = time
    }

    fun getDurationText(): String? {
        return time
    }

    fun setPreviewDescText(preview_desc: String?) {
        this.preview_desc = preview_desc
    }

    fun getPreviewDescText(): String? {
        return preview_desc
    }

    fun setRating(rating: String?) {
        this.rating = rating
    }

    fun getRating(): String? {
        return rating
    }

    companion object {
        var TAG = "AppModel"
        var DEBUG = false
    }
}