package com.huan.player.ui.view

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.huan.player.R
import com.huan.player.ui.BaseDialog
import com.huan.player.ui.PlayerDataHelp

class PlayerErrorDialog(context: Context, var playerDataHelp: PlayerDataHelp?) :
    BaseDialog(context, BaseDialog.POP_UP_THEME) {

    private var popConten: TextView
    private var popButtonLeft: Button
    private var popButtonRight: Button
    private var feedbackParent: LinearLayout
    private var buttonParent: LinearLayout
    private var cid: String? = ""
    private var vid: String? = ""
    private var title: String? = ""
    private var encrypt: Int? = 0
    private var errorCode: Long = 0
    private var msg: String? = ""
    private var isFeedback: Boolean = false

    private var rightClick: (() -> Unit)? = null

    override fun onLayoutId(): Int = R.layout.sdk_player_error_dialog

    init {
        popConten = findViewById<TextView>(R.id.player_pop_content)
        popButtonLeft = findViewById<Button>(R.id.player_pop_button_left)
        popButtonRight = findViewById<Button>(R.id.player_pop_button_right)

        feedbackParent = findViewById<LinearLayout>(R.id.load_feedback)
        buttonParent = findViewById<LinearLayout>(R.id.player_button_parent)

        popButtonLeft?.setOnClickListener {
            feedbackParent.visibility = View.VISIBLE
            popConten.visibility = View.GONE
            buttonParent.visibility = View.GONE

            playerDataHelp?.onPlayerSettingsCallback?.onUpdateLog(playerDataHelp, success = {
                feedbackParent.visibility = View.GONE
                popConten.visibility = View.VISIBLE
                buttonParent.visibility = View.VISIBLE
                popButtonLeft.visibility = View.GONE
                popButtonRight.setText("确认")
                popConten.setText("感谢您的反馈 ^_^")
                isFeedback = true
                feedback(1)
            }, error = {
                feedbackParent.visibility = View.GONE
                buttonParent.visibility = View.VISIBLE
                popConten.visibility = View.VISIBLE
                popConten.setText("反馈失败")
                popButtonLeft.setText("重试")
                popButtonRight.setText("取消")
            })
        }
        popButtonRight?.setOnClickListener {
            dismiss()
            if (!isFeedback)
                feedback(0)
            if (rightClick != null)
                rightClick?.invoke()
            isFeedback = false
        }
    }

    fun setOnClickListener(rightClick: (() -> Unit)?) {
        if (rightClick != null)
            this.rightClick = rightClick
    }


    fun showLog(
        cid: String?,
        vid: String?,
        title: String?,
        encrypt: Int?, errorCode: Long, msg: String?
    ) {
        if (TextUtils.isEmpty(msg)) return
        this.cid = cid
        this.vid = vid
        this.title = title
        this.encrypt = encrypt
        this.msg = msg
        this.errorCode = errorCode
        popConten.setText(msg)

        if (playerDataHelp?.onPlayerSettingsCallback?.isLogRuning() == true) {
            popButtonLeft.visibility = View.VISIBLE
            popButtonLeft.setText("反馈")
            popButtonRight.setText("取消")
        } else {
            popButtonLeft.visibility = View.GONE
            popButtonRight.setText("确认")
        }
        show()
    }

    private fun feedback(feedback: Int) {
        playerDataHelp?.onPlayerSettingsCallback?.onErrorLogReport(playerDataHelp,feedback,errorCode,msg)
    }

    fun onDestroy() {
        rightClick = null
    }
}