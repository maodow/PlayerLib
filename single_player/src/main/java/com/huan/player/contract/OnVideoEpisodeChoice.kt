package com.huan.player.contract

import com.huan.player.ui.PlayerDataHelp

interface OnVideoEpisodeChoice {
    fun onPay()
    fun onEpisode(playerDataHelp: PlayerDataHelp)
    fun onVirtualTvskey()
    fun onError(code: Int, msg: String?)
    fun onCompletion()
}