package com.example.androidtv.search

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.ArrayList

/**
 * Created by Dipak Kumar Mehta on 10/26/2021.
 */
class AppDataManage(context: Context) {
    var icon: Drawable? = null
    var title: String? = null
    fun getFeaturedApp(): ArrayList<AppModel> {
        if (DEBUG) Log.d(TAG, "getFeaturedApp() : +", Throwable("AppDataManage"))
        return sFeaturedApp
    }

    fun getGoogleApp(): ArrayList<AppModel> {
        if (DEBUG) Log.d(TAG, "getGoogleApp() : +", Throwable("AppDataManage"))
        return sGoogleApp
    }

    fun getRecentApp(): ArrayList<AppModel> {
        if (DEBUG) Log.d(TAG, "getFeaturedApp() : +", Throwable("AppDataManage"))
        return sRecentApp
    }

    companion object {
        var TAG = "AppDataManage"
        var DEBUG = false
        var sLocalArrayList = ArrayList<AppModel>()
        var sFeaturedApp = ArrayList<AppModel>()
        var sGoogleApp = ArrayList<AppModel>()
        var sRecentApp = ArrayList<AppModel>()
        private lateinit var sContext: Context

        @get:RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
        val launchAppList: ArrayList<AppModel>
            get() {
                if (DEBUG) Log.d(TAG, "getLaunchAppList() : +", Throwable("AppDataManage"))
                val localPackageManager = sContext.packageManager
                val localIntent = Intent("android.intent.action.MAIN")
                localIntent.addCategory("android.intent.category.LEANBACK_LAUNCHER")
                val localList = localPackageManager.queryIntentActivities(localIntent, 0)
                var localIterator: Iterator<ResolveInfo>? = null
                sLocalArrayList = ArrayList()
                sFeaturedApp = ArrayList()
                sGoogleApp = ArrayList()
                sRecentApp = ArrayList()
                if (localList.size != 0) {
                    localIterator = localList.iterator()
                }
                var isFirstPosition = true
                while (true) {
                    if (!localIterator!!.hasNext()) break
                    val localResolveInfo = localIterator.next()
                    val InsApp = AppModel()
                    InsApp.setBanner(localResolveInfo.activityInfo.loadBanner(localPackageManager))
                    InsApp.setName(localResolveInfo.activityInfo.loadLabel(localPackageManager).toString())
                    InsApp.setPackageName(localResolveInfo.activityInfo.packageName)
                    InsApp.setPackageManger(localPackageManager)
                    InsApp.setDataDir(localResolveInfo.activityInfo.applicationInfo.publicSourceDir)
                    InsApp.setLauncherName(localResolveInfo.activityInfo.name)
                    InsApp.setLaunchColor(InsApp.getLaunchColor())
                    InsApp.setDesc(localResolveInfo.activityInfo.applicationInfo.loadDescription(localPackageManager).toString())
                    val pkgName = localResolveInfo.activityInfo.packageName
                    var mPackageInfo: PackageInfo
                    try {
                        mPackageInfo = sContext.packageManager.getPackageInfo(pkgName, 0)
                        if ((mPackageInfo.applicationInfo.flags
                                    and ApplicationInfo.FLAG_SYSTEM) > 0
                        ) {
                            InsApp.setSysApp(true)
                        }
                    } catch (e: PackageManager.NameNotFoundException) {
                        e.printStackTrace()
                    }
                    if (!InsApp.getPackageName().equals("com.mstar.launcher")) {
                        run {
                            if (pkgName.startsWith("com.ted.android.tv") ||
                                pkgName.startsWith("com.onemainstream.youngholly.android")
                                || pkgName.startsWith("com.onemainstream.smithsonia.android")
                                || pkgName.startsWith("com.netflix.ninja")
                                || pkgName.startsWith("com.onemainstream.smithearth.android")
                                || pkgName.startsWith("com.graymatrix.did")
                            ) {
                                if (!isFirstPosition) {
                                    InsApp.setFirstPosition(isFirstPosition)
                                } else {
                                    InsApp.setFirstPosition(isFirstPosition)
                                    isFirstPosition = false
                                }
                                sFeaturedApp.add(InsApp)
                            } else if (pkgName.startsWith("com.android.vending")
                                || pkgName.contains("com.google.android")
                            ) {
                                sGoogleApp.add(InsApp)
                            }
                            sLocalArrayList.add(InsApp)
                        }
                    }
                    InsApp.setIcon(localResolveInfo.activityInfo.loadIcon(localPackageManager))
                    InsApp.setLaunchColor(InsApp.getLaunchColor())
                }
                return sLocalArrayList
            }
    }

    init {
        if (DEBUG) Log.d(TAG, "AppDataManage() : +", Throwable("AppDataManage"))
        sContext = context
    }
}