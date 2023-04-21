package com.hw.demoplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.huan.player.constant.Utils
import com.huan.player.ui.PlayerDataHelp
import com.huan.player.ui.view.HWPlayer
import com.hw.demoplayer.bean.ColumnJson
import com.hw.demoplayer.bean.DataJson
import com.hw.demoplayer.bean.FilmJson
import tv.huan.hwplayer.config.HWPlayerSettingOptions


class MainActivity : AppCompatActivity() {

    private var playerDataHelp: PlayerDataHelp? = null
    private var mediaPlayer: HWPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer = findViewById<HWPlayer>(R.id.media_player)


        val data = DataJson()
        //val data = ColumnJson()
        //val data = FilmJson()
        val program = data.getResponseBean()

        playerDataHelp = PlayerDataHelp.Builder()
            .setAlbum(program?.album)
            .setColumn(program?.column)
            .setMedias(program?.medias)
            .setSectionNum(10)
            .setMoreFunctions(true)
            .setOnPlayerButEventListener(OnPlayerButEventImpl())
            .setOnPlayerSettingsCallback(OnPlayerSettingsCallbackImpl())
            .setOnPlayerListener(OnPlayerListenerImpl())
            .build()

        //设置历史记录
        playerDataHelp?.setHistoryProgress(0,11,0)

        //初始化资源
       // mediaPlayer?.setDataSource(playerDataHelp)
        //初始化资源并播放
        mediaPlayer?.setStartDataSource(playerDataHelp)

         // 播放器下一集
       // playerDataHelp?.getNextEpisode()

        //播放指定集数
        //playerDataHelp?.getNextEpisode(1)
    }


    fun startQuan(view: View) {
        mediaPlayer?.gotoScreenFullscreen()
        mediaPlayer?.startPlayer()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pausePlayer()
    }

    override fun onRestart() {
        super.onRestart()
        mediaPlayer?.startPlayer()
    }

    fun startPlay(view: View) {
        startActivity(Intent(this, FullScreenActivity::class.java))
    }
}