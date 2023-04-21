package tv.huan.hwsystemsdk.player.adapter

import android.content.Context
import com.huan.player.R
import com.huan.player.bean.Album
import com.huan.player.databinding.PlayerRecommendBinding
import com.huan.player.ui.BaseRecyclerViewAdapter
import com.huan.player.ui.BaseViewHolder

class PlayerRecommendAdapter(context: Context) : BaseRecyclerViewAdapter<Album>() {

    override fun getLayoutId(): Int = R.layout.sdk_player_recommend_adapter

    override fun onBindItemHolder(holder: BaseViewHolder?, position: Int) {
        val binding = holder?.viewDataBinding as? PlayerRecommendBinding
        val album = getDataList()?.get(position)
        //album?.picVtPath = ImagePathHelp.getImageVtPathSrcType(album?.newPicVt, album?.picVtPath,
          //  if (album?.getType() === 10) 0 else 350)
        binding?.album = album
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        super.onViewRecycled(holder)
//        val binding = holder?.viewDataBinding as PlayerRecommendBinding
//        binding.frescoImageView.clearUriCaches()
    }
}