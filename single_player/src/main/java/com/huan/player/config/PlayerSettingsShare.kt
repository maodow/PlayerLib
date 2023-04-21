package com.huan.player.config

import android.content.Context
import android.content.SharedPreferences
import com.huan.player.constant.Utils

class PlayerSettingsShare {
    private val PLAYER_INFO_SHARE: String = "player_info_share"

    private val SKIP_HEAD = "skip_head"
    private val ASPECT_RATION = "aspect_ration"


    companion object {
        val INSTANCE: PlayerSettingsShare by lazy {
            PlayerSettingsShare()
        }
    }

    /**
     * 跳过片头片尾
     */
    fun setSkipHead(isSkipHead: Boolean) {
        getShareObject()?.edit()?.putBoolean(SKIP_HEAD, isSkipHead)?.apply()
    }

    fun isSkipHead(): Boolean {
        return getShareObject()?.getBoolean(SKIP_HEAD, false) ?: false
    }

    /**
     * 画面比例
     */
    fun setAspectRatio(aspectRatio: Int) {
        getShareObject()?.edit()?.putInt(ASPECT_RATION, aspectRatio)?.apply()
    }

    fun getAspectRatio(): Int {
        return getShareObject()?.getInt(ASPECT_RATION, 0)?:0
    }


    private fun getShareObject(): SharedPreferences? {
        return Utils.getApp()?.getSharedPreferences(PLAYER_INFO_SHARE, Context.MODE_PRIVATE)
    }
}