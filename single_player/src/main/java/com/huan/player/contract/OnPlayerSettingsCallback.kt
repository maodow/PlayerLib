package com.huan.player.contract

import android.widget.ImageView
import com.huan.player.ui.PlayerDataHelp

interface OnPlayerSettingsCallback {

    /**
     * 获取会员状态
     */
    fun getAuth(): Int

    /**
     * 是否使用局方的播放地址
     */
    fun isThirdPartyPlayerUrl(): Boolean

    /**
     * 盒子的平台，决定播放器地址使用哪个字段
     * @return hw 华为  zx 中兴
     */
    fun getBoxPlatform(): String


    /**
     * 播放地址要凭借的参数
     * 自己判断？还是 &开头
     */
    fun getPlayerUrlParameter(): String

    /**
     * 是否设置播放器的logo
     */
    fun isPlayerLogo(): Boolean

    /**
     * 播放器logo的图片地址
     */
    fun getPlayerLogoUrl(): String?

    /**
     * 处理海报的图片并返回
     */
    fun onPosterImgUrl(playerDataHelp: PlayerDataHelp?): String?

    /**
     * 图片的加载器
     * 获取获取的图片地址的大小
     * -1 不需要拼接
     */
    fun onImgLoading(view: ImageView?, url: String?, imgSize: Int)


    /**
     * 触发支付
     */
    fun onPlayerStartPay(playerDataHelp: PlayerDataHelp?, success: () -> Unit, error: () -> Unit)


    /**
     * 收集日志服务是否开启
     */
    fun isLogRuning(): Boolean

    /**
     * 触发上传日志
     */
    fun onUpdateLog(playerDataHelp: PlayerDataHelp?, success: () -> Unit, error: () -> Unit)


    /**
     * 报错弹框触发
     * @param feedback 是否长传了日志 0 没有 1 上传了
     * @param errorCode 错误码
     * @param msg 错误信息
     */
    fun onErrorLogReport(
        playerDataHelp: PlayerDataHelp?, feedback: Int, errorCode: Long?,
        msg: String?
    )
}