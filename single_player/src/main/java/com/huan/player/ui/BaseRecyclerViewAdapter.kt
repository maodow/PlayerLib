package com.huan.player.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.huan.player.constant.Utils

/**
 * 使用了DataBinding
 */
abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<BaseViewHolder>() {

    private var dataBean: MutableList<T>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflate = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            getLayoutId(),
            parent,
            false
        )
        return BaseViewHolder(inflate)
    }

    override fun getItemCount(): Int = Utils.nullToInt(dataBean?.size)


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindItemHolder(holder, position)
    }


    abstract fun getLayoutId(): Int

    abstract fun onBindItemHolder(holder: BaseViewHolder?, position: Int)


    fun getDataList(): MutableList<T>? {
        return dataBean
    }

    fun setDataBean(dataBean: MutableList<T>?) {
        if (dataBean == null) return
        if (this.dataBean == null || this.dataBean!!.size != dataBean.size) {
            this.dataBean = dataBean
            notifyDataSetChanged()
        } else {
            this.dataBean = dataBean
            notifyItemRangeChanged(0, dataBean!!.size)
        }
    }


    /**
     * 清除数据
     */
    open fun clearDatas() {
        this.dataBean?.clear()
        notifyDataSetChanged()
    }

    /**
     * 追加数据
     */
    open fun appendDatas(datas: MutableList<T>?) {
        if (null == datas) return
        var size = 0
        if (dataBean == null) {
            this.dataBean = mutableListOf()
            this.dataBean?.addAll(datas)
            notifyDataSetChanged()
            return
        } else {
            size = this.dataBean!!.size
            if (size == 0) {
                this.dataBean?.addAll(datas)
                notifyDataSetChanged()
            } else {
                this.dataBean?.addAll(datas)
                notifyItemRangeInserted(size, datas.size)
            }
        }
    }

    /**
     * 移除数据
     * @param postion 索引
     */
    open fun removeItem(position: Int) {
        if (null != dataBean && position < dataBean!!.size) {
            dataBean?.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount+1-position)
        }
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        super.onViewRecycled(holder)
        holder?.getRootView()?.tag = null
    }
}