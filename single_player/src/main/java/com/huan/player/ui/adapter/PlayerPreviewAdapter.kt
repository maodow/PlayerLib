package tv.huan.hwsystemsdk.player.adapter

import android.content.Context
import android.view.View
import com.huan.player.R
import com.huan.player.databinding.PlayerPreviewAdapterBinding
import com.huan.player.ui.BaseRecyclerViewAdapter
import com.huan.player.ui.BaseViewHolder
import com.huan.player.ui.preview.PreviewDataMng

class PlayerPreviewAdapter(var context: Context?, var previewDataMng: PreviewDataMng?) :
    BaseRecyclerViewAdapter<String>() {
    var mItemCount: Int = 0
    private var selectedPosition = 0
    private var currentView: View? = null
    private var isFocus = false


    override fun getLayoutId(): Int = R.layout.sdk_player_preview_adapter

    override fun onBindItemHolder(holder: BaseViewHolder?, position: Int) {
        val binding = holder?.viewDataBinding as? PlayerPreviewAdapterBinding
        if (position == selectedPosition) {
           // if (isFocus)
                //FocusBorderHelp.getFocusView(context, currentView, false, 0f, 0f)
           // FocusBorderHelp.getFocusView(context, binding?.root, true, 0f, 0f)
            currentView = binding?.root
            isFocus = true
        }

        val drawable = previewDataMng?.getPreviewDrawableByIdx(position)

        if (drawable != null) {
           // binding?.frescoImageView?.setImageDrawableObject(drawable)
        }

    }

    override fun getItemCount(): Int {
        return mItemCount
    }

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
    }

    fun unfocused() {
       // if (isFocus)
           // FocusBorderHelp.getFocusView(context, currentView, false, 0f, 0f)
        isFocus = false
    }


    fun clearCache() {
        notifyItemRangeRemoved(0, mItemCount)
        currentView = null
        selectedPosition = 0
        isFocus = false
        mItemCount = 0
    }
}