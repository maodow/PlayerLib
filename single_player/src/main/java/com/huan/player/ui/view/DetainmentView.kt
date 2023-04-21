package com.huan.player.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import com.huan.player.contract.DetainmentViewContract
import com.huan.player.ui.HandlerProgress
import tv.huan.hwplayer.config.HWPlayerSettingOptions
import tv.huan.hwsystemsdk.player.presenter.DetainmentViewPresenter


/**
 * 播放器退出挽留页面
 */
class DetainmentView : LinearLayout,
    DetainmentViewContract {
    private var presenter: DetainmentViewContract? = null

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
        presenter = HWPlayerSettingOptions.INSTANCE.detainmentView
        if (presenter == null) {
            presenter =
                DetainmentViewPresenter()
        }
        presenter?.onCreate(context, viewGroup)
    }


    override fun setButtonListener(onButtonClick: (isExit: Boolean) -> Unit) {
        presenter?.setButtonListener(onButtonClick)
    }

    /**
     * 点击推荐时，上播放记录
     */
    override fun setPlayerReport(onClick: () -> Unit) {
        presenter?.setPlayerReport(onClick)
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
}