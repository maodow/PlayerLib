package com.huan.player.sdk


import android.view.SurfaceHolder
import android.view.View
import com.huan.player.constant.Utils


class HWSurfaceView : SDKIjkplayer(), SurfaceHolder.Callback {
    private var surfaceView: MySurfaceView? = null
    private var surfaceCreated: Boolean = false

    init {
        surfaceView = MySurfaceView(Utils.getApp())
        // 播放视频时，SurfaceView区域视觉上会黑屏一会儿，可以用
        surfaceView?.setZOrderOnTop(true)
        //悬浮最上层后可能会遮挡其他的View，可以用
        surfaceView?.setZOrderMediaOverlay(true)

        var holder = surfaceView?.holder
        holder?.addCallback(this)

    }

    override fun setVideoSize(
        currentVideoWidth: Int,
        currentVideoHeight: Int,
        videoSarNum: Int,
        videoSarDen: Int
    ) {
        surfaceView?.setVideoSize(currentVideoWidth, currentVideoHeight, videoSarNum, videoSarDen)
    }


    override fun isSurfaceCreated(): Boolean {
        return surfaceCreated
    }

    override fun getPalyerView(): View? {
        return surfaceView
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        surfaceCreated = false
        mediaPlayer.setDisplay(null)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        surfaceCreated = true
        surfaceHolder = p0
        mediaPlayer.setDisplay(p0)
    }


}