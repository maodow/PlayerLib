package tv.huan.hwsystemsdk.player.presenter

import android.content.Context
import android.graphics.Color
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.huan.player.R
import com.huan.player.bean.Media
import com.huan.player.bean.NetVideoInfo
import com.huan.player.config.PlayerSettingsShare
import com.huan.player.constant.Logcat
import com.huan.player.constant.Utils
import com.huan.player.contract.ControllerViewContract
import com.huan.player.contract.PlayerViewContract
import com.huan.player.contract.VideoEpisodeChoiceViewContract
import com.huan.player.ui.HandlerProgress
import com.huan.player.ui.PlayerDataHelp
import com.huan.player.ui.SwitchImageView
import com.huan.player.ui.preview.PreviewDataMng
import com.hw.recycler.widget.THRecyclerView
import tv.huan.hwsystemsdk.player.adapter.PlayerPreviewAdapter
import java.util.*

class ControllerViewPresenter : ControllerViewContract {

    private val TAG = "HWPlayer"
    private var context: Context? = null
    private var viewGroup: ViewGroup? = null
    private var controllerShowDurationTime: Int = 3000 // 控制器显示多长时间消失（单位毫秒）
    private var showStartTime = 0L  //  更新显示的时间
    private var seekStep: Int = 10000  //快进快退的步长
    private var seekTime: Int = 0     // 记录快进的时间
    private var firstShow: Boolean = false // 首次显示，是为了解决第一次显示不去更新进度
    private var seek_bar: SeekBar? = null
    private var title: TextView? = null
    private var running_time: TextView? = null
    private var seek_tip_time: TextView? = null
    private var total_time: TextView? = null
    private var player_pause_bt: SwitchImageView? = null
    private var payBtn: LinearLayout? = null
    private var mediaPlayer: PlayerViewContract? = null
    private var videoEpsode: VideoEpisodeChoiceViewContract? = null
    private var handler: HandlerProgress? = null
    private var seekToOrientation: Boolean = true //快进的方向  false 退 true 进
    private var isSeekToEnd: Boolean = true  // 是否快进快退结束
    private var isFilmTryAndSeeStatus = true  // 电影试看状态进行中，还是结束
    private var filmTryAndSeeTimeMillisecond = 0
    private var isShowSkipHead: String? = "" //是否显示跳过片头提示
    private var currentMedia: Media? = null
    private var playerDataHelp: PlayerDataHelp? = null
    private var skipHeadNotice: LinearLayout? = null
    private var vipPayLayout: LinearLayout? = null
    private var vipHint: TextView? = null
    private var skipHeadSure: TextView? = null
    private var skipHeadCancel: TextView? = null
    private var dotOne: TextView? = null
    private var dotTwo: TextView? = null
    private var bottomIcon: ImageView? = null


    private var progressUpdate: ((progress: Int, bufferProgress: Int) -> Unit)? = null
    private var filmTryAndSeeEnd: (() -> Unit)? = null
    private var seekToListener: ((isEnd: Boolean, isEnter: Boolean) -> Unit)? = null
    private var skipEndNext: (() -> Unit)? = null

    private var playerPreviewAdapter: PlayerPreviewAdapter? = null
    private var previewRecycler: THRecyclerView? = null
    private var previewDataMng: PreviewDataMng? = null
    private var previewFocusView: View? = null
    private var previewData: TextView? = null
    private var layoutBottom: ConstraintLayout? = null
    private var inAnimation: Animation? = null
    private var outAnimation: Animation? = null


    //handler 中调用，更新seekbar的进度
    override fun handleUpdateProgress() {

        if (mediaPlayer != null && mediaPlayer!!.isPlaying()) {
            setSeekBarProgress(mediaPlayer!!.getCurrentPosition()?.toInt(), false)
            skipTheEnd()
        }
        if (isShown()) {
            if (Calendar.getInstance().timeInMillis - showStartTime >= controllerShowDurationTime)
                hide()
        }
        if (progressUpdate != null) {
            progressUpdate?.invoke(Utils.nullToInt(seek_bar?.progress), getBufferProgress())
        }
    }

    //handler 中调用，更新视屏进度（快进快退）
    override fun handleSeekTo() {
        var progress =
            if (Utils.null2False(previewDataMng?.isPreviewDataValid)) {
                previewDataMng?.getVideoPositionByIdx(
                    Utils.nullToInt(
                        previewDataMng?.getItemIndexByPosition(
                            Utils.nullToInt(seek_bar?.progress)
                        )
                    )
                )
            } else
                seek_bar?.progress

        setSeekTo(Utils.nullToInt(progress))
        previewDataMng?.notifyFailingRefresh()
    }

    override fun onCreate(context: Context, viewGroup: ViewGroup, handler: HandlerProgress?) {
        this.context = context
        this.handler = handler
        this.viewGroup = viewGroup
        val mView = View.inflate(context, R.layout.sdk_controller_view, viewGroup)
        skipHeadNotice = mView.findViewById<LinearLayout>(R.id.skip_head_notice)
        title = mView.findViewById<TextView>(R.id.title_video)
        seek_bar = mView.findViewById<SeekBar>(R.id.seek_bar)
        running_time = mView.findViewById<TextView>(R.id.running_time)
        total_time = mView.findViewById<TextView>(R.id.total_time)
        seek_tip_time = mView.findViewById<TextView>(R.id.seek_tip_time)
        player_pause_bt = mView.findViewById<SwitchImageView>(R.id.player_pause_bt)
        payBtn = mView.findViewById<LinearLayout>(R.id.pay_btn)
        vipPayLayout = mView.findViewById<LinearLayout>(R.id.vip_pay)
        vipHint = mView.findViewById<TextView>(R.id.vip_hint)
        skipHeadSure = mView.findViewById<TextView>(R.id.skip_head_sure)
        skipHeadCancel = mView.findViewById<TextView>(R.id.skip_head_cancel)
        layoutBottom = mView.findViewById<ConstraintLayout>(R.id.layout_bottom)


        dotOne = mView.findViewById<TextView>(R.id.dot_one)
        dotTwo = mView.findViewById<TextView>(R.id.dot_two)

        previewRecycler = mView.findViewById<THRecyclerView>(R.id.preview_recycler)
        previewFocusView = mView.findViewById<View>(R.id.preview_focus_view)
        previewData = mView.findViewById<TextView>(R.id.preview_data)
        bottomIcon = mView.findViewById<ImageView>(R.id.play_controller_bottom_icon)


        //底部进入动画
        inAnimation =
            AnimationUtils.loadAnimation(context, R.anim.show_in_bottom)
        //底部退出动画
        outAnimation = AnimationUtils.loadAnimation(context, R.anim.hide_out_top)

        seek_bar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                setTextTime(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        payBtn?.setOnClickListener {
            mediaPlayer?.pausePlayer()
            if (filmTryAndSeeEnd != null) {
                filmTryAndSeeEnd?.invoke()
            }
        }

        skipHeadSure?.setOnClickListener {
            skipHeadNotice?.visibility = View.GONE
            PlayerSettingsShare.INSTANCE.setSkipHead(true)
            showSkipHead()
        }
        skipHeadCancel?.setOnClickListener {
            skipHeadNotice?.visibility = View.GONE
            PlayerSettingsShare.INSTANCE.setSkipHead(false)
        }

        outAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                viewGroup?.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })

        previewDataMng = PreviewDataMng()
    }

    /**
     * 视频快进快退
     */
    private fun setSeekTo(seekToProgress: Int) {
        seek_bar?.secondaryProgress = 0
        mediaPlayer?.seekTo(seekToProgress)
    }


    /**
     * 更新进度条的进度
     * @param isDown true,表示按键触发，false 其他触发
     */
    private fun setSeekBarProgress(currentPosition: Int, isDown: Boolean = true) {
        seek_bar?.secondaryProgress = getBufferProgress() //缓冲的进度
        seek_bar?.progress = currentPosition  //进度条进度
        //预览图进度设置
        setPreviewProgress(currentPosition, isDown)

        if (playerDataHelp != null && playerDataHelp!!.isFilmTryAndSee && !isFilmTryAndSeeStatus && currentPosition >= filmTryAndSeeTimeMillisecond) {
            isFilmTryAndSeeStatus = true
            mediaPlayer?.pausePlayer()
            var timer = Utils.toPlayFormatString(filmTryAndSeeTimeMillisecond.toLong())
            running_time?.setText(timer)
            seek_tip_time?.setText(timer)
            if (filmTryAndSeeEnd != null) {
                filmTryAndSeeEnd?.invoke()
            }
        }
    }


    /**
     * 设置预览图进度
     * @param currentPosition 当前的播放进度
     * @param takeEffect 是否立即更新
     */
    private fun setPreviewProgress(
        currentPosition: Int,
        isDown: Boolean,
        takeEffect: Boolean = false
    ) {
        if (!Utils.null2False(previewDataMng?.isPreviewDataValid) || !isShown()) return
        previewDataMng?.getItemIndexByPosition(currentPosition)?.let {
            var selectedPosition = Utils.nullToInt(it)
            selectedPosition = if (selectedPosition == -1) 0 else selectedPosition

            val position = Utils.toPlayFormatString(
                previewDataMng?.getVideoPositionByIdx(selectedPosition)?.toLong() ?: 0
            )

            if (takeEffect) {
                previewData?.setText(position)
                running_time?.setText(position)
                previewRecycler?.scrollToPosition(it)
                //修正第一次显示时，滑动不到位置的问题
                running_time?.postDelayed({
                    previewRecycler?.scrollToPosition(it)
                    playerPreviewAdapter?.setSelectedPosition(it)
                    playerPreviewAdapter?.notifyItemChanged(it)
                    previewDataMng?.notifyFailingRefresh()
                }, 30)
                return@let
            }
            if (it == Utils.nullToInt(previewRecycler?.selectedPosition)) return@let
            previewRecycler?.setSelectedPositionSmooth(it)
            previewData?.setText(position)
            running_time?.setText(position)
            if (!isDown) {
                playerPreviewAdapter?.setSelectedPosition(it)
                playerPreviewAdapter?.notifyItemChanged(it)
            }
            previewDataMng?.notifyFailingRefresh()
        }
    }

    /**
     * 进度条上时间刻度
     */
    private fun setTextTime(currentPosition: Int) {
        if (running_time == null || seek_bar == null) return
        var timer = Utils.toPlayFormatString(currentPosition.toLong())
        running_time?.setText(timer)
        if (Utils.null2False(previewDataMng?.isPreviewDataValid)) return

        seek_tip_time?.setText(timer)

        var width = -running_time!!.width / 10
        if (running_time?.length() == 8) {
            width = running_time!!.width / 6
        }

        if (isShown()) {
            seek_tip_time?.translationX =
                (seek_bar?.getThumb()?.getBounds()!!.left - width).toFloat()
            seek_tip_time?.visibility = View.VISIBLE
        }
    }

    /**
     * 进度条上的片头片尾刻度
     */
    private fun setSeekBarScale(head: Int, end: Int) {

        if (seek_bar == null || seek_bar!!.max == 0 || head == 0 || end == 0) {
            dotOne?.visibility = View.INVISIBLE
            dotTwo?.visibility = View.INVISIBLE
            return
        }
        seek_bar!!.getViewTreeObserver()
            .addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    seek_bar?.getViewTreeObserver()?.removeOnPreDrawListener(this)
                    val width = dotOne!!.width
                    val max = Math.max(seek_bar!!.max, seek_bar!!.width)
                    val min = Math.min(seek_bar!!.max, seek_bar!!.width)
                    if (min == 0) return false
                    val ratio = max / min
                    var headDistance = 0
                    var endDistance = 0
                    if (seek_bar!!.width > seek_bar!!.max) {
                        headDistance = head * ratio
                        endDistance = (seek_bar!!.max - end) * ratio
                    } else {
                        headDistance = head / ratio
                        endDistance = (seek_bar!!.max - end) / ratio
                    }
                    dotOne?.translationX = (seek_bar!!.paddingLeft + headDistance - width).toFloat()
                    dotTwo?.translationX = (endDistance - width - seek_bar!!.paddingLeft).toFloat()
                    dotOne?.visibility = View.VISIBLE
                    dotTwo?.visibility = View.VISIBLE
                    return false
                }
            })

    }


    /**
     * 有当前视频的缓冲进度
     */
    private fun getBufferProgress(): Int {
        return Utils.nullToInt(mediaPlayer?.getCurrentBufferPosition())
    }

    /**
     * 调用快进快退的开始监听
     */
    private fun getSeekToStartListener(isOrientaion: Boolean) {
        seekToOrientation = isOrientaion
        if (isSeekToEnd && seekToListener != null)
            seekToListener?.invoke(false, seekToOrientation)
        isSeekToEnd = false
    }

    /**
     *  进度条的定时任务
     */
    override fun setProgressUpdateListener(progressUpdate: (progress: Int, bufferProgress: Int) -> Unit) {
        this.progressUpdate = progressUpdate
    }

    /**
     * 快进监听
     */
    override fun setSeekToListener(seekToListener: (isEnd: Boolean, isEnter: Boolean) -> Unit) {
        this.seekToListener = seekToListener
    }


    /**
     * 电影试看结束监听
     */
    override fun setFilmTryAndSeeEndListener(filmTryAndSeeEnd: () -> Unit) {
        this.filmTryAndSeeEnd = filmTryAndSeeEnd
    }


    /**
     * 1 隐藏不带动画
     */
    override fun hide(tag: Int?) {
        if (tag == 0) {
            layoutBottom?.startAnimation(outAnimation)
            layoutBottom?.visibility = View.GONE
        } else {
            layoutBottom?.visibility = View.GONE
            viewGroup?.visibility = View.GONE
        }
        seek_tip_time?.visibility = View.GONE
        previewRecycler?.visibility = View.INVISIBLE
        previewFocusView?.visibility = View.GONE
        previewData?.visibility = View.GONE
    }

    override fun show(tag: String?) {
        if (playerDataHelp == null) return
        player_pause_bt?.setSwitch("pause".equals(tag) || "pause_s".equals(tag))
        showStartTime = Calendar.getInstance().timeInMillis
        if (!isShown() && mediaPlayer != null) {
            if (!mediaPlayer!!.isFullscreen()) return
            if (Utils.null2False(videoEpsode?.isShown()))
                videoEpsode?.hide()
            layoutBottom?.startAnimation(inAnimation)
            viewGroup?.visibility = View.VISIBLE
            layoutBottom?.visibility = View.VISIBLE
            seekTime = mediaPlayer?.getCurrentPosition()!!.toInt()
            firstShow = true
            setSeekBarScale(
                Utils.nullToInt(currentMedia?.headTime) * 1000,
                Utils.nullToInt(currentMedia?.tailTime) * 1000
            )
            if (Utils.null2False(skipHeadNotice?.isShown)) {
                skipHeadSure?.requestFocusFromTouch()
            }
            if (tag == "start_s" || tag == "pause_s")
                showPreview()
            setPreviewProgress(seekTime, false, true)
        }
        if (Utils.null2False(playerDataHelp?.isFilmTryAndSee)) {
            payBtn?.setFocusable(true)
            vipPayLayout?.visibility = View.VISIBLE
            bottomIcon?.visibility = View.GONE
            vipHint?.text =
                "本节目提供前${Utils.nullToInt(playerDataHelp?.filmTryAndSeeTimeMillisecond) / 60000}分钟试看，完整观看请"
        } else {
            vipPayLayout?.visibility = View.GONE
            payBtn?.setFocusable(false)
            bottomIcon?.visibility = View.VISIBLE
        }

    }

    /**
     *  视屏资源初始化成功
     */
    override fun onVideoPrepared(
        videoEpsode: VideoEpisodeChoiceViewContract?,
        playerView: PlayerViewContract?,
        playerDataHelp: PlayerDataHelp?
    ) {
        if (videoEpsode == null || playerDataHelp == null) return
        this.playerDataHelp = playerDataHelp
        this.videoEpsode = videoEpsode
        this.currentMedia = playerDataHelp?.currentMedia
        this.isFilmTryAndSeeStatus = !playerDataHelp!!.isFilmTryAndSee
        this.filmTryAndSeeTimeMillisecond = playerDataHelp!!.filmTryAndSeeTimeMillisecond
        this.mediaPlayer = playerView
        this.title?.setText(currentMedia?.title)
        total_time?.setText(Utils.toPlayFormatString(mediaPlayer!!.getDuration()))
        seek_bar?.max = mediaPlayer!!.getDuration().toInt()
        setSeekBarProgress(0, false)
        seek_tip_time?.setText("00:00:00")
        showSkipHead()
        if (playerDataHelp!!.isFilmTryAndSee)
            show("start")

        title?.setTextColor(Color.WHITE)

        playerView?.getDuration()?.let {
            previewDataMng?.setVideoDuration(it)
        }

        previewDataMng?.isPreviewDataValid?.let {
            if (it) {
                seek_tip_time?.visibility = View.GONE
                seekStep = Utils.nullToInt(previewDataMng?.intervalMillis)
                initPreviewAdapter(previewDataMng)
            } else {
                previewRecycler?.visibility = View.INVISIBLE
                previewFocusView?.visibility = View.GONE
                previewData?.visibility = View.GONE
                seek_tip_time?.visibility = View.VISIBLE
            }
        }
    }

    /**
     * 只有左右键可以触发预览图显示
     */
    private fun showPreview() {
        if (Utils.null2False(previewRecycler?.isShown) || !Utils.null2False(previewDataMng?.isPreviewDataValid)) return
        previewFocusView?.visibility = View.VISIBLE
        previewRecycler?.visibility = View.VISIBLE
        previewData?.visibility = View.VISIBLE
        seek_tip_time?.visibility = View.GONE
    }

    override fun onNetVideoInfoListener(videoInfo: NetVideoInfo?) {
        previewFocusView?.visibility = View.GONE
        playerPreviewAdapter?.clearCache()
        previewDataMng?.setPreviewDataMng(
            videoInfo?.getmPL(),
            videoInfo?.getmLnk(),
            Utils.nullToInt(videoInfo?.getmPLType())
        )
    }

    /**
     * 电影是否试看结束
     * true 结束  false 未结束
     */
    override fun isFilmTryAndSeeStatus(): Boolean {
        return isFilmTryAndSeeStatus
    }


    override fun getSeekBarProgress(): Int {
        return Utils.nullToInt(seek_bar?.progress)
    }

    /**
     * 视屏播放完成
     * 由于视频播放完之后播放器的当前进度到不了满进度，所以做处理
     */
    override fun onCompletion() {
        if (mediaPlayer != null)
            setSeekBarProgress(mediaPlayer!!.getDuration().toInt(), false)
    }


    /**
     * 快进快退加载完成
     */
    override fun onSeekComplete() {
        if (seekToListener != null) {
            seekToListener?.invoke(true, seekToOrientation)
        }
        isSeekToEnd = true
        Logcat.dTag( ">>>>>>onSeekComplete: ${mediaPlayer?.getCurrentPosition()}")
    }


    override fun onControllerKeyDown(keyCode: Int, event: KeyEvent?): Boolean {//试看完禁止滑动
        if (!isShown() || mediaPlayer?.getDuration() == 0L || (playerDataHelp != null && isFilmTryAndSeeStatus && playerDataHelp!!.isFilmTryAndSee)) return false
        if (skipHeadNotice!!.isShown() && (event?.action == KeyEvent.ACTION_DOWN || event?.action == KeyEvent.ACTION_UP)) {
            return false
        }
        if (event?.action == KeyEvent.ACTION_DOWN) {
            seekTime = seek_bar!!.progress
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_LEFT -> { //向左键
                    showPreview()
                    playerPreviewAdapter?.unfocused()
                    previewDataMng?.onKeyStatus(1)
                    getSeekToStartListener(false)
                    handler?.removeMessages(HandlerProgress.HANDLE_START_SEEK_TO)
                    handler?.removeMessages(HandlerProgress.HANDLE_START_UPDATE_PROGRESS)
                    seekTime -= seekStep
                    setSeekBarProgress(if (seekTime < 0) 0 else seekTime)
                    show("start")
                    firstShow = false
                }
                KeyEvent.KEYCODE_DPAD_RIGHT -> { //向右键
                    showPreview()
                    playerPreviewAdapter?.unfocused()
                    previewDataMng?.onKeyStatus(1)
                    getSeekToStartListener(true)
                    handler?.removeMessages(HandlerProgress.HANDLE_START_SEEK_TO)
                    handler?.removeMessages(HandlerProgress.HANDLE_START_UPDATE_PROGRESS)
                    seekTime += seekStep
                    var progress =
                        if (seekTime > Utils.nullToInt(seek_bar?.max)) Utils.nullToInt(
                            seek_bar?.max
                        ) else seekTime
                    setSeekBarProgress(progress)
                    show("start")
                    firstShow = false
                }
            }
        } else if (event?.action == KeyEvent.ACTION_UP) {
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_DPAD_LEFT -> {
                    previewDataMng?.onKeyStatus(0)
                    if (!firstShow) {
                        handler?.sendEmptyMessageDelayed(HandlerProgress.HANDLE_START_SEEK_TO, 500)
                        previewDataMng?.getItemIndexByPosition(Utils.nullToInt(seek_bar?.progress))
                            ?.let {
                                playerPreviewAdapter?.setSelectedPosition(it)
                                playerPreviewAdapter?.notifyItemChanged(it)
                            }
                    }
                    firstShow = false
                }
            }
        }
        return false
    }

    /**
     * 刷新支付状态
     */
    override fun refreshPayStatus() {
        if (!isShown()) return
        show("start")
    }

    override fun getMax(): Int {
        return Utils.nullToInt(seek_bar?.max)
    }

    override fun setSkipTheEndListener(skipEndNext: () -> Unit) {
        this.skipEndNext = skipEndNext
    }


    /**
     * 是否跳过片头和是否显示跳过片头片尾提示
     * 不是会员不跳过
     */
    private fun showSkipHead() {
        if (mediaPlayer == null || currentMedia == null || currentMedia!!.headTime <= 0) return
        if (mediaPlayer!!.getCurrentPosition() >= currentMedia!!.headTime * 1000
        ) return
        val auth = playerDataHelp?.onPlayerSettingsCallback?.getAuth()
        if (1 != auth) return
        val skipTag = PlayerSettingsShare.INSTANCE.isSkipHead()
        if (skipTag) {
            if (mediaPlayer!!.getCurrentPosition() < currentMedia!!.headTime * 1000) {
                mediaPlayer!!.seekTo(currentMedia!!.headTime * 1000)
                if (mediaPlayer!!.isPlayerWindowType())
                    Utils.toast("已为您跳过片头")
            }
            return
        }
        if (isShowSkipHead.equals(playerDataHelp?.album?.cid)) return
        skipHeadNotice?.visibility = View.VISIBLE
        isShowSkipHead = playerDataHelp?.album?.cid
        // 小屏的时候不显示，不获焦
        if (!mediaPlayer!!.isFullscreen()) return
        show("start")
        skipHeadSure?.requestFocusFromTouch()
    }

    /**
     * 跳过片尾
     */
    private fun skipTheEnd() {
        if (currentMedia == null || mediaPlayer == null || currentMedia!!.tailTime <= 0 || mediaPlayer!!.getCurrentPosition() == 0L || mediaPlayer!!.getDuration() == 0L) return
        if (mediaPlayer!!.getCurrentPosition() + currentMedia!!.getTailTime() * 1000 >= mediaPlayer!!.getDuration()
            && mediaPlayer!!.isPlaying()
        ) {
            val auth = playerDataHelp?.onPlayerSettingsCallback?.getAuth()
            if (1 != auth) return
            val skipTag = PlayerSettingsShare.INSTANCE.isSkipHead()
            if (skipTag) { //跳入下一集
                if (skipEndNext != null)
                    skipEndNext?.invoke()
                playerDataHelp?.getNextEpisode()
            }
        }
    }

    private fun initPreviewAdapter(previewDataMng: PreviewDataMng?) {
        if (playerPreviewAdapter == null) {
            playerPreviewAdapter = PlayerPreviewAdapter(context, previewDataMng)
            previewRecycler?.setAdapter(playerPreviewAdapter)
            previewDataMng?.setAdapter(playerPreviewAdapter)
            previewDataMng?.setRecycler(previewRecycler)
        }
        previewDataMng?.totalItems?.let {
            playerPreviewAdapter?.mItemCount = it
        }
        playerPreviewAdapter?.notifyDataSetChanged()
    }

    override fun onDestroy() {
        viewGroup?.removeAllViews()
        playerPreviewAdapter?.clearCache()
        previewDataMng?.onDestroy()
        inAnimation?.cancel()
        outAnimation?.cancel()
        inAnimation = null
        outAnimation = null
        handler = null
        mediaPlayer = null
        videoEpsode = null
        playerDataHelp = null
        progressUpdate = null
        filmTryAndSeeEnd = null
        seekToListener = null
        skipEndNext = null
        context = null
        viewGroup = null
        previewRecycler = null
        playerPreviewAdapter = null
        previewDataMng = null
    }

    override fun isShown(): Boolean {
        return viewGroup?.isShown ?: false
    }


}