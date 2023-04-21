package com.hw.demoplayer

import android.util.Log
import com.huan.player.contract.OnPlayerListener
import com.huan.player.contract.PlayerViewContract
import com.huan.player.ui.PlayerDataHelp

class OnPlayerListenerImpl: OnPlayerListener {

    override fun onVideoPrepared(
        playerDataHelp: PlayerDataHelp?,
        mediaPlayer: PlayerViewContract?
    ) {
        Log.d("TAGTAG","===onVideoPrepared========")

    }

    override fun onOpenPlayer(playerDataHelp: PlayerDataHelp?) {
        Log.d("TAGTAG","===onOpenPlayer========")

    }

    override fun onPlayerBuffer(isStrat: Boolean, netSpeedRate: Long) {
        Log.d("TAGTAG","===onPlayerBuffer========")

    }

    override fun onPlayerError(errorCode: Int, s: String?) {
        Log.d("TAGTAG","===onPlayerError========")
    }

    override fun onCompletion(playRecord: PlayerDataHelp?) {
        Log.d("TAGTAG","===onCompletion========")

    }

    override fun onSeekTo(isEnd: Boolean, isEnter: Boolean) {
        Log.d("TAGTAG","===onSeekTo========")

    }

    override fun onPlayerPause() {
        Log.d("TAGTAG","===onPlayerPause========")

    }

    override fun onPlayerStart() {
        Log.d("TAGTAG","===onPlayerStart========")

    }

    override fun onPlayerUpdateEpisode(playRecord: PlayerDataHelp?) {
        Log.d("TAGTAG","===onPlayerUpdateEpisode========")
    }

    override fun onExitPlayer(playRecord: PlayerDataHelp?) {
        Log.d("TAGTAG","===onExitPlayer========")

    }

    override fun onPlayerRecord(
        mediaPlayer: PlayerViewContract?,
        playRecord: PlayerDataHelp?,
        endFlag: Int
    ) {
        Log.d("TAGTAG","===onPlayerRecord========")
    }


    override fun setThirdPartyPlayerUrlListener(
        playerDataHelp: PlayerDataHelp?,
        success: (uri: String?) -> Unit?,
        error: () -> Unit?
    ) {
//                success.invoke("http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4")
        success.invoke("http://111.20.105.60:6060/huanwangtengxun/2/movie400c2019111313310017?AuthInfo=u9UjeGNVAiJW136tCGPdf50AoPh8rwrZ6p62Y5tYhvdfnwWDE2fi8E%2Fh3vjjLMDZvV1YkAM0WBFvQrw%2BjkbrjCYcXJWNvv7ss6Ph%2F2a%2BH6jthDM%2Fq3B9I7iLgcoAGpAw&version=v1.0&virtualDomain=huanwangtengxun.vod_hls.zte.com&stbid=F5570300004735900000AC00D0326C6F&terminalflag=1&userid=AC00D0326C6F")
    }

    override fun onDestroy() {
       Log.d("TAGTAG","===onDestroy========")
    }
}