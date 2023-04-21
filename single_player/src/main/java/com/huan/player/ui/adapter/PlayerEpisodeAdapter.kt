package tv.huan.hwsystemsdk.player.adapter

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.huan.player.R
import com.huan.player.bean.Media
import com.huan.player.databinding.PlayerEpisodeBinding
import com.huan.player.ui.BaseRecyclerViewAdapter
import com.huan.player.ui.BaseViewHolder

class PlayerEpisodeAdapter(context: Context, var playType: Int?) :
    BaseRecyclerViewAdapter<Media>() {
    private var imgCurrentPlay: ImageView? = null
    private var playerEpisode: View? = null
    private var currentPosition: Int = 0

    override fun getLayoutId(): Int = R.layout.sdk_player_episode_adapter

    override fun onBindItemHolder(holder: BaseViewHolder?, position: Int) {
        val binding = holder?.viewDataBinding as? PlayerEpisodeBinding
        binding?.playerEpisode?.setText((1 + position).toString())

        binding?.imgCurrentPlay?.visibility =
            if (currentPosition === position) {
                imgCurrentPlay = binding?.imgCurrentPlay
                playerEpisode = binding?.playerEpisode
                binding?.playerEpisode?.setActivated(true)
                View.VISIBLE
            } else {
                binding?.playerEpisode?.setActivated(false)
                View.GONE
            }


        binding?.root?.viewTreeObserver?.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val layoutParams = binding.episodeMark.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.width = binding.root.width
                layoutParams.height = binding.root.height
                binding.episodeMark.layoutParams = layoutParams
            }
        })

        val media = getDataList()?.get(position)
        binding?.episodeMark?.show(playType, media?.drm, 1 + position, media?.positiveTrailer)
    }


    fun notifyEpisode(currentPosition: Int) {
        this.currentPosition = currentPosition
        imgCurrentPlay?.visibility = View.GONE
        playerEpisode?.setActivated(false)
        notifyItemRangeChanged(currentPosition, 1)
    }


}