package com.huan.player.contract


interface BufferViewContract:
    BasePlayerViewContract {


    fun setNetSpeedRate()

    fun  getNetSpeedRate():Long

    fun onBuffer(isBufferStart:Boolean)

    fun onScreenChange(isSmall: Boolean)


}