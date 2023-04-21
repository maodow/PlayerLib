package com.huan.player.ui

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.huan.player.R


class SwitchImageView : AppCompatImageView {
    private var defaultImage = 0
    private var selectImage = 0
    private var isSwitchStatus = false
    private var isFocus = false // 是否开启获焦模式

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.SwitchImageView)
        defaultImage = typedArray.getResourceId(R.styleable.SwitchImageView_def, 0)
        selectImage = typedArray.getResourceId(R.styleable.SwitchImageView_sel, 0)
        isFocus = typedArray.getBoolean(R.styleable.SwitchImageView_isFocus, false);
        if (defaultImage != 0)
            setBackgroundResource(defaultImage)
        typedArray.recycle()
    }

    /**
     * @param off false:默认图片  true 选中的图片
     */
    fun setSwitch(off: Boolean) {
        isSwitchStatus = off
        if (off) {
            setBackgroundResource(selectImage);
        } else {
            setBackgroundResource(defaultImage);
        }
    }

    fun isSwitchStatus(): Boolean {
        return isSwitchStatus
    }

    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
        if (isSwitchStatus || !isFocus) return
        if (gainFocus) {
            setBackgroundResource(selectImage);
        } else {
            setBackgroundResource(defaultImage);
        }
    }
}