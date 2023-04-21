package com.huan.player.ui

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.huan.player.contract.ControllerViewContract


/**
 * 更新进度条的handler
 */
class HandlerProgress : Handler {
    companion object {
        val HANDLE_START_UPDATE_PROGRESS = 110  //更新进度条的what
        val HANDLE_START_UPDATE_TIME = 1000L // 多长时间更新一次进度条
        val HANDLE_START_SEEK_TO = 120 //快进快退的what

    }

    private var controlle: ControllerViewContract? = null

    constructor(controllerView: ControllerViewContract) : super(Looper.myLooper()!!) {
        controlle = controllerView
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        when (msg.what) {
            HANDLE_START_UPDATE_PROGRESS -> {
                controlle?.handleUpdateProgress()
                sendEmptyMessageDelayed(HANDLE_START_UPDATE_PROGRESS, 1000)
            }
            HANDLE_START_SEEK_TO -> {
                controlle?.handleSeekTo()
            }
        }
    }

    fun onDestroy() {
        removeMessages(HANDLE_START_UPDATE_PROGRESS)
        removeMessages(HANDLE_START_SEEK_TO)
        removeCallbacksAndMessages(null)
        controlle = null
    }
}