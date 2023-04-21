package com.huan.player.sdk

import android.os.Build
import android.text.TextUtils
import android.view.View
import androidx.annotation.Keep
import com.huan.player.contract.HWSDKPlayerContract
import tv.huan.hwplayer.config.HWPlayerSettingOptions
import tv.huan.hwplayer.constant.SDKPlayerType
import tv.danmaku.ijk.media.player.IMediaPlayer
import java.util.*

@Keep
class HWSDKIjkplayerViewImpl : HWSDKPlayerContract, IMediaPlayer.OnPreparedListener {
    private val TAG = "HWPlayer"
    override var isStart: Boolean = false
    override var mCurrentBufferPercentage: Int = 0
    private var setting: HWPlayerSettingOptions? = null
    var sdKijkplayer: SDKIjkplayer? = null
    private var mediaPlayer: IMediaPlayer? = null
    private var onVideoPreparedListener: HWSDKPlayerContract.Companion.OnVideoPreparedListener? =
        null
    private var onInfoListener: HWSDKPlayerContract.Companion.OnInfoListener? = null
    private var onVideoSizeChangedListener: HWSDKPlayerContract.Companion.OnVideoSizeChangedListener? =
        null
    private var onCompletionListener: HWSDKPlayerContract.Companion.OnCompletionListener? = null
    private var onErrorListener: HWSDKPlayerContract.Companion.OnErrorListener? = null
    private var onSeekCompleteListener: HWSDKPlayerContract.Companion.OnSeekCompleteListener? = null
    private var progress: Long = 0
    private var backCdn: HWSDKPlayerContract.Companion.OnTsCdnFetchListener? = null
    private var backLogo: ((isShow: Boolean) -> Unit)? = null
    private var videoPrepared = false
    private var timeDifference: Long = 0 //时差
    private var playerTime: Long = 0 // 实际播放时长

    init {
        setting = HWPlayerSettingOptions.INSTANCE
        if (setting?.viewPlayerType == SDKPlayerType.VIEW_TYPE_TEXTURE
            && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
        ) {
            sdKijkplayer = HWTextureView()
        } else {
            sdKijkplayer = HWSurfaceView()
        }
        mediaPlayer = sdKijkplayer?.mediaPlayer
    }

    override fun isInitSDK(): Boolean {
        return sdKijkplayer?.isInitSDK!!

    }

    override fun getPlayerView(): View? {
        return sdKijkplayer?.getPalyerView()
    }


    override fun setDataSource(isStart: Boolean?, uri: String?, progress: Long?) {
        if (isStart == null || TextUtils.isEmpty(uri) || progress == null)
            return
        this.isStart = isStart!!
        this.progress = progress!!
        dataAsync(uri)
    }

    override fun setDataSource(
        isStart: Boolean?,
        cookie: String?, cid: String?, vid: String?, definition: String?,
        progress: Long?, var7: Long?
    ) {
        if (isStart == null || TextUtils.isEmpty(vid) || progress == null || TextUtils.isEmpty(
                cookie
            )
        )
            return
        this.isStart = isStart!!
        this.progress = progress!!

        dataAsync(cookie)
    }


    private fun dataAsync(uri: String?) {
        videoPrepared = false
        resetPlayerTime()
        stopPlayer()
//        if (mediaPlayer is AndroidMediaPlayer) {
//            LogUtils.dTag(TAG, "重新创建播放器对象>>>>>")
//            mediaPlayer?.release()
//            mediaPlayer = sdKijkplayer?.onCreatePlayer()
//        } else {
//            mediaPlayer?.reset()
//        }
        mediaPlayer?.reset()
        initSDKListener()
        mediaPlayer?.setDataSource(uri)
        sdKijkplayer?.setIjkPlayerOptions()
        mediaPlayer?.prepareAsync()
    }

    override fun release() {
        videoPrepared = false
        mediaPlayer?.stop()
        mediaPlayer?.release()
        sdKijkplayer?.onDestroy()
        mediaPlayer = null
        sdKijkplayer = null
        onVideoPreparedListener = null
        onInfoListener = null
        onVideoSizeChangedListener = null
        onCompletionListener = null
        onErrorListener = null
        onSeekCompleteListener = null
        backCdn = null
        backLogo = null
        setting = null
    }

    override fun startPlayer() {
        if (!isVideoPrepared() || isPlaying()) return
        mediaPlayer?.start()
        timeDifference = Calendar.getInstance().timeInMillis
    }

    override fun pausePlayer() {
        if (!isVideoPrepared() || !isPlaying()) return
        mediaPlayer?.pause()
        appendTime()
    }

    override fun switchDefinition(def: String?) {

    }

    override fun stopPlayer() {
        if (!isVideoPrepared()) return
        mediaPlayer?.stop()
    }

    override fun getPlayerTime(): Long {
        if (isPlaying())
            playerTime = Calendar.getInstance().timeInMillis - timeDifference + playerTime
        return playerTime;
    }

    override fun getDuration(): Long {
        if (!isVideoPrepared() || mediaPlayer?.duration == null)
            return 0
        return mediaPlayer?.duration as Long
    }

    override fun getCurrentPosition(): Long {
        if (!isVideoPrepared() || mediaPlayer?.currentPosition == null)
            return 0L
        return mediaPlayer?.currentPosition as Long
    }


    override fun getCurrentBufferPosition(): Int {
        if (mediaPlayer != null)
            return mCurrentBufferPercentage * (getDuration().toInt() / 100)
        return 0
    }

    override fun getCurrentBufferPercentage(): Int {
        return mCurrentBufferPercentage
    }

    override fun seekTo(i: Int) {
        if (!isVideoPrepared()) return
        mediaPlayer?.seekTo(i.toLong())
    }

    override fun isPlaying(): Boolean {
        if (mediaPlayer == null || !isVideoPrepared()) return false
        return mediaPlayer!!.isPlaying
    }

    override fun isVideoPrepared(): Boolean {
        return videoPrepared
    }

    override fun isPlayingAD(): Boolean {
        return false
    }

    override fun setAspectRatio(aspectRatio: Int) {
        HWPlayerSettingOptions.INSTANCE.setVideoDisplayType(aspectRatio)
        getPlayerView()?.requestLayout()
    }


    override fun getVideoHeight(): Int {
        return sdKijkplayer?.palyerView?.height ?: 0
    }

    override fun getVideoWidth(): Int {
        return sdKijkplayer?.palyerView?.width ?: 0
    }

    override fun setOnVideoPreparedListener(onVideoPreparedListener: HWSDKPlayerContract.Companion.OnVideoPreparedListener?) {
        this.onVideoPreparedListener = onVideoPreparedListener
    }

    override fun setOnCompletionListener(onCompletionListener: HWSDKPlayerContract.Companion.OnCompletionListener?) {
        this.onCompletionListener = onCompletionListener
    }

    override fun setOnErrorListener(onErrorListener: HWSDKPlayerContract.Companion.OnErrorListener?) {
        this.onErrorListener = onErrorListener;
    }

    override fun setOnInfoListener(onInfoListener: HWSDKPlayerContract.Companion.OnInfoListener?) {
        this.onInfoListener = onInfoListener
    }

    override fun setOnVideoSizeChangedListener(onVideoSizeChangedListener: HWSDKPlayerContract.Companion.OnVideoSizeChangedListener?) {
        this.onVideoSizeChangedListener = onVideoSizeChangedListener
    }

    override fun setOnSeekCompleteListener(onSeekCompleteListener: HWSDKPlayerContract.Companion.OnSeekCompleteListener?) {
        this.onSeekCompleteListener = onSeekCompleteListener
    }

    override fun setOnTsCdnFetchListener(backCdn: HWSDKPlayerContract.Companion.OnTsCdnFetchListener?) {
        this.backCdn = backCdn
    }

    /**
     * 兼容腾讯播放器
     */
    override fun setOnLogoPositionListener(back: (isShow: Boolean) -> Unit) {
        backLogo = back
    }

    override fun setOnAdListener(onAdListener: HWSDKPlayerContract.Companion.OnAdListener) {
    }

    override fun getOriginalMediaObject(): Any? {
        return mediaPlayer
    }


    /*-------------以下都是 IMediaPlayer 重写的方法--------------------*/

    fun initSDKListener() {
        mediaPlayer?.setOnPreparedListener(this)

        mediaPlayer?.setOnCompletionListener {
            appendTime()
            onCompletionListener?.onCompletion(this)
        }

        mediaPlayer?.setOnVideoSizeChangedListener { iMediaPlayer, i, i2, i3, i4 ->
            sdKijkplayer?.setVideoSize(
                iMediaPlayer.videoWidth,
                iMediaPlayer.videoHeight,
                iMediaPlayer.videoSarNum,
                iMediaPlayer.videoSarDen
            )
            onVideoSizeChangedListener?.onVideoSizeChanged(this, i, i2)
        }

        mediaPlayer?.setOnBufferingUpdateListener { iMediaPlayer, i ->
            mCurrentBufferPercentage = i
        }

        mediaPlayer?.setOnSeekCompleteListener {
            onSeekCompleteListener?.onSeekComplete(this)
        }

        mediaPlayer?.setOnInfoListener { iMediaPlayer, i, i2 ->
            onInfoListener?.onInfo(this, i, i2)
            false
        }

        mediaPlayer?.setOnErrorListener { iMediaPlayer, i, i2 ->
            videoPrepared = false
            onErrorListener?.onError(
                this, HWSDKPlayerContract.ERROR_UNKNOWN,
                i2,
                "#$i,$i2"
            )
            false
        }
    }


    //资源准备完成
    override fun onPrepared(p0: IMediaPlayer?) {
//        if (sdKijkplayer?.palyerView is TextureView && NullUtis.null2False(sdKijkplayer?.isSurfaceCreated)) {
//            mediaPlayer?.setSurface(sdKijkplayer?.surface)
//        } else if (NullUtis.null2False(sdKijkplayer?.isSurfaceCreated)) {
//            mediaPlayer?.setDisplay(sdKijkplayer?.surfaceHolder)
//        }
        videoPrepared = true
        onVideoPreparedListener?.onVideoPrepared(this)
        if (isStart) {
            if (backLogo != null) {
                backLogo?.invoke(false)
            }
            if (progress.toInt() != 0)
                seekTo(progress.toInt())
            mediaPlayer?.start()  //必须在同步完数据才能播放
        }

    }


    /**
     * 使用Cdn地址播放,这个后期有需要可以扩展
     */
    private fun isOpenCdn(): Boolean {
        if (HWPlayerSettingOptions.INSTANCE.isCdnOpen) {
            val uri = backCdn?.onCdn()
            dataAsync(uri)
            return true
        }
        return false
    }


    /**
     * 累计播放时间
     */
    private fun appendTime() {
        playerTime = Calendar.getInstance().timeInMillis - timeDifference + playerTime
        timeDifference = Calendar.getInstance().timeInMillis
    }

    //重置播放总时长
    private fun resetPlayerTime() {
        this.playerTime = 0
        timeDifference = Calendar.getInstance().timeInMillis
    }

}