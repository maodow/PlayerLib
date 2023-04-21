package tv.huan.hwsystemsdk.player.adapter

import android.content.Context
import android.text.TextUtils
import android.view.View
import com.huan.player.R
import com.huan.player.bean.Media
import com.huan.player.databinding.PlayerVarietyEpisodeBinding
import com.huan.player.ui.BaseRecyclerViewAdapter
import com.huan.player.ui.BaseViewHolder
import com.huan.player.ui.PlayerDataHelp
import tv.huan.hwplayer.config.HWPlayerSettingOptions

class PlayerVarietyEpisodeAdapter(
    context: Context,
    var playType: Int?,
    var playerDataHelp: PlayerDataHelp?
) :
    BaseRecyclerViewAdapter<Media>() {
    private var currentPosition: Int = 0
    private var playerCurrentTag: View? = null

    override fun getLayoutId(): Int = R.layout.sdk_player_variety_episode_adapter

    override fun onBindItemHolder(holder: BaseViewHolder?, position: Int) {
        val binding = holder?.viewDataBinding as? PlayerVarietyEpisodeBinding
        val datalist = getDataList()?.get(position)

        if (currentPosition == position) {
            playerCurrentTag = binding?.playerCurrentTag
            binding?.playerCurrentTag?.visibility = View.VISIBLE
            binding?.playerCurrentTag?.setActivated(true)
        } else {
            binding?.playerCurrentTag?.visibility = View.GONE
            binding?.playerCurrentTag?.setActivated(false)
        }

        val intranet = HWPlayerSettingOptions.INSTANCE.intranet
        val imgUrl = if (intranet) datalist?.pic else datalist?.picPath

        playerDataHelp?.onPlayerSettingsCallback?.onImgLoading(binding?.frescoImage, imgUrl, 332)

        if (!TextUtils.isEmpty(datalist?.publishDate)) {
            binding?.dateContent = "${datalist?.publishDate} 期"
        }
        binding?.media = datalist
        val media = getDataList()?.get(position)
        binding?.episodeMark?.show(playType, media?.drm, position + 1, media?.positiveTrailer)
    }

    fun notifyEpisode(currentPosition: Int) {
        this.currentPosition = currentPosition
        playerCurrentTag?.visibility = View.GONE
        playerCurrentTag?.setActivated(false)
        notifyItemRangeChanged(currentPosition, 1)
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        super.onViewRecycled(holder)
//        val binding = holder?.viewDataBinding as PlayerVarietyEpisodeBinding
//        binding.frescoImage.clearUriCaches()
    }
}