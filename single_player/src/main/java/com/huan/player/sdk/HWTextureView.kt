package com.huan.player.sdk

import android.graphics.SurfaceTexture
import android.view.Surface
import android.view.TextureView
import android.view.View
import com.huan.player.constant.Utils

class HWTextureView : SDKIjkplayer(), TextureView.SurfaceTextureListener {
    private lateinit var textureView: MyTextureView
    private var surfaceTexture: SurfaceTexture? = null
    private var surfaceCreated: Boolean = false

    init {
        textureView = MyTextureView(Utils.getApp()!!)
        textureView.surfaceTextureListener = this
    }


    override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {

    }

    override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
        surfaceCreated =false
        return false
    }

    override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {

    }

    override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
        surfaceCreated = true
        if (surface == null || surfaceTexture == null) {
            surfaceTexture = p0;
            surface = Surface(p0)
            mediaPlayer.setSurface(surface)
        } else {
            textureView.setSurfaceTexture(surfaceTexture!!)
        }
    }

    override fun isSurfaceCreated(): Boolean {
        return surfaceCreated
    }


    override fun getPalyerView(): View {
        return textureView
    }

    override fun setVideoSize(
        currentVideoWidth: Int,
        currentVideoHeight: Int,
        videoSarNum: Int,
        videoSarDen: Int
    ) {
        textureView.setVideoSize(currentVideoWidth,currentVideoHeight,videoSarNum,videoSarDen)
    }


}