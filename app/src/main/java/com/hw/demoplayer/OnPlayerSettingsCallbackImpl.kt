package com.hw.demoplayer

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.huan.player.constant.Utils
import com.huan.player.contract.OnPlayerSettingsCallback
import com.huan.player.ui.PlayerDataHelp

class OnPlayerSettingsCallbackImpl : OnPlayerSettingsCallback {
    override fun getAuth(): Int {
        return 1
    }

    override fun isThirdPartyPlayerUrl(): Boolean {
        return false
    }

    override fun getBoxPlatform(): String {
        return "hw"
    }

    override fun getPlayerUrlParameter(): String {
        return ""
    }

    override fun isPlayerLogo(): Boolean {
        return true
    }

    override fun getPlayerLogoUrl(): String? {
        return null
    }

    override fun onPosterImgUrl(playerDataHelp: PlayerDataHelp?): String? {
        return "${playerDataHelp?.album?.newPicHz}"
    }

    override fun onImgLoading(view: ImageView?, url: String?, imgSize: Int) {
        Log.d("TAGTAG","===="+url)
        Glide.with(Utils.getApp()!!).load(url).into(view!!);
    }

    override fun onPlayerStartPay(
        playerDataHelp: PlayerDataHelp?,
        success: () -> Unit,
        error: () -> Unit
    ) {

    }

    override fun isLogRuning(): Boolean {
        return false
    }

    override fun onUpdateLog(
        playerDataHelp: PlayerDataHelp?,
        success: () -> Unit,
        error: () -> Unit
    ) {

    }

    override fun onErrorLogReport(
        playerDataHelp: PlayerDataHelp?,
        feedback: Int,
        errorCode: Long?,
        msg: String?
    ) {
        Log.d("TAGTAG", "错误弹框报错=========$msg")
    }
}