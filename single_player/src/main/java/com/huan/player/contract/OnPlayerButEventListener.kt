package com.huan.player.contract

import com.huan.player.ui.PlayerDataHelp

interface OnPlayerButEventListener {
    /**
     * 切换下一集的按钮
     */
    fun onNextEpisode(playerDataHelp: PlayerDataHelp?)

    /**
     * 跳转到首页的按钮
     */
    fun onStartHome(playerDataHelp: PlayerDataHelp?)

    /**
     * 跳转历史
     */
    fun onStartHistory(playerDataHelp: PlayerDataHelp?)


    /**
     * 跳转收藏
     */
    fun onStartCollect(playerDataHelp: PlayerDataHelp?)

    /**
     * 跳转搜索
     */
    fun onStartSearch(playerDataHelp: PlayerDataHelp?)
}