package com.hw.demoplayer;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.hw.demoplayer.network.RetrofitUtil;
import tv.huan.hwplayer.config.HWPlayerSettingOptions;

public class BaseApplication extends Application {

    private static Context mContext = null;

    public static BaseApplication instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init(getApplicationContext());
        HWPlayerSettingOptions.Companion.getINSTANCE()
                .init(this, true)
                .setLogEnabled(true)
                .setDetainmentView(new DetainmentViewImpl());
        RetrofitUtil.init(BuildConfig.BASE_URL);
    }

    /**
     * 如果继承此Application，则在application中调用次初始化方法
     * @param context 上下文
     */
    public static void init(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }

    public static BaseApplication getInsatance(){
        return instance;
    }

    public void logout(){};
}
