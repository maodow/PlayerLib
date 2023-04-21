package com.huan.player.constant

import android.util.Log

object Logcat {
    var debug = true
    const val TAG = "HWPlayer"

    fun iTag(i: String?) {
        if (debug)
            Log.i(TAG, i ?: "")
    }

    fun dTag(d: String?) {
        if (debug)
            Log.d(TAG, d ?: "")
    }

    fun eTag(e: String?) {
        if (debug)
            Log.d(TAG, e ?: "")
    }
}