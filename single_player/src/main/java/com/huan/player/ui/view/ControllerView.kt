package com.huan.player.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.ViewGroup
import android.widget.LinearLayout
import com.huan.player.bean.NetVideoInfo
import com.huan.player.contract.ControllerViewContract
import com.huan.player.contract.PlayerViewContract
import com.huan.player.contract.VideoEpisodeChoiceViewContract
import com.huan.player.ui.HandlerProgress
import com.huan.player.ui.HandlerProgress.Companion.HANDLE_START_UPDATE_PROGRESS
import com.huan.player.ui.HandlerProgress.Companion.HANDLE_START_UPDATE_TIME
import com.huan.player.ui.PlayerDataHelp
import tv.huan.hwplayer.config.HWPlayerSettingOptions
import tv.huan.hwsystemsdk.player.presenter.ControllerViewPresenter

/**
 * MediaMetadataRetriever 获取视频帧图
 */
class ControllerView : LinearLayout,
    ControllerViewContract {
    private var handler: HandlerProgress? = null
    private var presenter: ControllerViewContract? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        onCreate(context, this)
    }

    override fun onCreate(context: Context, viewGroup: ViewGroup, handler: HandlerProgress?) {
        this.handler =
            HandlerProgress(this) //更新进度条的handler
        presenter = HWPlayerSettingOptions.INSTANCE.controllerView
        if (presenter == null) {
            presenter =
                ControllerViewPresenter()
        }

        presenter?.onCreate(context, viewGroup, this.handler)
    }

    override fun handleUpdateProgress() {
        presenter?.handleUpdateProgress()
    }

    override fun handleSeekTo() {
        presenter?.handleSeekTo()
    }

    /**
     *  进度条的定时任务
     */
    override fun setProgressUpdateListener(progressUpdate: (progress: Int, bufferProgress: Int) -> Unit) {
        presenter?.setProgressUpdateListener(progressUpdate)
    }

    /**
     * 快进监听
     */
    override fun setSeekToListener(seekToListener: (isEnd: Boolean, isEnter: Boolean) -> Unit) {
        presenter?.setSeekToListener(seekToListener)
    }


    /**
     * 电影试看结束监听
     */
    override fun setFilmTryAndSeeEndListener(filmTryAndSeeEnd: () -> Unit) {
        presenter?.setFilmTryAndSeeEndListener(filmTryAndSeeEnd)
    }


    override fun hide(tag: Int?) {
        presenter?.hide(tag)
    }

    override fun show(tag: String?) {
        presenter?.show(tag)
    }

    /**
     *  视屏资源初始化成功
     */
    override fun onVideoPrepared(
        videoEpsode: VideoEpisodeChoiceViewContract?,
        playerView: PlayerViewContract?,
        playerDataHelp: PlayerDataHelp?
    ) {
        presenter?.onVideoPrepared(videoEpsode, playerView, playerDataHelp)
        handler?.sendEmptyMessageDelayed(
            HANDLE_START_UPDATE_PROGRESS,
            HANDLE_START_UPDATE_TIME
        )
    }

    override fun onNetVideoInfoListener(videoInfo: NetVideoInfo?) {
        presenter?.onNetVideoInfoListener(videoInfo)
    }

    /**
     * 电影是否试看结束
     * true 结束  false 未结束
     */
    override fun isFilmTryAndSeeStatus(): Boolean {
        return presenter?.isFilmTryAndSeeStatus() ?: true
    }


    override fun getSeekBarProgress(): Int {
        return presenter?.getSeekBarProgress() ?: 0
    }

    /**
     * 视屏播放完成
     * 由于视频播放完之后播放器的当前进度到不了满进度，所以做处理
     */
    override fun onCompletion() {
        handler?.removeMessages(HANDLE_START_UPDATE_PROGRESS)
        presenter?.onCompletion()
    }


    /**
     * 快进快退加载完成
     */
    override fun onSeekComplete() {
        //为了解决快进回退问题，延迟更新进度,一定要是关闭控制器时间的倍数
        handler?.sendEmptyMessageDelayed(HANDLE_START_UPDATE_PROGRESS, HANDLE_START_UPDATE_TIME * 3)
        presenter?.onSeekComplete()
    }


    override fun onControllerKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return presenter?.onControllerKeyDown(keyCode, event) ?: false
    }

    /**
     * 刷新支付状态
     */
    override fun refreshPayStatus() {
        presenter?.refreshPayStatus()
        handler?.removeMessages(HANDLE_START_UPDATE_PROGRESS)
        handler?.sendEmptyMessageDelayed(HANDLE_START_UPDATE_PROGRESS, HANDLE_START_UPDATE_TIME)
    }

    override fun getMax(): Int {
        return presenter?.getMax() ?: 0
    }

    override fun setSkipTheEndListener(skipEndNext: () -> Unit) {
        presenter?.setSkipTheEndListener(skipEndNext)
    }


    override fun onDestroy() {
        handler?.onDestroy()
        presenter?.onDestroy()
        handler = null
        presenter = null

    }

}