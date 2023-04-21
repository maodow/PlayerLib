package com.huan.player.contract

import android.view.View
import androidx.annotation.Keep

/**
 * 统一播放器对象的 API的方法（tengxun，huanwang）
 */
@Keep
interface HWSDKPlayerContract {
    var isStart: Boolean
    var mCurrentBufferPercentage: Int

    fun isInitSDK(): Boolean  //判断SDK 是否初始化成功

    fun getPlayerView(): View?  //返回视频播放器视图的对象

    fun setDataSource(isStart: Boolean?, uri: String?, progress: Long?)

    fun setDataSource(
        isStart: Boolean?,
        cookie: String?,
        cid: String?,
        vid: String?,
        definition: String?,
        progress: Long?,
        var7: Long?
    )

    fun release()

    fun startPlayer();

    fun pausePlayer()

    fun switchDefinition(def: String?) // 设置清晰度

    fun stopPlayer()

    fun getDuration(): Long //播放时长

    fun getCurrentPosition(): Long //当前播放时长

    fun getCurrentBufferPercentage(): Int //缓冲百分比

    fun getCurrentBufferPosition(): Int //缓冲进度

    fun seekTo(i: Int)  // 设置快进快退度

    fun isPlaying(): Boolean  //获取播放转态

    fun isVideoPrepared(): Boolean //资源是否准备完成

    fun isPlayingAD(): Boolean // 广告是否显示

    fun setAspectRatio(aspectRatio: Int) //设置画面比例

    //视频开始加载的监听
    // fun setHW_OnVideoPreparingListener(onVideoPreparingListener: OnVideoPreparingListener)

    //设置视频加载完成的回调接口
    fun setOnVideoPreparedListener(onVideoPreparedListener: OnVideoPreparedListener?)

    //视频播放完成回调接口
    fun setOnCompletionListener(onCompletionListener: OnCompletionListener?)

    //播放错误的回调
    fun setOnErrorListener(onErrorListener: OnErrorListener?)

    //视屏的监听信息 包括缓冲信息等
    fun setOnInfoListener(onInfoListener: OnInfoListener?)

    //视屏大小改变监听
    fun setOnVideoSizeChangedListener(onVideoSizeChangedListener: OnVideoSizeChangedListener?)

    //快进完成
    fun setOnSeekCompleteListener(onSeekCompleteListener: OnSeekCompleteListener?)

    //cdn 回调地址
    fun setOnTsCdnFetchListener(backCdn: OnTsCdnFetchListener?)

    fun setOnLogoPositionListener(back: (isShow: Boolean) -> Unit)

    fun setOnAdListener(onAdListener: OnAdListener)

    //原始的播放器对象
    fun getOriginalMediaObject(): Any?

    // 返回播放时长，不包含快进快退
    fun getPlayerTime(): Long


    fun getVideoHeight(): Int

    fun getVideoWidth(): Int


    companion object {
        const val VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER = 0
        const val VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT = 1  // 填满父布局
        const val VIDEO_16_9_TYPE_ADAPTER = 4  // 16:9
        const val VIDEO_4_3_TYPE_ADAPTER = 5  // 4:3


        val MEDIA_INFO_BUFFERING_START: Int
            get() = 701
        val MEDIA_INFO_BUFFERING_END: Int
            get() = 702
        val MEDIA_INFO_VIDEO_RENDERING_START: Int //视屏渲染开始
            get() = 3

        val ERROR_VIP_PAY: Int  //需要付费
            get() = -3000

        //ERROR_VIP_PAY_EXPIRE, //会员到期 expire
        val ERROR_UNKNOWN: Int //未知的错误
            get() = -3001

        interface OnVideoPreparedListener {
            fun onVideoPrepared(hWSDKPlayerContract: HWSDKPlayerContract?)
        }

        interface OnCompletionListener {
            fun onCompletion(hWSDKPlayerContract: HWSDKPlayerContract?)
        }

        interface OnErrorListener {
            fun onError(
                hWSDKPlayerContract: HWSDKPlayerContract?,
                errorStatus: Int,
                errorCode: Int,
                s: String?
            )
        }

        interface OnInfoListener {
            fun onInfo(hWSDKPlayerContract: HWSDKPlayerContract?, what: Int, extra: Any?)
        }

        interface OnVideoSizeChangedListener {
            fun onVideoSizeChanged(
                hWSDKPlayerContract: HWSDKPlayerContract?,
                width: Int,
                height: Int
            )
        }

        interface OnSeekCompleteListener {
            fun onSeekComplete(hWSDKPlayerContract: HWSDKPlayerContract?)
        }

        interface OnTsCdnFetchListener {
            fun onCdn(): String
        }


        interface OnAdListener {

            /**
             * 广告资源开始准备
             * @param hWSDKPlayerContract 播放器对象
             */
            fun onPreparing(hWSDKPlayerContract: HWSDKPlayerContract?, type: Int)

            /**
             * 广告资源加载完成
             * @param hWSDKPlayerContract 播放器对象
             * @param type 0 前切片广告 ，1 中切片广告， 2 后切片广告
             */
            fun onPrepared(hWSDKPlayerContract: HWSDKPlayerContract?, type: Int, adDuration: Long)
        }

    }

}