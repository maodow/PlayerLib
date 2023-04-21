package com.huan.player.sdk;

import android.media.AudioManager;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import com.huan.player.constant.Logcat;
import com.huan.player.constant.Utils;

import tv.huan.hwplayer.config.HWPlayerSettingOptions;
import tv.huan.hwplayer.constant.SDKPlayerType;

import tv.danmaku.ijk.media.exo.IjkExoMediaPlayer;
import tv.danmaku.ijk.media.player.AndroidMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public abstract class SDKIjkplayer {
    protected final String TAG = SDKIjkplayer.class.getName();
    protected IMediaPlayer mediaPlayer;
    protected HWPlayerSettingOptions setting;
    protected Surface surface;
    protected SurfaceHolder surfaceHolder;
    protected boolean isInitSDK = true;

    abstract View getPalyerView();

    abstract boolean isSurfaceCreated();

    abstract void setVideoSize(int currentVideoWidth,int  currentVideoHeight,int videoSarNum,int videoSarDen);

    protected SDKIjkplayer() {
        setting = HWPlayerSettingOptions.Companion.getINSTANCE();
        onCreatePlayer();
    }

    public IMediaPlayer onCreatePlayer() {
        try {//选择播放器
            if (setting.getSdkPlayerType() == SDKPlayerType.SDK_TYPE_ANDROID)
                mediaPlayer = new AndroidMediaPlayer();
            if (setting.getSdkPlayerType() == SDKPlayerType.SDK_TYPE_EXO)
                mediaPlayer = new IjkExoMediaPlayer(Utils.INSTANCE.getApp());
            if (setting.getSdkPlayerType() == SDKPlayerType.SDK_TYPE_IJK)
                mediaPlayer = getIjkplayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setScreenOnWhilePlaying(true);
            Logcat.INSTANCE.dTag( "系统播放器初始化成功 ^_^");
        } catch (Exception e) {
            isInitSDK = false;
            Logcat.INSTANCE.eTag( "SDKIjkplayer---初始化失败——————" + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return mediaPlayer;
    }

    private IMediaPlayer getIjkplayer() {
        IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer();
        IjkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_VERBOSE);
        ijkMediaPlayer.setLogEnabled(setting.getLogEnabled());
        return ijkMediaPlayer;
    }

    /**
     * 设置这些参数只能在设置资源之后，并且没有同步资源前设置
     */
    protected void setIjkPlayerOptions() {
        if (!(mediaPlayer instanceof IjkMediaPlayer)) return;
        IjkMediaPlayer ijkMediaPlayer = (IjkMediaPlayer) mediaPlayer;
        //默认未开启 开启媒体解码 0：不开启 1 ：开启
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", setting.isUsingMediaCodec() ? 1 : 0);
        if (setting.isUsingMediaCodec()) {
            // 开启媒体解码时，是否自动旋转 0：不旋转 1 ：旋转
            ijkMediaPlayer.setOption(
                    IjkMediaPlayer.OPT_CATEGORY_PLAYER,
                    "mediacodec-auto-rotate",
                    1
            );
            // 开启媒体解码时，分辨率是否改变  0：不改变 1 ：改变
            ijkMediaPlayer.setOption(
                    IjkMediaPlayer.OPT_CATEGORY_PLAYER,
                    "mediacodec-handle-resolution-change",
                    1
            );
        }

        //默认开启 Android平台使用OpenSL ES或AudioTrack输出音频，iOS平台使用AudioQueue输出
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", setting.isUsingOpenSLES() ? 1 : 0);


        if (TextUtils.isEmpty(setting.getPixelFormat())) {
            ijkMediaPlayer.setOption(
                    IjkMediaPlayer.OPT_CATEGORY_PLAYER,
                    "overlay-format",
                    IjkMediaPlayer.SDL_FCC_RV32
            );
        } else {
            ijkMediaPlayer.setOption(
                    IjkMediaPlayer.OPT_CATEGORY_PLAYER,
                    "overlay-format",
                    setting.getPixelFormat()
            );
        }
        //0:跳帧处理,放CPU处理较慢时，进行跳帧处理，保证播放流程，画面和声音同步
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
        //准备好之后是否自动播放
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", setting.isStartOnPrepared() ? 1 : 0);

        ijkMediaPlayer.setOption(
                IjkMediaPlayer.OPT_CATEGORY_PLAYER,
                "http-detect-range-support",
                0
        );
        //重连次数
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "reconnect", 3);
        //设置是否开启环路过滤: 0开启，画面质量高，解码开销大，48关闭，画面质量差点，解码开销小
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);
        //某些视频在SeekTo的时候，会跳回到拖动前的位置，这是因为视频的关键帧的问题
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1);
        //设置seekTo能够快速seek到指定位置并播放
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "fflags", "fastseek");
        //播放前的探测Size，默认是1M, 改小一点会出画面更快
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probesize", 1024 * 20);
        //如果项目中同时使用了HTTP和HTTPS的视频源的话，要注意如果视频源刚好是相同域名，会导致播放失败，这是由于dns缓存造成的
        // ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "dns_cache_clear", 1);
    }

    protected String getErrorString(int what, int extra) {
        String error = "#" + what + "," + extra;
        switch (extra) {
            case IMediaPlayer.MEDIA_ERROR_MALFORMED:
                error = "比特流不符合相关的编码标准和文件规范";
                break;
            case IMediaPlayer.MEDIA_ERROR_IO:
                error = "本地文件或网络相关错误";
                break;
            case IMediaPlayer.MEDIA_ERROR_TIMED_OUT:
                error = "播放超时错误";
                break;
            case IMediaPlayer.MEDIA_ERROR_SERVER_DIED:
                error = "媒体服务器挂机（应用必须释放对象，然后开启新的实例）";
                break;
            case IMediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                error = "播放错误（一般视频播放比较慢或视频本身有问题会引发）";
                break;
            case -10000:
                error = "视频源有问题或者数据格式不支持";
                break;
            case -2147483648:
                error = "低级系统错误(不支持此格式)";
                break;
            case -19:
                error = "解码失败";
                break;
        }
        switch (what) {
            case IMediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                Logcat.INSTANCE.eTag( "what: 比特流符合相关编码标准或文件规范，但媒体框架不支持该功能");
                break;
            case IMediaPlayer.MEDIA_ERROR_UNKNOWN:
                Logcat.INSTANCE.eTag( "what: 未知错误");
                break;
        }
        return error;
    }

    public void onDestroy() {
        setting = null;
    }

}
