package com.huan.player.contract


import android.view.KeyEvent
import com.huan.player.bean.NetVideoInfo
import com.huan.player.ui.PlayerDataHelp

interface VideoEpisodeChoiceViewContract :
    BasePlayerViewContract {

    fun setData(playerDataHelp: PlayerDataHelp?, mediaPlayer: PlayerViewContract?)

    fun onVideoPrepared(
        playerView: PlayerViewContract?,
        playerDataHelp: PlayerDataHelp?
    )

    fun setDefnInfo(videoInfo: NetVideoInfo?)

    fun setPlayerReport(onClick: () -> Unit)

    fun onUpdateEpisodeListener()

    fun setNextEpisodeListener(next: () -> Unit)

    fun dispatchKeyEvent(event: KeyEvent?): Boolean

}