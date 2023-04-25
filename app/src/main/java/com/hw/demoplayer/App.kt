package com.hw.demoplayer

import android.app.Application
import android.content.Context
import tv.huan.hwplayer.config.HWPlayerSettingOptions

class App: Application() {

    private var mContext: Context? = null

    override fun onCreate() {
        super.onCreate()
        init(applicationContext)
        HWPlayerSettingOptions.INSTANCE
            .init(this, true)
            .setLogEnabled(true)
            .setDetainmentView(DetainmentViewImpl())
    }

    fun getContext(): Context? {
        return mContext
    }

    /**
     * 如果继承此Application，则在application中调用次初始化方法
     * @param context 上下文
     */
    fun init(context: Context) {
        this.mContext = context
    }

}