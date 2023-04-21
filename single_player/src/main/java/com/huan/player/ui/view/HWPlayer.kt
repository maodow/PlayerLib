package com.huan.player.ui.view

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.huan.player.R
import com.huan.player.bean.Media
import com.huan.player.constant.Logcat
import com.huan.player.constant.PlayerErrorCode
import com.huan.player.constant.Utils
import com.huan.player.contract.*
import com.huan.player.contract.HWSDKPlayerContract.Companion.MEDIA_INFO_BUFFERING_END
import com.huan.player.contract.HWSDKPlayerContract.Companion.MEDIA_INFO_BUFFERING_START
import com.huan.player.contract.HWSDKPlayerContract.Companion.MEDIA_INFO_VIDEO_RENDERING_START
import com.huan.player.ui.BaseLinearLayoutImpl
import com.huan.player.ui.PlayerDataHelp
import java.util.*

class HWPlayer : BaseLinearLayoutImpl {
    private var playerErrorDialog: PlayerErrorDialog? = null
    private var playerPoster: ImageView? = null
    private var controllerView: ControllerViewContract? = null
    private var bufferView: BufferView? = null
    private var videoEpisodeChoiceView: VideoEpisodeChoiceViewContract? = null
    private var playerSmallProgress: ProgressBar? = null
    private var detainmentView: DetainmentViewContract? = null
    private var playerError = false // 播放器是否报错了
    private var exitPlayer: ((isPlaying: Boolean) -> Unit)? = null
    private var firstClick = 0L
    private var playerLogo: ImageView? = null
    private var isStartPay = false  //防止重复跳转支付页面

    private var logoMarginTop = 40
    private var oldCurrentPosition = 0L //之前的播放记录


    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

    }

    override fun getLayoutId(): Int {
        return R.layout.sdk_hw_layout_fragmelayout
    }

    override fun onCreate(context: Context) {
        playerPoster = findViewById<ImageView>(R.id.player_poster)
        controllerView = findViewById<ControllerView>(R.id.player_controller_view)
        playerSmallProgress = findViewById<ProgressBar>(R.id.player_small_progress)
        bufferView = findViewById<BufferView>(R.id.player_buffer_view)
        videoEpisodeChoiceView =
            findViewById<VideoEpisodeChoiceView>(R.id.player_video_episode_choice_view)
        detainmentView = findViewById<DetainmentView>(R.id.player_detainment_view)
        playerLogo = findViewById<ImageView>(R.id.player_logo)

    }

    override fun onInitData(playerDataHelp: PlayerDataHelp?) {
        initData()
        playerDataHelp?.setOnVideoEpisodeChoice(onVideoEpisodeChoice)
    }

    /**
     * @see HWSDKPlayerContract
     * 视屏播放器的监听, 之所以在父类调用
     * 是因为HWPlayerContract对象的创建时机
     */
    override fun setMediaPlayerListener(mediaPlayer: HWSDKPlayerContract?) {
        val playerDataHelp = getPlayerDataHelp()


        //视频资源准备完成
        mediaPlayer?.setOnVideoPreparedListener(object :
            HWSDKPlayerContract.Companion.OnVideoPreparedListener {
            override fun onVideoPrepared(hWSDKPlayerContract: HWSDKPlayerContract?) {
                Logcat.dTag("视频加载完成=====${getPlayerDataHelp()?.currentMediaIndex}")
                oldCurrentPosition = Utils.nullToLong(getPlayerDataHelp()?.historyProgress)
                getPlayerDataHelp()?.setHistoryProgress(
                    0,
                    getPlayerDataHelp()?.currentMediaIndex,
                    0
                )
                resetPlayerTime()
                playerDataHelp?.onPlayerListener?.onVideoPrepared(playerDataHelp, this@HWPlayer)
                controllerView?.onVideoPrepared(
                    videoEpisodeChoiceView,
                    this@HWPlayer,
                    playerDataHelp
                )
                videoEpisodeChoiceView?.onVideoPrepared(this@HWPlayer, playerDataHelp)
                hideViewAll()
                playerSmallProgress?.max = hWSDKPlayerContract?.getDuration()!!.toInt()
            }
        })
        //视频播放完成
        mediaPlayer?.setOnCompletionListener(object :
            HWSDKPlayerContract.Companion.OnCompletionListener {
            override fun onCompletion(hWSDKPlayerContract: HWSDKPlayerContract?) {
                Logcat.dTag(">>>>>>>CompletionListener")
                if (playerError) return
                controllerView?.onCompletion()
                if (playerDataHelp == null) {
                    return
                }
                endFlag = 1
                requestRecord(playerDataHelp)
                playerDataHelp?.onPlayerListener?.onCompletion(playerDataHelp)

                if (isLoopPlayer()) {
                    playerDataHelp?.getNextEpisode(playerDataHelp?.currentMediaIndex)
                } else {
                    playerDataHelp?.getNextEpisode()
                }

            }
        })

        //视频播返回的监听信息
        mediaPlayer?.setOnInfoListener(object :
            HWSDKPlayerContract.Companion.OnInfoListener {
            override fun onInfo(
                hWSDKPlayerContract: HWSDKPlayerContract?,
                what: Int,
                extra: Any?
            ) {
                when (what) {
                    MEDIA_INFO_BUFFERING_START -> {
                        bufferView?.onBuffer(true)
                        playerDataHelp?.onPlayerListener?.onPlayerBuffer(
                            true,
                            bufferView!!.getNetSpeedRate()
                        )
                    }
                    MEDIA_INFO_BUFFERING_END -> {
                        bufferView?.onBuffer(false)
                        playerDataHelp?.onPlayerListener?.onPlayerBuffer(
                            false,
                            bufferView!!.getNetSpeedRate()
                        )
                    }
                    MEDIA_INFO_VIDEO_RENDERING_START -> {
                        bufferView?.onBuffer(false)
                    }

                }
            }
        })

        mediaPlayer?.setOnErrorListener(object :
            HWSDKPlayerContract.Companion.OnErrorListener {
            override fun onError(
                hWSDKPlayerContract: HWSDKPlayerContract?,
                errorStatus: Int,
                errorCode: Int,
                s: String?
            ) {
                Logcat.eTag(">>>>>>>>>>>>播放异常:$s")
                if (errorStatus == HWSDKPlayerContract.ERROR_VIP_PAY) {
                    playerDataHelp?.onPayInvoke()
                } else {
                    playerErrorDialog("播放异常(错误码:$s)，请稍候重试！", errorCode)
                }

            }

        })

        mediaPlayer?.setOnSeekCompleteListener(object :
            HWSDKPlayerContract.Companion.OnSeekCompleteListener {
            override fun onSeekComplete(hWSDKPlayerContract: HWSDKPlayerContract?) {
                controllerView?.onSeekComplete()
            }
        })

        mediaPlayer?.setOnVideoSizeChangedListener(object :
            HWSDKPlayerContract.Companion.OnVideoSizeChangedListener {
            override fun onVideoSizeChanged(
                hWSDKPlayerContract: HWSDKPlayerContract?,
                width: Int,
                height: Int
            ) {
                if (height != 0 && width != 0) {
                    val playerLogo1 = setPlayerLogo()
                    if (!playerLogo1) return
                    val videoHeight = mediaPlayer?.getVideoHeight()
                    logoMarginTop = (Utils.nullToInt(playerContainer?.height) - videoHeight) / 2
                    setLogoImageSize(isFullscreen(), 180, 45, 40)
                }
            }
        })


        //进度条的定时任务
        controllerView?.setProgressUpdateListener { progress, bufferProgress ->
            bufferView?.setNetSpeedRate()
            if (!isFullscreen()) {
                playerSmallProgress?.progress = progress
                playerSmallProgress?.secondaryProgress = bufferProgress
            }
        }

        //快进快退监听
        controllerView?.setSeekToListener { isEnd, isEnter ->
            playerDataHelp?.onPlayerListener?.onSeekTo(isEnd, isEnter)
        }

        //电影试看结束
        controllerView?.setFilmTryAndSeeEndListener {
            Logcat.dTag("电影试看完，触发支付>>>>")
            playerDataHelp?.onPayInvoke()
        }

        // 跳过片尾监听
        controllerView?.setSkipTheEndListener {
            endFlag = 1
            requestRecord(playerDataHelp)
            playerDataHelp?.onPlayerListener?.onPlayerUpdateEpisode(playerDataHelp)
        }


        //挽留页面的按钮监听
        detainmentView?.setButtonListener {
            if (it) {
                getExitPalyer()
            } else {
                startPlayer()
            }
        }

        /**
         * 特殊情况时（没有主动调用退出方法），上播播放记录
         */
        detainmentView?.setPlayerReport {
            getExitPalyer(true, false)
        }
        /**
         * 特殊情况时（没有主动调用退出方法），上播播放记录
         */
        videoEpisodeChoiceView?.setPlayerReport {
            getExitPalyer(true, false)
        }

        //主动切换集数的时候上报播放器记录
        videoEpisodeChoiceView?.setNextEpisodeListener {
            requestRecord(playerDataHelp)
            playerDataHelp?.onPlayerListener?.onPlayerUpdateEpisode(playerDataHelp)
        }
    }


    override fun setStartDataSource(playerDataHelp: PlayerDataHelp?) {
        setPosterVisibility(false)
        super.setStartDataSource(playerDataHelp)
    }

    override fun setStartDataUri(uri: String, playerType: Int) {
        setPosterVisibility(false)
        super.setStartDataUri(uri, playerType)
    }

    override fun startPlayer() {
        super.startPlayer()
        controllerView?.refreshPayStatus()
    }

    override fun gotoScreenNormal() {
        super.gotoScreenNormal()
        if (controllerView != null && controllerView!!.isShown())
            controllerView?.hide()
        bufferView?.onScreenChange(true)
        playerSmallProgress?.visibility = View.VISIBLE
        setLogoImageSize(false, 180, 45, 40)
        oldCurrentPosition = 0
    }

    override fun gotoScreenFullscreen() {
        super.gotoScreenFullscreen()
        playerError = false
        setPosterVisibility(false)
        bufferView?.onScreenChange(false)
        playerSmallProgress?.visibility = View.GONE
        setLogoImageSize(true, 180, 45, 40)
    }

    override fun setAspectRatio(aspectRatio: Int) {
        super.setAspectRatio(aspectRatio)
        val videoWidth = getVideoWidth()
        val videoHeight1 = getVideoHeight()
        if (videoHeight1 != 0 && videoWidth != 0) {
            val playerLogo1 = setPlayerLogo()
            if (!playerLogo1) return
            playerLogo?.visibility = View.GONE
            postDelayed(object : Runnable {
                override fun run() {
                    playerLogo?.visibility = View.VISIBLE
                    val videoHeight = mediaPlayer?.getVideoHeight() ?: 0
                    logoMarginTop = (Utils.nullToInt(playerContainer?.height) - videoHeight) / 2
                    setLogoImageSize(isFullscreen(), 180, 45, 40)
                }
            }, 300)
        }
    }

    override fun onDispatchPlayerKeyEvent(event: KeyEvent?): Boolean {
        if (event?.keyCode != KeyEvent.KEYCODE_BACK
            && detainmentView != null && !detainmentView!!.isShown()
            && mediaPlayer != null && mediaPlayer!!.isPlayingAD()
        )
            return true
        if (isFullscreen() && controllerView != null)
            return controllerView?.onControllerKeyDown(event!!.keyCode, event)!!
        return false
    }

    override fun onPlayerKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN && getPlayerDataHelp() != null) {
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_LEFT,
                KeyEvent.KEYCODE_DPAD_RIGHT -> {
                    if (videoEpisodeChoiceView!!.isShown() || detainmentView!!.isShown()) return false
                    val status = if (isPlaying()) "start_s" else "pause_s"
                    controllerView?.show(status)
                }
                KeyEvent.KEYCODE_DPAD_UP -> {
                    if (!detainmentView!!.isShown()) {
                        if (controllerView!!.isShown())
                            controllerView?.hide(1)
                        videoEpisodeChoiceView?.show("up")
                    }
                }
                KeyEvent.KEYCODE_DPAD_DOWN -> {
                    if (!detainmentView!!.isShown()) {
                        if (controllerView!!.isShown() && Utils.null2False(getPlayerDataHelp()?.isFilmTryAndSee)) {
                            return false
                        }
                        if (controllerView!!.isShown())
                            controllerView?.hide(1)
                        videoEpisodeChoiceView?.show("down")
                    }
                }
                KeyEvent.KEYCODE_ENTER, KeyEvent.KEYCODE_DPAD_CENTER -> {
                    if (isShowEpisodeAndDetainment()) return false
                    if (isPlaying()) {
                        pausePlayer()
                        controllerView?.show("pause")
                    } else {
                        startPlayer()
                        controllerView?.show("start")
                    }
                    return true
                }
                KeyEvent.KEYCODE_BACK -> {
                    if (isShowControllerAndEpisode()) {
                        hideViwe()
                        return true
                    }
                    if (getPlayerDataHelp() != null && !getPlayerDataHelp()!!.isBackDetention
                    ) {
                        onNextExit()
                        return true
                    }
                    if (!detainmentView!!.isShown()) {
                        pausePlayer()
                        detainmentView?.show(getPlayerDataHelp()?.classId)
                    } else {
                        detainmentView?.hide()
                        getExitPalyer()
                    }
                    return true
                }
            }
        }
        return false
    }

    private fun initData() {
        val playerDataHelp = getPlayerDataHelp()
        videoEpisodeChoiceView?.setData(playerDataHelp, this)
        if (Utils.null2False(playerDataHelp?.isPosterVisible)) {
            val settings = playerDataHelp?.onPlayerSettingsCallback
            val onPosterImgUrl = settings?.onPosterImgUrl(playerDataHelp)
            if (!TextUtils.isEmpty(onPosterImgUrl)) {
                val size = if (playerDataHelp?.album?.type == 10) 558 else 556
                settings?.onImgLoading(playerPoster, onPosterImgUrl, size)
            }

        } else {
            setPosterVisibility(false)
        }
        bufferView?.show(playerDataHelp?.currentMedia?.title)
    }

    /**
     * 设置海报隐藏显示
     */
    fun setPosterVisibility(visible: Boolean) {
        playerPoster?.visibility = if (visible) View.VISIBLE else View.GONE
        playerLogo?.visibility = View.GONE
    }

    private fun hideViwe() {
        controllerView?.hide()
        videoEpisodeChoiceView?.hide()
    }

    private fun hideViewAll() {
        controllerView?.hide(1)
        videoEpisodeChoiceView?.hide(1)
        detainmentView?.hide()
        bufferView?.hide()
    }

    private fun isShowControllerAndEpisode(): Boolean {
        if (controllerView!!.isShown() || videoEpisodeChoiceView!!.isShown())
            return true
        return false
    }

    private fun isShowEpisodeAndDetainment(): Boolean {
        if (detainmentView!!.isShown() || videoEpisodeChoiceView!!.isShown())
            return true
        return false
    }

    override fun exitPalyer() {
        getExitPalyer()
        super.exitPalyer()
    }

    /**
     * 退出播放器
     */
    private fun getExitPalyer(isReport: Boolean = true, isOutWindow: Boolean = true) {
        hideViewAll()
        var isPlaying = false  //解决更新不及时
        val playerDataHelp = getPlayerDataHelp()
        if (isReport)
            requestRecord(playerDataHelp)
        if (isPlayerWindowType()) {
            if (isOutWindow)
                gotoScreenNormal()
            if (Utils.null2False(playerDataHelp?.isPlayerFinish)
                || Utils.null2False(playerDataHelp?.isFilmTryAndSee)
                || playerError || playerDataHelp?.album?.showType == 1
            ) {
                isPlaying = false
                setPosterVisibility(true)
            } else {
                isPlaying = true
                startPlayer()
            }
            //activity容器中已经调用，所以这里只在窗口模式的时候调用
            playerDataHelp?.onPlayerListener?.onExitPlayer(playerDataHelp)
        } else {
            if (context != null) {
                (context as Activity).finish()
            }
        }
        if (exitPlayer != null) {
            this.exitPlayer?.invoke(isPlaying)
        }
    }

    fun setExitPlayerListener(exitPlayer: (isPlaying: Boolean) -> Unit) {
        this.exitPlayer = exitPlayer
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        getPlayerDataHelp()?.onDestroy()
        controllerView?.onDestroy()
        bufferView?.onDestroy()
        detainmentView?.onDestroy()
        videoEpisodeChoiceView?.onDestroy()
        playerErrorDialog?.onDestroy()
        controllerView = null
        bufferView = null
        detainmentView = null
        onVideoEpisodeChoice = null
        playerErrorDialog = null
        exitPlayer = null
    }


    /**
     * 保存历史记录
     */
    private fun requestRecord(
        playerDataHelp: PlayerDataHelp?
    ) {
        if (playerDataHelp == null || playerDataHelp?.album == null || playerDataHelp?.currentMedia == null || mediaPlayer == null || controllerView == null) return
        playerDataHelp?.onPlayerListener?.onPlayerRecord(this, playerDataHelp, endFlag)
    }

    /**
     * 小屏不弹支付和错误码提示
     * @return true 不弹框
     */
    private fun isPopUP(): Boolean {
        if (!isFullscreen()) {  //小屏不弹框
            if (getPlayerDataHelp() != null && getPlayerDataHelp()!!.isPosterVisible)
                setPosterVisibility(true)
            return true
        }
        return false
    }

    /**
     * 播放器播放错误弹框
     */
    private fun playerErrorDialog(error: String, oldCode: Int) {
        playerError = true
        if (isPopUP()) return
        val media = getPlayerDataHelp()?.currentMedia

        val newCode =
            Utils.nullToLong(PlayerErrorCode.addFirst(media?.encryptionType, oldCode))

        getPlayerDataHelp()?.onPlayerListener?.onPlayerError(oldCode, error)

        if (playerErrorDialog == null) {
            playerErrorDialog = PlayerErrorDialog(context, getPlayerDataHelp())
            playerErrorDialog?.setOnClickListener {
                getExitPalyer()
            }
        }

        playerErrorDialog?.showLog(
            getPlayerDataHelp()?.album?.cid,
            media?.vid,
            media?.title,
            media?.encryptionType, newCode, error
        )
    }

    /**
     * 按两次返回退出播放器
     */
    fun onNextExit() {
        if (System.currentTimeMillis() - firstClick > 2000L) {
            firstClick = System.currentTimeMillis()
            Utils.toast("再按一次退出")
            return
        }
        getExitPalyer()
    }


    /**
     * 设置播放器的logo
     */
    private fun setPlayerLogo(): Boolean {
        val settings = playerData?.onPlayerSettingsCallback
        if (settings?.isPlayerLogo() == false) {
            playerLogo?.visibility = View.GONE
            return false
        }
        playerLogo?.visibility = View.VISIBLE
        if (TextUtils.isEmpty(settings?.getPlayerLogoUrl())) {
//            playerLogo?.setImageResource(R.mipmap.sdk_player_logo)
        } else {
            settings?.onImgLoading(playerLogo, settings?.getPlayerLogoUrl(), -1)
        }
        return true
    }

    private fun setLogoImageSize(isMax: Boolean, width: Int, height: Int, margins: Int) {
        if (!Utils.null2False(playerLogo?.isShown)) return
        var wRatio = Utils.divide(
            measuredWidth.toDouble(),
            Utils.getScreenWidth(context).toDouble()
        )
        var hRatio = Utils.divide(
            measuredHeight.toDouble(),
            Utils.getScreenHeight(context).toDouble()
        )
        var w = 0
        var h = 0
        var m = 0
        if (isMax) {
            w = Utils.dp2px(width.toFloat())
            h = Utils.dp2px(height.toFloat())
            m = Utils.dp2px(margins.toFloat())
        } else {
            w = (Utils.dp2px(width.toFloat()) * wRatio).toInt()
            h = (Utils.dp2px(height.toFloat()) * hRatio).toInt()
            m = Utils.dp2px(margins.toFloat()) * Math.min(wRatio, hRatio).toInt()
        }
        val layoutParams =
            FrameLayout.LayoutParams(w, h)
        layoutParams?.gravity = Gravity.END
        if (logoMarginTop == 0)//全屏的时候等于0
            logoMarginTop = 10
        layoutParams?.setMargins(0, logoMarginTop + m, m, 0)
        playerLogo?.layoutParams = layoutParams
    }

    private var onVideoEpisodeChoice: OnVideoEpisodeChoice? = object : OnVideoEpisodeChoice {
        //去支付
        override fun onPay() {
            if (isPopUP() || isStartPay) return
            isStartPay = true
            pausePlayer()
            val playerDataHelp = getPlayerDataHelp()
            playerDataHelp?.onPlayerSettingsCallback?.onPlayerStartPay(playerDataHelp, success = {
                Logcat.dTag("订购成功——————")
                isStartPay = false
                //只有试看功能，订购完，才是续播
                if (Utils.null2False(playerDataHelp?.isTypeFilmTryAndSee())
                    && isVideoPrepared()
                ) {
                    startPlayer()
                    return@onPlayerStartPay
                }
                setStartDataSource(playerDataHelp)
            }, error = {
                Logcat.dTag("订购失败——————")
                isStartPay = false
                // 如果试看没有结束，继续试看
                if (Utils.null2False(getPlayerDataHelp()?.isFilmTryAndSee) && !Utils.null2False(
                        controllerView?.isFilmTryAndSeeStatus()
                    )
                ) {
                    startPlayer()
                    return@onPlayerStartPay
                }
                getExitPalyer()
            })
        }

        //切换集数
        override fun onEpisode(playerDataHelp: PlayerDataHelp) {
            Logcat.dTag("集数切换--->当前集数:(第${playerDataHelp.currentMediaIndex + 1}集)")
            bufferView?.show(playerDataHelp?.currentMedia?.title)
            setStartDataSource(playerDataHelp)
            videoEpisodeChoiceView?.onUpdateEpisodeListener()
        }

        //获取播放器凭证, 这个方法是异步的
        override fun onVirtualTvskey() {
            Logcat.dTag(
                ">>>>>>onVirtualTvskey: ${getPlayerDataHelp()?.currentMediaIndex}"
            )
        }

        override fun onError(code: Int, msg: String?) {
            post {
                playerErrorDialog(Utils.null2Length0(msg), code)
            }
        }

        override fun onCompletion() {
            Utils.toast("播放完毕")
            if (!isFullscreen()) {
                pausePlayer()
                setPosterVisibility(true)
                return
            }
            getExitPalyer(false)
        }
    }
}