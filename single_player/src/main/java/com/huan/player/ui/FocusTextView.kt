package com.huan.player.ui

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.huan.player.R

/**
 *  犹豫recyclerView 的item 点击会 item 的焦点监听冲突，无法获取
 *  所以重新此方法，自己向外提供
 */
class FocusTextView : AppCompatTextView {
    private var onFocus: ((Boolean) -> Unit?)? = null
    private var visibleType: String? = null
    private var disappearType: String? = null
    private var isHide: Boolean = false
    private var oldVisibleValue: Int = View.VISIBLE

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FocusTextView)
        isHide = typedArray.getBoolean(R.styleable.FocusTextView_isHide, false)
        visibleType = typedArray.getString(R.styleable.FocusTextView_visibleType) ?: "none"
        disappearType = typedArray.getString(R.styleable.FocusTextView_disappearType) ?: "GONE"
        oldVisibleValue = visibility
    }


    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
        onFocus?.let { it(gainFocus) }
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        if (isHide)
            visibility = isVisible(selected)
    }

    fun setOnFocusChanged(onFocus: (focus: Boolean) -> Unit) {
        this.onFocus = onFocus
    }

    fun setHide(isHide: Boolean) {
        this.isHide = isHide
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onFocus = null
    }

    private fun isVisible(gainFocus: Boolean): Int {
        if (visibleType == "focus" && gainFocus)
            return disappearType()
        else if (visibleType == "blur" && !gainFocus)
            return disappearType()
        return oldVisibleValue
    }

    private fun disappearType(): Int {
        if (disappearType == "Gone") {
            return View.GONE
        }
        return View.INVISIBLE
    }
}