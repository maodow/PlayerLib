package com.huan.player.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.huan.player.R
import com.huan.player.config.MarkConfigHelp
import com.huan.player.constant.Utils
import tv.huan.hwplayer.config.HWPlayerSettingOptions


/**
 * 集数角标
 */
class EpisodeMarkView : LinearLayout {

    private var rightMark: TextView? = null
    private var leftImage: ImageView? = null
    private var markConfig: MarkConfigHelp? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(attrs)
    }

    fun initView(attrs: AttributeSet?) {
        View.inflate(context, R.layout.sdk_episode_mark_layout, this)
        rightMark = findViewById<TextView>(R.id.right_mark)
        leftImage = findViewById<ImageView>(R.id.left_image)

        markConfig = HWPlayerSettingOptions.INSTANCE.markConfigHelp

        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.SdkEpisodeMarkView)
        var contentSize = typedArray.getDimension(R.styleable.SdkEpisodeMarkView_sdk_contentSize, 0f)
        var markMargin = typedArray.getDimension(R.styleable.SdkEpisodeMarkView_sdk_markMargin, 0f)
        var imageSize = typedArray.getDimension(R.styleable.SdkEpisodeMarkView_sdk_imageSize, 0f)
        if (contentSize != 0f) {
            rightMark?.setTextSize(contentSize)
        }

        if (markMargin != 0f && (markConfig == null || markConfig!!.episodeMargin == -100)) {
            val rightParams = rightMark?.layoutParams as RelativeLayout.LayoutParams
            rightParams?.setMargins(markMargin.toInt(),0,0,0)
            rightMark?.layoutParams = rightParams

            val leftParams = leftImage?.layoutParams as RelativeLayout.LayoutParams
            leftParams?.setMargins(markMargin.toInt(),0,0,0)
            leftImage?.layoutParams = leftParams
        } else if (markConfig != null && markConfig!!.episodeMargin != -100) {
            val rightParams = rightMark?.layoutParams as RelativeLayout.LayoutParams
            rightParams?.setMargins(markConfig!!.episodeMargin,0,0,0)
            rightMark?.layoutParams = rightParams

            val leftParams = leftImage?.layoutParams as RelativeLayout.LayoutParams
            leftParams?.setMargins(markConfig!!.episodeMargin,0,0,0)
            leftImage?.layoutParams = leftParams
        }

        if (imageSize != 0f) {
            val leftParams = leftImage?.layoutParams as RelativeLayout.LayoutParams
            leftParams?.width = imageSize.toInt()
            leftParams?.height = imageSize.toInt()
            leftImage?.layoutParams = leftParams
        }

        typedArray.recycle()
    }

    /**
     * @param playType 后台自定义的免费集数
     * @param drm 腾讯的免费标识  0是免费的
     * @param episode 当前集数，不是下标
     */
    fun show(playType: Int?, drm: Int?, episode: Int?, positiveTrailer: Int?) {

        if (Utils.nullToInt(playType) == -1) {
            setType(drm)
        } else {
            var type = if (Utils.nullToInt(episode) <= Utils.nullToInt(playType)) 0 else 1
            setType(type)
        }

        if (Utils.nullToInt(positiveTrailer) != 1) {
            visibility = View.VISIBLE
            rightMark?.setText("预告")
            leftImage?.visibility= View.GONE
            rightMark?.background = Utils.getDrawable(R.drawable.sdk_corner_mark_bg5)
        }
    }

    private fun setType(type: Int?) {
        val episodeIconMap = markConfig?.episodeIconMap

        if (episodeIconMap != null && markConfig!!.locationLeftOrRight) {
            val imageId = episodeIconMap.get(getToType(type))
            if (markConfig!!.isShowEpisodeVip) {
                if (Utils.nullToInt(imageId) != 0) {
                    visibility = View.VISIBLE
                    leftImage?.visibility= View.VISIBLE
                    leftImage?.setImageResource(imageId!!)
                } else {
                    visibility = View.GONE
                }
            } else {
                if (Utils.nullToInt(imageId) != 0) {
                    visibility = View.VISIBLE
                    leftImage?.visibility= View.VISIBLE
                    leftImage?.setImageResource(imageId!!)
                } else {
                    visibility = View.GONE
                }
            }

            return
        }

        if (markConfig == null || Utils.null2False(markConfig?.isShowEpisodeVip)) {
            setVip(type)
        } else {
            setFree(type)
        }
    }

    private fun setVip(type: Int?) {
        rightMark?.setText("VIP")
        rightMark?.background = Utils.getDrawable(R.drawable.sdk_corner_mark_bg4)
        if (Utils.nullToInt(type) != 0) {
            visibility = View.VISIBLE
            rightMark?.setText("VIP")
            rightMark?.background = Utils.getDrawable(R.drawable.sdk_corner_mark_bg4)
        } else {
            visibility = View.GONE
        }
    }

    private fun setFree(type: Int?) {
        if (Utils.nullToInt(type) != 0) {
            visibility = View.GONE
        } else {
            visibility = View.VISIBLE
            rightMark?.setText("免费")
            rightMark?.background = Utils.getDrawable(R.drawable.sdk_corner_mark_bg3)
        }
    }


    private fun getToType(type: Int?): Int {
        return if (type == 0) 0 else 1
    }

}