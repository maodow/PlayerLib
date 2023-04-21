package com.huan.player.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.ViewGroup
import android.widget.LinearLayout
import com.huan.player.bean.NetVideoInfo
import com.huan.player.contract.PlayerViewContract
import com.huan.player.contract.VideoEpisodeChoiceViewContract
import com.huan.player.ui.HandlerProgress
import com.huan.player.ui.PlayerDataHelp
import tv.huan.hwplayer.config.HWPlayerSettingOptions
import tv.huan.hwsystemsdk.player.presenter.VideoEpisodeChoiceViewPresenter

/**
 * 选集控制器
 */
class VideoEpisodeChoiceView : LinearLayout,
    VideoEpisodeChoiceViewContract {
    private var presenter: VideoEpisodeChoiceViewContract? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        onCreate(context!!, this)
    }

    override fun onCreate(context: Context, viewGroup: ViewGroup, handler: HandlerProgress?) {
        presenter = HWPlayerSettingOptions.INSTANCE.videoEpisodeChoiceView
        if (presenter == null) {
            presenter =
                VideoEpisodeChoiceViewPresenter()
        }
        presenter?.onCreate(context, viewGroup)
    }

    override fun setData(playerDataHelp: PlayerDataHelp?, mediaPlayer: PlayerViewContract?) {
        presenter?.setData(playerDataHelp,mediaPlayer)
    }

    override fun onVideoPrepared(playerView: PlayerViewContract?, playerDataHelp: PlayerDataHelp?) {
        presenter?.onVideoPrepared(playerView,playerDataHelp)
    }

    override fun setDefnInfo(videoInfo: NetVideoInfo?) {
        presenter?.setDefnInfo(videoInfo)
    }

    override fun setPlayerReport(onClick: () -> Unit) {
        presenter?.setPlayerReport(onClick)
    }

    override fun onUpdateEpisodeListener() {
        presenter?.onUpdateEpisodeListener()
    }

    override fun setNextEpisodeListener(next: () -> Unit) {
        presenter?.setNextEpisodeListener(next)
    }


    override fun hide(tag: Int?) {
        presenter?.hide(tag)
    }

    override fun show(tag: String?) {
        presenter?.show(tag)
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        presenter = null
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (presenter?.dispatchKeyEvent(event) == true)
            return true
        return super.dispatchKeyEvent(event)
    }

}