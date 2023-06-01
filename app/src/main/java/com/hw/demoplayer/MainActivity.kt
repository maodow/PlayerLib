package com.hw.demoplayer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.huan.player.ui.PlayerDataHelp
import com.huan.player.ui.view.HWPlayer
import com.hw.demoplayer.bean.AuthRequestBean
import com.hw.demoplayer.bean.DataJson
import com.hw.demoplayer.network.API
import com.hw.demoplayer.network.AuthResponse
import com.hw.demoplayer.network.DefaultObservers
import com.hw.demoplayer.network.RetrofitUtil
import com.hw.demoplayer.utils.LogUtil
import okhttp3.MediaType
import okhttp3.RequestBody

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

    override fun onResume() {
        super.onResume()
        getAuth() //用户鉴权
        findViewById<View>(R.id.go_pay).setOnClickListener {
            startActivity(Intent(this, PayActivity::class.java))
        }
    }

    fun startPlay(view: View) {
        startActivity(Intent(this, FullScreenActivity::class.java))
    }

    fun goPay(view: View) {
        startActivity(Intent(this, PayActivity::class.java))
    }


    fun getAuth() {
        val gson = Gson()
        val requestBean = AuthRequestBean()
        requestBean.account = "5113304547B02"
        requestBean.contentId = "HBGD7707453463196508165369336899" //测试用内容code：99180001000000010000000120648713
        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            gson.toJson(requestBean)
        )

        RetrofitUtil.hull(RetrofitUtil.createService(API::class.java).getAuth(body))
            .subscribe(object : DefaultObservers<AuthResponse?>() {

                override fun onResponse(data: AuthResponse?) {
                    if (data?.data != null) {
                        LogUtil.d("Result ==> "+data.desc)
                    }
                }

                @Override
                override fun onFailure(code: String?, msg: String?): Int {
                    LogUtil.d("onFailure! ==> Code: $code, errorMsg: $msg")
                    return super.onFailure(code, msg)
                }
            })
    }

}