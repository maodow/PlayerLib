package tv.huan.hwsystemsdk.player.presenter


import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.huan.player.R
import com.huan.player.constant.NetSpeedRate
import com.huan.player.constant.Utils
import com.huan.player.contract.BufferViewContract
import com.huan.player.ui.HandlerProgress

class BufferViewPresenter : BufferViewContract {

    private var viewGroup: ViewGroup? = null
    private var playerLayout1: ConstraintLayout? = null
    private var playerLayout2: LinearLayout? = null
    private var bufferDownloadingSpeed: TextView? = null
    private var bufferDownloadingSpeed2: TextView? = null
    private var bufferTitle: TextView? = null
    private var bufferHint: TextView? = null
    private var playerLoading: ImageView? = null
    private var netSpeed: Long = 0


    override fun onCreate(context: Context, viewGroup: ViewGroup, handler: HandlerProgress?) {
        this.viewGroup = viewGroup
        val mView = View.inflate(context, R.layout.sdk_buffer_view, viewGroup)
        playerLayout1 = mView.findViewById<ConstraintLayout>(R.id.player_layout1)
        playerLayout2 = mView.findViewById<LinearLayout>(R.id.player_layout2)
        bufferDownloadingSpeed = mView.findViewById<TextView>(R.id.buffer_downloading_speed)
        bufferTitle = mView.findViewById<TextView>(R.id.buffer_title)
        bufferHint = mView.findViewById<TextView>(R.id.buffer_hint)
        playerLoading = mView.findViewById<ImageView>(R.id.player_loading)

        bufferDownloadingSpeed2 = mView.findViewById<TextView>(R.id.buffer_downloading_speed2)

    }

    /**
     * stepTime  两秒执行一次
     * 犹豫进度条是一秒更新一次
     */
    override fun setNetSpeedRate() {
        netSpeed = NetSpeedRate.getNetSpeed(Utils.nullToInt(Utils.getApp()?.applicationInfo?.uid))
        if (Utils.null2False(bufferDownloadingSpeed?.isShown))
            bufferDownloadingSpeed?.setText("${NetSpeedRate.FormetFileSize(netSpeed * 1024)}/s")
        if (Utils.null2False(bufferDownloadingSpeed2?.isShown))
            bufferDownloadingSpeed2?.setText("${NetSpeedRate.FormetFileSize(netSpeed * 1024)}/s")
    }

    override fun getNetSpeedRate(): Long {
        return netSpeed
    }

    /**
     *视频的缓冲
     * @param isBufferStart  true 开始缓冲  false 结束缓冲
     */
    override fun onBuffer(isBufferStart: Boolean) {
        if (isBufferStart) {
            show()
        } else {
            hide()
        }
    }

    /**
     * 全屏小屏切换
     * @param isSmall true 小屏  false 全屏
     */
    override fun onScreenChange(isSmall: Boolean) {
        if (isSmall) {
            setTextSize(16f, 12f, 8f)
            loadingImageSize(15, 15)
        } else {
            setTextSize(26f, 18f, 12f)
            loadingImageSize(25, 25)
        }
    }

    override fun hide(tag: Int?) {
        viewGroup?.visibility = View.GONE
    }

    /**
     * 显示缓冲页面
     * @param tag 0标示缓冲界面。“标题内容”  标示切换界面的前置页面
     */
    override fun show(tag: String?) {
        viewGroup?.visibility = View.VISIBLE
        if ("0".equals(tag)) {
            playerLayout1?.visibility = View.GONE
            playerLayout2?.visibility = View.VISIBLE
        } else {
            playerLayout1?.visibility = View.VISIBLE
            playerLayout2?.visibility = View.GONE
            bufferTitle?.setText(tag)
        }

    }

    override fun onDestroy() {
        NetSpeedRate.onClose()
        viewGroup?.removeAllViews()
        viewGroup = null
    }

    override fun isShown(): Boolean {
        return viewGroup?.isShown ?: false
    }

    private fun loadingImageSize(width: Int, height: Int) {
        val layoutParams = playerLoading?.layoutParams
        layoutParams?.width = Utils.dp2px(width.toFloat())
        layoutParams?.height = Utils.dp2px(height.toFloat())
        playerLoading?.layoutParams = layoutParams
    }

    private fun setTextSize(titleSize: Float, hintSize: Float, speedSize: Float) {
        bufferTitle?.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            Utils.sp2px(titleSize).toFloat()
        )
        bufferHint?.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            Utils.sp2px(hintSize).toFloat()
        )
        bufferDownloadingSpeed?.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            Utils.sp2px(speedSize).toFloat()
        )
        bufferDownloadingSpeed2?.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            Utils.sp2px(speedSize).toFloat()
        )
    }
}