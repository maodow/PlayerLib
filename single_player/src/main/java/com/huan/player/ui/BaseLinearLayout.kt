package com.huan.player.ui

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.huan.player.config.PlayerSettingsShare
import com.huan.player.constant.Logcat
import com.huan.player.constant.PlayerErrorCode
import com.huan.player.constant.PlayerScreenStatus
import com.huan.player.constant.Utils
import com.huan.player.contract.HWSDKPlayerContract
import com.huan.player.contract.PlayerViewContract
import java.lang.reflect.Constructor

abstract class BaseLinearLayout : LinearLayout,
    PlayerViewContract {
    protected val TAG: String = "HWPlayer"
    protected var playerContainer: FrameLayout? = null


    private var backgroundColor: View? = null
    internal var fullScreenDialog: FullScreenDialog? = null
    internal var screenStatus = PlayerScreenStatus.SCREEN_NORMAL
    internal var playerWindowType = PlayerScreenStatus.WINDOW_VIEW

    private var currentPlayerType: Int = -1  // 当前播放器类型
    private var firstMeasuredWidth = true //宽高只测量一次

    protected var mediaPlayer: HWSDKPlayerContract? = null //播放器对象

    protected var playerData: PlayerDataHelp? = null

    protected abstract fun getLayoutId(): Int
    protected abstract fun onCreate(context: Context)
    protected abstract fun onInitData(playerDataHelp: PlayerDataHelp?)
    protected abstract fun setMediaPlayerListener(mediaPlayer: HWSDKPlayerContract?)
    abstract fun onDispatchPlayerKeyEvent(event: KeyEvent?): Boolean
    abstract fun onPlayerKeyDown(keyCode: Int, event: KeyEvent?): Boolean

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var activitylayout =
            Utils.scanForActivity(context)?.getWindow()?.getDecorView() as ViewGroup
        var height = activitylayout.height
        var width = activitylayout.width

        if (height == measuredHeight && width == measuredWidth && firstMeasuredWidth) {
            firstMeasuredWidth = false
            screenStatus = PlayerScreenStatus.FULLSCREEN
            playerWindowType = PlayerScreenStatus.WINDOW_ACTIVITY
            gotoScreenFullscreen()
        } else if (firstMeasuredWidth && width != 0 && height != 0 && playerWindowType == PlayerScreenStatus.WINDOW_ACTIVITY) {
            firstMeasuredWidth = false
            screenStatus = PlayerScreenStatus.SCREEN_NORMAL
            gotoScreenNormal()
        }
    }


    private fun initView(context: Context) {
        View.inflate(context, getLayoutId(), this) //根布局必须的是FrameLayout
        onCreate(context)
        backgroundColor = View(getContext())
        backgroundColor?.setBackgroundColor(Color.BLACK)
        playerContainer = getChildAt(0) as FrameLayout?
        fullScreenDialog = FullScreenDialog(context, playerContainer);
        fullScreenDialog?.setOnDismissListener {
            gotoScreenNormal()
        }
        fullScreenDialog?.setDialogDispatchKeyEvent {
            if (mediaPlayer == null) return@setDialogDispatchKeyEvent false
            if (onDispatchPlayerKeyEvent(it))
                return@setDialogDispatchKeyEvent true
            false
        }
        fullScreenDialog?.setDialogKeyDown { keyCode, event ->
            if (mediaPlayer == null) return@setDialogKeyDown false
            if (onPlayerKeyDown(keyCode, event))
                return@setDialogKeyDown true
            false
        }
    }


    internal fun addData(isStart: Boolean, playerDataHelp: PlayerDataHelp?) {
        if (playerDataHelp == null) return
        if (this.playerData != null && playerDataHelp != null && this.playerData != playerDataHelp) {
            this.playerData = playerDataHelp
            if (mediaPlayer != null)
                setMediaPlayerListener(mediaPlayer)
        } else {
            this.playerData = playerDataHelp
        }
        onInitData(playerDataHelp)
        judgePlayerType(isStart)
    }

    internal fun addData(isStart: Boolean, uri: String?, playerType: Int = 0) {
        if (TextUtils.isEmpty(uri)) return
        addPlayerView(getPlayerPackage(playerType))
        mediaPlayer?.setDataSource(isStart, uri, 0)
    }

    //选择使用哪种播放器
    private fun judgePlayerType(isStart: Boolean) {
        if (!isStart) return

        addPlayerView(getPlayerPackage(Utils.nullToInt(playerData?.selectPlayerType)))

        if (playerData?.currentMedia == null) {
            playerData?.onErrorInvoke(
                PlayerErrorCode.RESOURECE_FAILURE_0, "加载资源失败0"
            )
            return
        }

        if (Utils.null2False(playerData?.isPay) && isStart) {
            Logcat.dTag("创建播放器时，检查到支付")
            playerData?.onPayInvoke()
            return
        }

        //使用局方的播放地址
        val thirdPartyPlayerUrl = playerData?.onPlayerSettingsCallback?.isThirdPartyPlayerUrl()
        if (thirdPartyPlayerUrl != null && thirdPartyPlayerUrl
            && TextUtils.isEmpty(playerData?.currentMedia?.cdnUrl)
        ) {
            playerData?.onPlayerListener?.setThirdPartyPlayerUrlListener(playerData, success = {
                    if (TextUtils.isEmpty(it)) {
                        playerData?.onErrorInvoke(
                            PlayerErrorCode.RESOURECE_FAILURE_1, "#加载资源失败1"
                        )
                    } else {
                        playerData?.currentMedia?.cdnUrl = it
                        addData(isStart, playerData)
                    }
                }, error = {
                playerData?.onErrorInvoke(
                        PlayerErrorCode.RESOURECE_FAILURE_2, "#加载资源失败2"
                    )
                })
            return
        }

        if (TextUtils.isEmpty(playerData?.currentMedia?.cdnUrl)) {
            Logcat.iTag("加载资源失败3======url: ${playerData?.currentMedia?.cdnUrl}")
            playerData?.onErrorInvoke(
                PlayerErrorCode.RESOURECE_FAILURE_3, "加载资源失败3"
            )
            return
        }

        if (playerData?.currentMedia?.encryptionType == 0
            && playerData?.currentMedia?.drm != 0 && isStart
        ) {
            playerData?.onVirtualTvskey()
            return
        }

        if (playerData?.currentMedia?.encryptionType == 0) {

        } else {
            Logcat.dTag(">>>>ijk uri: ${playerData?.currentMedia?.cdnUrl}")
            mediaPlayer?.setDataSource(
                isStart,
                playerData?.currentMedia?.cdnUrl,
                playerData?.album?.cid,
                playerData?.currentMedia?.vid,
                "auto",
                playerData?.historyProgress,
                0
            )
        }
    }

    /**
     * 获取播放器对象，并添加到视图中
     * @param name 播放器对象的class
     */
    private fun addPlayerView(playerName: Class<*>?) {
        var playerNames = playerName
        // 防止多次创建对象
        if (mediaPlayer != null || Utils.nullToInt(playerData?.selectPlayerType) == 1) {
            playerNames = transformPlayer()
            if (playerNames == null)
                return
        }
        createPlayerView(playerNames)
        val layoutParams =
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER
            )

        mediaPlayer?.setAspectRatio(PlayerSettingsShare.INSTANCE.getAspectRatio())
        mediaPlayer?.let {
            if (it.isInitSDK())
                playerContainer?.addView(it?.getPlayerView(), 0, layoutParams)
        }

        currentPlayerType = Utils.nullToInt(playerData?.currentMedia?.encryptionType)
        Logcat.dTag( "addPlayerView_______")
        setMediaPlayerListener(mediaPlayer)
        if (!Utils.null2False(mediaPlayer?.isInitSDK()))
            playerData?.onErrorInvoke(
                PlayerErrorCode.PLAYER_INIT_FAILURE, "播放器初始化错误（$currentPlayerType），请重试"
            )

    }

    // 创建播放器
    private fun createPlayerView(playerName: Class<*>?) {
        try {
            val constructor =
                playerName?.getConstructor() as Constructor<HWSDKPlayerContract>
            mediaPlayer = constructor.newInstance()
        } catch (e: Exception) {
            Logcat.eTag("HWPlayerContract对象===>${e.localizedMessage}")
            e.printStackTrace()
        }
    }


    /**
     * 当是系统播放器时，自动转换播放器
     */
    private fun transformPlayer(): Class<*>? {
        val selectPlayerType = playerData?.selectPlayerType
        if (selectPlayerType == 1) {  //系统播放器
            if (mediaPlayer == null || currentPlayerType != playerData?.currentMedia!!.encryptionType) {
                playerContainer?.removeView(mediaPlayer?.getPlayerView())
                mediaPlayer?.release()
                return getPlayerPackage(playerData?.currentMedia!!.encryptionType)
            }
        }
        return null
    }

    /**
     * @param selectPlayerType 0 腾讯播放器 1 系统播放器
     */
    private fun getPlayerPackage(selectPlayerType: Int): Class<*> {
        if (selectPlayerType == 0) {
            var className = "tv.huan.tencent.player.TXPlayerViewImpl"
            return Class.forName(className)
        } else {
            var className = "com.huan.player.sdk.HWSDKIjkplayerViewImpl"
            return Class.forName(className)
        }
    }

    //进入全屏
    internal fun goScreenFullscreen() {
        if (isFullscreen() || playerContainer == null) return
        Logcat.dTag(">>>>>>>>goScreenFullscreen")
        removeView(playerContainer)
        fullScreenDialog?.showFullScreen()
        //获取当前Activi的容器视图
        var vg = (context as Activity)?.getWindow()
            ?.getDecorView() as ViewGroup //和他也没有关系
        vg.addView(
            backgroundColor,
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        )
        screenStatus = PlayerScreenStatus.FULLSCREEN
    }


    //退出全屏
    internal fun goScreenNormal() { //goback本质上是goto
        if (!isFullscreen() || playerContainer == null) return
        Logcat.dTag(">>>>>>>>goScreenNormal")
        fullScreenDialog?.dismissFullScreen()
        addView(
            playerContainer,
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        )
        //获取当前Activi的容器视图
        var vg = (context as Activity)?.getWindow()
            ?.getDecorView() as ViewGroup //和他也没有关系
        vg.removeView(backgroundColor)
        screenStatus = PlayerScreenStatus.SCREEN_NORMAL
    }


    /**
     *  View 销毁时调用
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (fullScreenDialog != null && fullScreenDialog!!.isShowing)
            fullScreenDialog?.dismissFullScreen()
        fullScreenDialog?.onDestroy()
        playerData?.onPlayerListener?.onDestroy()
        mediaPlayer?.release()
        playerContainer?.removeAllViews()
        mediaPlayer = null
        playerContainer = null
        fullScreenDialog = null
        playerData = null
    }

}