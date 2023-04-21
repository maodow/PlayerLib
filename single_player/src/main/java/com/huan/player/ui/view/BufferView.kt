package com.huan.player.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import com.huan.player.contract.BufferViewContract
import com.huan.player.ui.HandlerProgress
import tv.huan.hwplayer.config.HWPlayerSettingOptions
import tv.huan.hwsystemsdk.player.presenter.BufferViewPresenter

class BufferView : LinearLayout,
    BufferViewContract {
    private var presenter: BufferViewContract? = null

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
        presenter = HWPlayerSettingOptions.INSTANCE.buffView
        if (presenter == null) {
            presenter = BufferViewPresenter()
        }
        presenter?.onCreate(context, viewGroup)
    }

    /**
     * stepTime  一秒更新一次
     */
    override fun setNetSpeedRate() {
        presenter?.setNetSpeedRate()
    }

    override fun getNetSpeedRate(): Long {
        return presenter?.getNetSpeedRate() ?: 0
    }

    /**
     *视频的缓冲
     * @param isBufferStart  true 开始缓冲  false 结束缓冲
     */
    override fun onBuffer(isBufferStart: Boolean) {
        presenter?.onBuffer(isBufferStart)
    }

    /**
     * 全屏小屏切换
     * @param isSmall true 小屏  false 全屏
     */
    override fun onScreenChange(isSmall: Boolean) {
        presenter?.onScreenChange(isSmall)
    }


    override fun hide(tag: Int?) {
        presenter?.hide(tag)
    }

    /**
     * 显示缓冲页面
     * @param tag 0标示缓冲界面。“标题内容”  标示切换界面的前置页面
     */
    override fun show(tag: String?) {
        presenter?.show(tag)
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        presenter = null
    }

}