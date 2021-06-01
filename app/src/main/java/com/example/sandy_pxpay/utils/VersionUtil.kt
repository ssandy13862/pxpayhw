package com.example.sandy_pxpay.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.util.Log

object VersionUtil {

    fun getOSAppVersion(context: Context): String {
        val info: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val osVersion = android.os.Build.VERSION.RELEASE
        Log.d("Log111", info.versionName)
        Log.d("Log222", android.os.Build.VERSION.RELEASE)
        return "${osVersion}_${info.versionName}"
    }
}