package tv.huan.hwsystemsdk.player.adapter


import android.graphics.Color
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.huan.player.R
import com.huan.player.config.PlayerSettingsShare
import com.huan.player.constant.Utils
import com.huan.player.ui.PlayerDataHelp
import com.huan.player.ui.SwitchImageView

/**
 * 更多功能Item
 */
class PlayerVarietyItem0Adapter(title: String) : BaseItemAdapter<PlayerDataHelp>(title),
    View.OnKeyListener {

    private var onReportClick: (() -> Unit)? = null
    private var next: (() -> Unit)? = null

    private var titleName: TextView? = null
    private var titleIcon: ImageView? = null
    private var playerMoreNext: TextView? = null
    private var playerMoreSkipHead: LinearLayout? = null
    private var playerMoreHome: TextView? = null
    private var playerMoreHistory: TextView? = null
    private var playerMoreCollect: TextView? = null
    private var playerMoreSearch: TextView? = null

    private var imgControlSkipHead: SwitchImageView? = null
    private var textControlSkipHead: TextView? = null
    private var playerMoreLayout: LinearLayout? = null
    private var playerDataHelp: PlayerDataHelp? = null

    override fun getLayoutId(): Int = R.layout.sdk_more_view

    override fun onCreateView() {
        titleIcon = getView<ImageView>(R.id.icon_img0)
        titleName = getView<TextView>(R.id.icon_name0)
        playerMoreLayout = getView<LinearLayout>(R.id.player_more_layout)
        playerMoreNext = getView<TextView>(R.id.player_more_next)
        playerMoreSkipHead = getView<LinearLayout>(R.id.player_more_skip_head)
        imgControlSkipHead = getView<SwitchImageView>(R.id.img_control_skip_head)
        textControlSkipHead = getView<TextView>(R.id.text_control_skip_head)

        playerMoreHome = getView<TextView>(R.id.player_more_home)
        playerMoreHistory = getView<TextView>(R.id.player_more_history)
        playerMoreCollect = getView<TextView>(R.id.player_more_collect)
        playerMoreSearch = getView<TextView>(R.id.player_more_search)

        titleName?.setText(title)

        playerMoreNext?.setOnKeyListener(this)
        playerMoreHome?.setOnKeyListener(this)
        playerMoreSkipHead?.setOnKeyListener(this)
        playerMoreHistory?.setOnKeyListener(this)
        playerMoreCollect?.setOnKeyListener(this)
        playerMoreSearch?.setOnKeyListener(this)

        playerMoreSkipHead?.setOnClickListener {
            val skipHead = PlayerSettingsShare.INSTANCE.isSkipHead()
            PlayerSettingsShare.INSTANCE.setSkipHead(!skipHead)
            setSkipHead()
            imgControlSkipHead?.setSwitch(true)
            textControlSkipHead?.setTextColor(Color.WHITE)
        }

        playerMoreSkipHead?.setOnFocusChangeListener { v, hasFocus ->
            if (Utils.null2False(imgControlSkipHead?.isShown)) {
                imgControlSkipHead?.setSwitch(hasFocus)
                if (hasFocus) {
                    textControlSkipHead?.setTextColor(Color.WHITE)
                } else {
                    val color = Utils.getColor(R.color.sdk_skip_head_text)
                    textControlSkipHead?.setTextColor(color)
                }
            }
        }
    }


    override fun onBindItem(position: Int, data: PlayerDataHelp?) {
        this.playerDataHelp = data
    }

    private fun setSkipHead() {
        val color = Utils.getColor(R.color.sdk_skip_head_text)
        if (PlayerSettingsShare.INSTANCE.isSkipHead()) {
            imgControlSkipHead?.visibility = View.VISIBLE
            imgControlSkipHead?.setSwitch(false)
            textControlSkipHead?.setTextColor(color)
        } else {
            imgControlSkipHead?.visibility = View.GONE
            textControlSkipHead?.setTextColor(Color.WHITE)
        }
    }

    /**
     * 上报播放记录
     */
    fun setPlayerReport(onReportClick: () -> Unit) {
        this.onReportClick = onReportClick
    }

    /**
     * 点击下一集按钮
     */
    fun setNextEpisodeListener(next: () -> Unit) {
        this.next = next
    }

    override fun spread(position: Int): Boolean {
        val spread = super.spread(position)
        if (spread) {
            setSkipHead()
            playerMoreNext?.requestFocusFromTouch()
        }
        return spread
    }

    override fun onDestroy() {
        super.onDestroy()
        onReportClick = null
        next = null
        playerDataHelp = null
    }

    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        if (p2?.action == KeyEvent.ACTION_UP && (p2?.keyCode == KeyEvent.KEYCODE_ENTER
                    || p2?.keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
        ) {
            when (p0?.id) {
                R.id.player_more_next -> {
                    if (playerDataHelp?.medias != null && playerDataHelp?.currentMediaIndex == playerDataHelp?.medias!!.size - 1) {
                        Utils.toast("已经是最后一集")
                        return true
                    }
                    if (next != null) {
                        next?.invoke()
                    }
                    playerDataHelp?.getNextEpisode()
                    playerDataHelp?.onPlayerButEventListener?.onNextEpisode(playerDataHelp)
                }
                R.id.player_more_home -> {
                    if (onReportClick != null) {
                        onReportClick?.invoke()
                    }
                    playerDataHelp?.onPlayerButEventListener?.onStartHome(playerDataHelp)
                }
                R.id.player_more_history -> {
                    if (onReportClick != null) {
                        onReportClick?.invoke()
                    }
                    playerDataHelp?.onPlayerButEventListener?.onStartHistory(playerDataHelp)
                }
                R.id.player_more_collect -> {
                    if (onReportClick != null) {
                        onReportClick?.invoke()
                    }
                    playerDataHelp?.onPlayerButEventListener?.onStartCollect(playerDataHelp)
                }
                R.id.player_more_search -> {
                    if (onReportClick != null) {
                        onReportClick?.invoke()
                    }
                    playerDataHelp?.onPlayerButEventListener?.onStartSearch(playerDataHelp)
                }
            }
            true
        }
        return false
    }

    override fun getSpreadAnimView(): ViewGroup? = playerMoreLayout

    /**
     * 标题设置透明度
     */
    override fun onAlphaTitle(alpha:Float) {
        titleIcon?.alpha = alpha
        titleName?.alpha = alpha
    }


}