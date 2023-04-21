package tv.huan.hwsystemsdk.player.adapter

import android.content.Context
import android.view.View
import com.huan.player.R
import com.huan.player.bean.Media
import com.huan.player.constant.Utils
import com.huan.player.databinding.PlayerFilmEpisodeBinding
import com.huan.player.ui.BaseRecyclerViewAdapter
import com.huan.player.ui.BaseViewHolder
import com.huan.player.ui.PlayerDataHelp
import tv.huan.hwplayer.config.HWPlayerSettingOptions

class PlayerFilmEpisodeAdapter(context: Context,var playerDataHelp: PlayerDataHelp?) : BaseRecyclerViewAdapter<Media>() {
    private var currentPosition: Int = 0
    private var filmIsPlay: View? = null

    override fun getLayoutId(): Int = R.layout.sdk_player_film_episode_adapter

    override fun onBindItemHolder(holder: BaseViewHolder?, position: Int) {
        val binding = holder?.viewDataBinding as? PlayerFilmEpisodeBinding
        val media = getDataList()?.get(position)
        if (currentPosition == position) {
            binding?.filmIsPlay?.visibility = View.VISIBLE
            filmIsPlay = binding?.filmIsPlay
            binding?.filmIsPlay?.setActivated(true)
        } else {
            binding?.filmIsPlay?.visibility = View.GONE
            binding?.filmIsPlay?.setActivated(false)
        }
        val intranet = HWPlayerSettingOptions.INSTANCE.intranet
        val imgUrl = if (intranet) media?.picPath else media?.pic
        playerDataHelp?.onPlayerSettingsCallback?.onImgLoading(binding?.frescoImageView,imgUrl, 332)

        binding?.filmPlayTime?.setText(Utils.toPlayFormatString(media!!.duration!!.toLong() * 1000))
        binding?.media = media
    }


    fun notifyEpisode(currentPosition: Int) {
        this.currentPosition = currentPosition
        filmIsPlay?.visibility = View.GONE
        filmIsPlay?.setActivated(false)
        notifyItemRangeChanged(currentPosition, 1)
    }
}