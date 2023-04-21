package tv.huan.hwsystemsdk.player.adapter


import android.graphics.Color
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.huan.player.R
import com.huan.player.config.PlayerSettingsShare
import com.huan.player.constant.Utils
import com.huan.player.contract.HWSDKPlayerContract
import com.huan.player.contract.PlayerViewContract
import com.huan.player.ui.SwitchImageView

/**
 * 画面比例功能
 */
class PlayerVarietyItem2Adapter(title: String) : BaseItemAdapter<PlayerViewContract>(title),
    View.OnKeyListener, View.OnFocusChangeListener {
    private var titleName: TextView? = null
    private var titleIcon: ImageView? = null
    private var playerRatioLayout: LinearLayout? = null
    private var playerQuanLS: LinearLayout? = null
    private var playerYSBL: LinearLayout? = null

    private var playTextRatioI: SwitchImageView? = null
    private var playTextRatioT: TextView? = null
    private var playTextRatioI1: SwitchImageView? = null
    private var playTextRatioT1: TextView? = null
    private var mediaPlayer: PlayerViewContract? = null

    override fun getLayoutId(): Int = R.layout.sdk_aspect_ratio_view

    override fun onCreateView() {
        titleIcon = getView<ImageView>(R.id.icon_img2)
        titleName = getView<TextView>(R.id.icon_name2)

        playerRatioLayout = getView<LinearLayout>(R.id.player_ratio_layout)

        playerQuanLS = getView<LinearLayout>(R.id.player_quan_ls)
        playTextRatioI = getView<SwitchImageView>(R.id.play_text_ratio_i)
        playTextRatioT = getView<TextView>(R.id.play_text_ratio_t)

        playerYSBL = getView<LinearLayout>(R.id.player_y_s_b_l)
        playTextRatioI1 = getView<SwitchImageView>(R.id.play_text_ratio_i1)
        playTextRatioT1 = getView<TextView>(R.id.play_text_ratio_t1)

        titleName?.setText(title)

        playerQuanLS?.setOnClickListener {
            val aspectRatio = PlayerSettingsShare.INSTANCE.getAspectRatio()
            if (aspectRatio == HWSDKPlayerContract.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT)
                return@setOnClickListener
            hideAspectRatio()
            PlayerSettingsShare.INSTANCE.setAspectRatio(HWSDKPlayerContract.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT)
            mediaPlayer?.setAspectRatio(HWSDKPlayerContract.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT)
            showAspectRatio()
        }

        playerYSBL?.setOnClickListener {
            val aspectRatio = PlayerSettingsShare.INSTANCE.getAspectRatio()
            if (aspectRatio == HWSDKPlayerContract.VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER)
                return@setOnClickListener
            hideAspectRatio()
            PlayerSettingsShare.INSTANCE.setAspectRatio(HWSDKPlayerContract.VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER)
            mediaPlayer?.setAspectRatio(HWSDKPlayerContract.VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER)
            showAspectRatio()
        }

        playerQuanLS?.setOnFocusChangeListener(this)
        playerYSBL?.setOnFocusChangeListener(this)
    }


    override fun onBindItem(position: Int, data: PlayerViewContract?) {
        this.mediaPlayer = data
    }


    override fun spread(position: Int): Boolean {
        val spread = super.spread(position)
        if (spread) {
            showAspectRatio()
            getParentLayout()?.requestFocusFromTouch()
        }
        return spread
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer = null
    }


    override fun getSpreadAnimView(): ViewGroup? = playerRatioLayout

    /**
     * 标题设置透明度
     */
    override fun onAlphaTitle(alpha: Float) {
        titleIcon?.alpha = alpha
        titleName?.alpha = alpha
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        val aspectRatio = getAspectRatio(v?.id)
        if (Utils.null2False(getImgView(aspectRatio)?.isShown)) {
            getImgView(aspectRatio)?.setSwitch(hasFocus)
            if (hasFocus) {
                getTextView(aspectRatio)?.setTextColor(Color.WHITE)
            } else {
                val color = Utils.getColor(R.color.sdk_skip_head_text)
                getTextView(aspectRatio)?.setTextColor(color)
            }
        }
    }


    private fun showAspectRatio() {
        val aspectRatio = PlayerSettingsShare.INSTANCE.getAspectRatio()
        getImgView(aspectRatio)?.visibility = View.VISIBLE
        getImgView(aspectRatio)?.setSwitch(true)
        getTextView(aspectRatio)?.setTextColor(Color.WHITE)
    }


    private fun hideAspectRatio() {
        val aspectRatio = PlayerSettingsShare.INSTANCE.getAspectRatio()
        getImgView(aspectRatio)?.visibility = View.GONE
        getTextView(aspectRatio)?.setTextColor(Color.WHITE)
    }

    private fun getImgView(aspectRatio: Int): SwitchImageView? {
        if (aspectRatio == HWSDKPlayerContract.VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER) {
            return playTextRatioI1
        }
        return playTextRatioI
    }

    private fun getTextView(aspectRatio: Int): TextView? {
        if (aspectRatio == HWSDKPlayerContract.VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER) {
            return playTextRatioT1
        }
        return playTextRatioT
    }

    private fun getParentLayout(): LinearLayout? {
        val aspectRatio = PlayerSettingsShare.INSTANCE.getAspectRatio()
        if (aspectRatio == HWSDKPlayerContract.VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER) {
            return playerYSBL
        }
        return playerQuanLS
    }


    private fun getAspectRatio(viewId: Int?): Int {
        if (viewId == R.id.player_quan_ls)
            return HWSDKPlayerContract.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT
        return HWSDKPlayerContract.VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER
    }

}