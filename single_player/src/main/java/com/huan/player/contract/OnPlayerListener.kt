package com.huan.player.contract

import com.huan.player.ui.PlayerDataHelp

interface OnPlayerListener {

    fun onVideoPrepared(playerDataHelp: PlayerDataHelp?, mediaPlayer: PlayerViewContract?)

    fun onOpenPlayer(playerDataHelp: PlayerDataHelp?)

    fun onPlayerBuffer(isStrat: Boolean, netSpeedRate: Long)

    fun onPlayerError(errorCode: Int, s: String?)

    fun onCompletion(playRecord: PlayerDataHelp?)

    fun onSeekTo(isEnd: Boolean, isEnter: Boolean)

    fun onPlayerPause()

    fun onPlayerStart()

    fun onPlayerUpdateEpisode(playRecord: PlayerDataHelp?)

    fun onExitPlayer(playRecord: PlayerDataHelp?)

    /**
     *播放记录上报
     * @param endFlag 0 没有播放完，1 播放完了
     */
    fun onPlayerRecord(mediaPlayer: PlayerViewContract?, playRecord: PlayerDataHelp?, endFlag: Int)

    fun setThirdPartyPlayerUrlListener(
        playerDataHelp: PlayerDataHelp?,
        success: (uri: String?) -> Unit?,
        error: () -> Unit?
    )

    fun onDestroy()
}