package tv.huan.hwsystemsdk.player.adapter

import android.content.Context
import android.view.View
import com.huan.player.R
import com.huan.player.constant.Utils
import com.huan.player.databinding.PlayerEpisodePointingBinding
import com.huan.player.ui.BaseRecyclerViewAdapter
import com.huan.player.ui.BaseViewHolder
import com.huan.player.ui.FocusTextView

class PlayerEpisodePointingAdapter(context: Context) : BaseRecyclerViewAdapter<String>() {
    private var currentFocusView: FocusTextView? = null

    override fun getLayoutId(): Int = R.layout.sdk_player_episode_pointing_adapter

    override fun onBindItemHolder(holder: BaseViewHolder?, position: Int) {
        val binding = holder?.viewDataBinding as? PlayerEpisodePointingBinding
        binding?.content = getDataList()?.get(position)

        (binding?.root as? FocusTextView)?.setOnFocusChanged {
            if (it) {
                currentFocusView = binding?.episodePointingContent
            }
        }
    }


    /**
     * 选择状态复原
     */
    fun selecteStatusReset() {
        currentFocusView?.setTextColor(Utils.getColor(R.color.theme_text))
    }

    /**
     *设置选中状态
     */
    fun setSelecteStatus() {
        currentFocusView?.setTextColor(Utils.getColor(R.color.skin_button_text_selecte))
    }

    fun setSelecteView(selecteView: View?) {
        if (selecteView == null) return
        currentFocusView = selecteView as FocusTextView
        setSelecteStatus()
    }
}