package com.hw.demoplayer

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.huan.player.contract.DetainmentViewContract
import com.huan.player.ui.HandlerProgress

class DetainmentViewImpl : DetainmentViewContract {
    private var viewGroup: ViewGroup? = null

    override fun setButtonListener(onButtonClick: (isExit: Boolean) -> Unit) {

    }

    override fun setPlayerReport(onClick: () -> Unit) {

    }

    override fun onCreate(context: Context, viewGroup: ViewGroup, handler: HandlerProgress?) {
        this.viewGroup = viewGroup
        val view = View.inflate(context, R.layout.detainment_view, viewGroup)
    }

    override fun hide(tag: Int?) {
        viewGroup?.visibility = View.GONE
    }

    override fun show(tag: String?) {
        viewGroup?.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        viewGroup?.removeAllViews()
        viewGroup = null
    }

    override fun isShown(): Boolean {
        return viewGroup?.isShown ?: false
    }
}