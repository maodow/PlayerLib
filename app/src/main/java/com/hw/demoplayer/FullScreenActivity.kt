package com.hw.demoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huan.player.ui.PlayerDataHelp
import com.huan.player.ui.view.HWPlayer
import com.hw.demoplayer.bean.DataJson

class FullScreenActivity : AppCompatActivity() {
    private var playerDataHelp: PlayerDataHelp? = null
    private var mediaPlayer: HWPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)
        mediaPlayer = findViewById<HWPlayer>(R.id.media_player)



        val data = DataJson()
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
        mediaPlayer?.setStartDataSource(playerDataHelp)

    }

    override fun onDestroy() {
        super.onDestroy()
        playerDataHelp?.onPlayerListener?.onExitPlayer(playerDataHelp)
    }
}