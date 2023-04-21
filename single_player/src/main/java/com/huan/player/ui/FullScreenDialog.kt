package com.huan.player.ui


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout


class FullScreenDialog : BaseDialog {
    private var playerContainer: FrameLayout? = null
    private lateinit var textView: View
    private var dialogDispatchKeyEvent: ((event: KeyEvent) -> Boolean)? = null
    private var dialogKeyDown: ((keyCode: Int, event: KeyEvent) -> Boolean)? = null


    constructor(
        context: Context,
        playerContainer: FrameLayout?
    ) : super(context, BaseDialog.FULL_THEME) {
        this.playerContainer = playerContainer
    }


    fun showFullScreen() {
        setContentView(playerContainer!!)
        if (!(mContext as Activity).isFinishing)
            show()
    }


    fun dismissFullScreen() {
        setContentView(textView)
        dismiss()
    }

    fun setDialogDispatchKeyEvent(dialogDispatchKeyEvent: (event: KeyEvent) -> Boolean) {
        this.dialogDispatchKeyEvent = dialogDispatchKeyEvent
    }

    fun setDialogKeyDown(dialogKeyDown: (keyCode: Int, event: KeyEvent) -> Boolean) {
        this.dialogKeyDown = dialogKeyDown
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (dialogDispatchKeyEvent != null) {
            if (dialogDispatchKeyEvent!!.invoke(event))
                return true
        }
        return super.dispatchKeyEvent(event)
    }

    override fun onLayoutId(): Int = 0


    override fun onLayoutView(): View? {
        textView = View(context)
        textView.setBackgroundColor(Color.BLACK)
        return null
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (dialogKeyDown != null) {
            if (dialogKeyDown!!.invoke(keyCode, event))
                return true
        }
        return super.onKeyDown(keyCode, event)
    }

    fun onDestroy() {
        mContext = null
        playerContainer = null
        dialogKeyDown = null
        dialogDispatchKeyEvent =null
    }
}