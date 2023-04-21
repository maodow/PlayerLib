package com.huan.player.ui

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder : RecyclerView.ViewHolder {
    var viewDataBinding: ViewDataBinding? = null

    constructor(viewDataBinding: ViewDataBinding) : super(viewDataBinding.root) {
        this.viewDataBinding = viewDataBinding
    }

    fun getRootView(): View? {
        return this.viewDataBinding?.root
    }

    /**
     * 通过该方法获取view
     *
     * @param viewId view id
     * @param <T>    view 对象
    </T> */
    fun <T : View?> getView(viewId: Int): T? {
        val view = getRootView()?.findViewById<View>(viewId)
        if (view == null) return null
        return view as T
    }
}