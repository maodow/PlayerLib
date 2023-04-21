package com.huan.player.contract


interface DetainmentViewContract:
    BasePlayerViewContract {

    fun setButtonListener(onButtonClick: (isExit: Boolean) -> Unit)

    fun setPlayerReport(onClick: () -> Unit)

}