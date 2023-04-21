package com.hw.demoplayer

import android.app.Application
import tv.huan.hwplayer.config.HWPlayerSettingOptions

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        HWPlayerSettingOptions.INSTANCE
            .init(this, true)
            .setLogEnabled(true)
            .setDetainmentView(DetainmentViewImpl())
    }
}