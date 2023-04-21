package com.huan.player.contract

import com.huan.player.ui.PlayerDataHelp

/**
 * 播放器view视图外部可用的方法
 */
interface PlayerViewContract {

    fun setDataSource(playerDataHelp: PlayerDataHelp?)

    fun setStartDataSource(playerDataHelp: PlayerDataHelp?)

    fun setDataUri(uri: String, playerType: Int)

    fun setStartDataUri(uri: String, playerType: Int)

    fun getPlayerDataHelp(): PlayerDataHelp?

    fun startPlayer()

    fun stopPlayer()

    fun pausePlayer()

    fun release()

    //播放器的进入时间
    fun getEnterTheTime(): Long

    // 返回播放时长，不包含快进快退
    fun getPlayerTime(): Long

    fun getDuration(): Long

    fun getCurrentPosition(): Long

    fun getCurrentBufferPosition(): Int

    fun seekTo(i: Int)

    fun switchDefinition(defn: String?)

    fun setAspectRatio(aspectRatio: Int) //设置画面比例

    fun isFullscreen(): Boolean

    fun isPlayerWindowType(): Boolean

    fun gotoScreenFullscreen()

    fun gotoScreenNormal()

    fun setLoopPlayer(islooped: Boolean)

    fun isLoopPlayer(): Boolean

    fun isPlaying(): Boolean

    fun isVideoPrepared(): Boolean

    fun exitPalyer()

    fun getVideoHeight(): Int

    fun getVideoWidth(): Int

}