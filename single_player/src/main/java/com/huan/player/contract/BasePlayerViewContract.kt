package com.huan.player.contract

import android.content.Context
import android.view.ViewGroup
import com.huan.player.ui.HandlerProgress

interface BasePlayerViewContract {

    fun onCreate(context: Context, viewGroup: ViewGroup, handler: HandlerProgress? = null)

    fun hide(tag: Int? = 0)

    fun show(tag: String? = "0")

    fun onDestroy()

    fun isShown(): Boolean
}