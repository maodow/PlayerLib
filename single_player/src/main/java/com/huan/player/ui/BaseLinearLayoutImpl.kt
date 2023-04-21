package com.huan.player.ui


import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import com.huan.player.config.PlayerSettingsShare
import com.huan.player.constant.PlayerScreenStatus
import com.huan.player.constant.Utils
import tv.huan.hwplayer.config.HWPlayerSettingOptions
import java.util.*

abstract class BaseLinearLayoutImpl : BaseLinearLayout {

    protected var enterTime: Long = 0   //进入播放器时间
    private var timeDifference: Long = 0 //时差
    private var playerTime: Long = 0 // 实际播放时长
    private var islooped: Boolean = false  //是否开启循环播放
    private var isEndFlag: Boolean = false

    protected var endFlag = 0  // 0 没有播放完，1 播放完了

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

    }


    override fun setDataUri(uri: String, playerType: Int) {
        resetPlayerTime()
        addData(false, uri)
    }

    override fun setStartDataUri(uri: String, playerType: Int) {
        resetPlayerTime()
        addData(true, uri)
    }

    // 设置配置
    override fun setDataSource(playerDataHelp: PlayerDataHelp?) {
        resetPlayerTime()
        addData(false, playerDataHelp)
    }

    override fun setStartDataSource(playerDataHelp: PlayerDataHelp?) {
        resetPlayerTime()
        enterTime = getCurrentTimeMillis()
        addData(true, playerDataHelp)
    }

    override fun getPlayerDataHelp(): PlayerDataHelp? = playerData

    /**
     * 判断是否是全屏状态
     * @return true : 全屏 ；false ：原始状态
     */
    override fun isFullscreen(): Boolean {
        return screenStatus == PlayerScreenStatus.FULLSCREEN ?: false
    }

    /**
     *判断view的窗口模式
     * @return true 窗口类型，false 不是窗口类型
     */
    override fun isPlayerWindowType(): Boolean {
        return playerWindowType == PlayerScreenStatus.WINDOW_VIEW ?: false
    }

    //进入全屏
    override fun gotoScreenFullscreen() {
        if (enterTime == 0L)
            enterTime = getCurrentTimeMillis()
        resetPlayerTime()
        goScreenFullscreen()
        keepScreenOn = true //设置屏幕常亮
        playerData?.onPlayerListener?.onOpenPlayer(getPlayerDataHelp())
    }

    //退出全屏
    override fun gotoScreenNormal() {
        keepScreenOn = false
        goScreenNormal()
    }

    override fun switchDefinition(defn: String?) {
        mediaPlayer?.switchDefinition(defn)
    }

    override fun startPlayer() {
        if (!isVideoPrepared() || isPlaying()) {
            if (Utils.null2False(mediaPlayer?.isPlayingAD()))
                mediaPlayer?.startPlayer()
            return
        }
        mediaPlayer?.startPlayer()
        timeDifference = getCurrentTimeMillis()
        playerData?.onPlayerListener?.onPlayerStart()
    }

    override fun stopPlayer() {
        if (!isVideoPrepared()) return
        mediaPlayer?.stopPlayer()
    }

    override fun pausePlayer() {
        if (!isVideoPrepared() || !isPlaying()) {
            if (Utils.null2False(mediaPlayer?.isPlayingAD()))
                mediaPlayer?.pausePlayer()
            return
        }
        mediaPlayer?.pausePlayer()
        val currentDate = getCurrentTimeMillis()
        if (currentDate != 0L && timeDifference != 0L)
            playerTime = currentDate - timeDifference + playerTime
        timeDifference = currentDate
        playerData?.onPlayerListener?.onPlayerPause()
    }

    override fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
        fullScreenDialog = null
    }

    override fun getDuration(): Long {
        return Utils.nullToLong(mediaPlayer?.getDuration())
    }

    override fun getCurrentPosition(): Long {
        return Utils.nullToLong(mediaPlayer?.getCurrentPosition())
    }

    override fun getCurrentBufferPosition(): Int {
        return Utils.nullToInt(mediaPlayer?.getCurrentBufferPosition())
    }

    override fun seekTo(i: Int) {
        mediaPlayer?.seekTo(i)
    }

    override fun setAspectRatio(aspectRatio: Int) {
        mediaPlayer?.setAspectRatio(aspectRatio)
    }

    override fun setLoopPlayer(islooped: Boolean) {
        this.islooped = islooped
    }

    override fun isPlaying(): Boolean {
        return Utils.null2False(mediaPlayer?.isPlaying())
    }

    override fun isLoopPlayer(): Boolean {
        return islooped
    }

    override fun isVideoPrepared(): Boolean {
        return Utils.null2False(mediaPlayer?.isVideoPrepared())
    }

    override fun getVideoHeight(): Int {
        return Utils.nullToInt(mediaPlayer?.getVideoHeight())
    }

    override fun getVideoWidth(): Int {
        return Utils.nullToInt(mediaPlayer?.getVideoWidth())
    }

    override fun exitPalyer() {

    }

    override fun getEnterTheTime(): Long {
        return enterTime
    }

    //获取实际播放时间
    override fun getPlayerTime(): Long {
        val currentDate = getCurrentTimeMillis()
        if (isPlaying() || (endFlag == 1 && !isEndFlag)) {
            if (currentDate != 0L && timeDifference != 0L)
                playerTime = currentDate - timeDifference + playerTime
            timeDifference = currentDate
            if (endFlag == 1) //防止多次调用出现假数据
                isEndFlag = true
        }
        if (playerTime < 0 || playerTime >= currentDate)
            playerTime = 0
        return playerTime
    }

    //重置播放总时长
    protected fun resetPlayerTime() {
        endFlag = 0
        isEndFlag = false
        this.playerTime = 0
        timeDifference = getCurrentTimeMillis()
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (onDispatchPlayerKeyEvent(event) && mediaPlayer != null)
            return true
        return super.dispatchKeyEvent(event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (onPlayerKeyDown(event!!.keyCode, event) && mediaPlayer != null)
            return true
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 获取系统当前时间
     */
    private fun getCurrentTimeMillis(): Long {
        var currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis == 0L) {
            currentTimeMillis = Date().time
        }
        return currentTimeMillis
    }


}