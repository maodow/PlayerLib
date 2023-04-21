package com.huan.player.constant

object PlayerErrorCode {
    const val RESOURECE_FAILURE_0 = 10000
    const val RESOURECE_FAILURE_1 = 10001
    const val RESOURECE_FAILURE_2 = 10002
    const val RESOURECE_FAILURE_3 = 10003

    // 播放器加密类型播放器初始化失败
    const val PLAYER_INIT_FAILURE = 22000

    const val PLAYER_AUTH_FAILURE = 13000

    //加密前缀
    fun addFirst(encrypt: Int?, code: Int): String {
        if (encrypt == null)// 未知类型
            return "${code}22"
        if (encrypt == 1)
            return "${code}11"
        return "${code}00"
    }
}