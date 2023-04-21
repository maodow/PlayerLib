package com.huan.player.contract

import android.view.KeyEvent
import com.huan.player.bean.NetVideoInfo
import com.huan.player.ui.PlayerDataHelp

interface ControllerViewContract :
    BasePlayerViewContract {
    fun handleUpdateProgress()

    fun handleSeekTo()

    fun setProgressUpdateListener(progressUpdate: (progress: Int, bufferProgress: Int) -> Unit)

    fun setSeekToListener(seekToListener: (isEnd: Boolean, isEnter: Boolean) -> Unit)

    fun setFilmTryAndSeeEndListener(filmTryAndSeeEnd: () -> Unit)

    fun onVideoPrepared(
        videoEpsode: VideoEpisodeChoiceViewContract?,
        playerView: PlayerViewContract?,
        playerDataHelp: PlayerDataHelp?
    )

    fun onNetVideoInfoListener(
        videoInfo: NetVideoInfo?
    )

    fun isFilmTryAndSeeStatus(): Boolean

    fun getSeekBarProgress(): Int

    fun onCompletion()

    fun onSeekComplete()

    fun onControllerKeyDown(keyCode: Int, event: KeyEvent?): Boolean

    fun refreshPayStatus()

    fun getMax(): Int

    fun setSkipTheEndListener(skipEndNext: () -> Unit)
}