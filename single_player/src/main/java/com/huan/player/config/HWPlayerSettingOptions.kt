package tv.huan.hwplayer.config

import android.content.Context
import com.huan.player.config.MarkConfigHelp
import com.huan.player.constant.Logcat
import com.huan.player.constant.Utils
import com.huan.player.contract.BufferViewContract
import com.huan.player.contract.ControllerViewContract
import com.huan.player.contract.DetainmentViewContract
import com.huan.player.ui.PlayerDataHelp
import com.huan.player.ui.view.VideoEpisodeChoiceView
import tv.huan.hwplayer.constant.SDKPlayerType

class HWPlayerSettingOptions private constructor() {

    var sdkPlayerType: SDKPlayerType? = SDKPlayerType.SDK_TYPE_ANDROID
        private set

    //当屏幕中有多个视频是请使用VIEW_TYPE_TEXTURE模式，
    // VIEW_TYPE_SURFACE只容许屏幕中有一个视频视图，并要先设置资源在启动，要不会第一次闪烁黑屏
    var viewPlayerType: SDKPlayerType? = SDKPlayerType.VIEW_TYPE_SURFACE
        private set

    var isUsingMediaCodec: Boolean = false
        private set
    var isUsingOpenSLES: Boolean = true
        private set
    var pixelFormat: String = ""
        private set
    var isStartOnPrepared: Boolean = false
        private set
    var logEnabled: Boolean = true
        private set
    var isCdnOpen: Boolean = false
        private set
    var intranet: Boolean = false  // 是否是内网环境
        private set

    var markConfigHelp: MarkConfigHelp? = null
        private set

    /**
     * 自己定义UI
     */
    var buffView: BufferViewContract? = null
        private set
    var controllerView: ControllerViewContract? = null
        private set
    var detainmentView: DetainmentViewContract? = null
        private set
    var videoEpisodeChoiceView: VideoEpisodeChoiceView? = null
        private set

    /**
     * 设置播放器展示视图的对象
     *  @param viewPlayerType view对象枚举
     */
    fun setViewPlayerType(viewPlayerType: SDKPlayerType?): HWPlayerSettingOptions {
        this.viewPlayerType = viewPlayerType
        return this
    }

    /**
     * 设置使用哪种引擎的播放器
     * @param sdkPlayerType  引擎对象枚举
     */
    fun setSdkPlayerType(sdkPlayerType: SDKPlayerType?): HWPlayerSettingOptions {
        this.sdkPlayerType = sdkPlayerType
        return this
    }

    /**
     * 音频输出是否开启OpenSLES
     */
    fun setUsingOpenSLES(isUsingOpenSLES: Boolean): HWPlayerSettingOptions {
        this.isUsingOpenSLES = isUsingOpenSLES
        return this
    }

    /**
     * 是否开启媒体解码（硬解码）
     */
    fun setUsingMediaCodec(isUsingMediaCodec: Boolean): HWPlayerSettingOptions {
        this.isUsingMediaCodec = isUsingMediaCodec
        return this
    }

    /**
     * 设置像素格式  默认是fcc-rv32
     *  @param pixelFormat  fcc-_es2、fcc-i420、fcc-yv12、fcc-rv16、fcc-rv24、fcc-rv32、fcc-custom
     */
    fun setPixelFormat(pixelFormat: String): HWPlayerSettingOptions {
        this.isUsingMediaCodec = isUsingMediaCodec
        return this
    }

    /**
     * 准备就绪是否自动播放，默认不启动 只支持ijkplayer播放器
     * 由于系统播放器不支持，自己已经另一种方式实现，此方法不用设置
     *  @param isStartOnPrepared
     */
    fun setStartOnPrepared(isStartOnPrepared: Boolean): HWPlayerSettingOptions {
        this.isStartOnPrepared = isStartOnPrepared
        return this
    }

    fun setLogEnabled(logEnabled: Boolean): HWPlayerSettingOptions {
        this.logEnabled = logEnabled
        Logcat.debug = logEnabled
        return this
    }

    /**
     * 是否使用Cdn地址
     */
    fun setExternalCdnMode(isCdnOpen: Boolean?): HWPlayerSettingOptions {
        this.isCdnOpen = Utils.null2False(isCdnOpen)
        return this
    }

    fun setVideoDisplayType(type: Int): HWPlayerSettingOptions {
        VIDEO_IMAGE_DISPLAY_TYPE = type
        return this
    }

    /**
     * @param intranet  内网还是外网，用于播放器选择用那个字段
     */
    fun init(application: Context, intranet: Boolean):HWPlayerSettingOptions {
        this.intranet = intranet
        Utils.setContext(application.applicationContext)
        return this
    }

    fun setBuffView(buffView: BufferViewContract?): HWPlayerSettingOptions {
        this.buffView = buffView
        return this
    }

    fun setControllerView(controllerView: ControllerViewContract?): HWPlayerSettingOptions {
        this.controllerView = controllerView
        return this
    }

    fun setDetainmentView(detainmentView: DetainmentViewContract?): HWPlayerSettingOptions {
        this.detainmentView = detainmentView
        return this
    }

    fun setVideoEpisodeChoiceView(videoEpisodeChoiceView: VideoEpisodeChoiceView?): HWPlayerSettingOptions {
        this.videoEpisodeChoiceView = videoEpisodeChoiceView
        return this
    }

    fun setMarkConfigHelp(markConfigHelp: MarkConfigHelp): HWPlayerSettingOptions {
        this.markConfigHelp = markConfigHelp
        return this
    }

    companion object {
        const val VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER = 0 //DEFAULT 标准的(原始比例)
        const val VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT = 1  // 填满父布局
        const val VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP = 2  // 填充裁剪
        const val VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL = 3  //原图
        const val VIDEO_16_9_TYPE_ADAPTER = 4  // 16:9
        const val VIDEO_4_3_TYPE_ADAPTER = 5  // 4:3

        var VIDEO_IMAGE_DISPLAY_TYPE = 0
            private set

        @get:Synchronized
        val INSTANCE: HWPlayerSettingOptions by lazy {
            HWPlayerSettingOptions()
        }
    }
}