package com.hw.demoplayer

import android.util.Log
import com.huan.player.contract.OnPlayerButEventListener
import com.huan.player.ui.PlayerDataHelp

class OnPlayerButEventImpl: OnPlayerButEventListener {
    override fun onNextEpisode(playerDataHelp: PlayerDataHelp?) {
        Log.d("TAGTAG","=更多操作———— 下一集 ——按钮=====")
    }

    override fun onStartHome(playerDataHelp: PlayerDataHelp?) {
        Log.d("TAGTAG","=更多操作———— 跳转的首页=====")
    }

    override fun onStartHistory(playerDataHelp: PlayerDataHelp?) {
        Log.d("TAGTAG","=更多操作———— 跳转的历史页=====")
    }

    override fun onStartCollect(playerDataHelp: PlayerDataHelp?) {
        Log.d("TAGTAG","=更多操作———— 跳转的收藏页=====")
    }

    override fun onStartSearch(playerDataHelp: PlayerDataHelp?) {
        Log.d("TAGTAG","=更多操作———— 跳转的搜索页=====")
    }
}