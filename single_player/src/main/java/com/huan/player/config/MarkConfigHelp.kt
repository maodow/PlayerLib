package com.huan.player.config

import android.graphics.drawable.Drawable
import com.huan.player.R
import com.huan.player.constant.AlbumType
import com.huan.player.constant.Utils


class MarkConfigHelp private constructor(builder: Builder) {
    var locationLeftOrRight = false
        private set

    var albumIconMap: MutableMap<Int, Int>? = null
        private set

    var isShowEpisodeVip = true
        private set

    var episodeIconMap: MutableMap<Int, Int>? = null
        private set

    var episodeMargin: Int = 0
        private set

    var textArray: MutableList<String> = mutableListOf("专题", "专区", "独播", "免费", "VIP")
        private set

    var markType = 1
        private set

    var bgArray: MutableList<Int> = mutableListOf(
        Utils.getColor(R.color.corner_mark_color),
        Utils.getColor(R.color.corner_mark_color1),
        Utils.getColor(R.color.corner_mark_color2),
        Utils.getColor(R.color.corner_mark_color3),
        Utils.getColor(R.color.corner_mark_color4),
        Utils.getColor(R.color.corner_mark_color5)
    )
        private set

    /**
     * @param type 影片类型
     * @param episodeUpdated 更新的集数
     * @param score 评分
     * @param publishDate 综艺的日期
     */
    fun getHint(
        type: Int?,
        episodeUpdated: String?,
        score: String?,
        publishDate: String?
    ): String {
        if (type == null) return ""
        if (type == AlbumType.FILM) {
            return Utils.null2Length0(score)
        }
        if (type == AlbumType.VARIETY) {
            return Utils.null2Length0(publishDate)
        }
        if (type == AlbumType.TELEPLAY || type == AlbumType.ANIME || type == AlbumType.CHILDREN) {
            var null2Length0 = Utils.null2Length0(episodeUpdated)
            if (null2Length0.length == 1) {
                return ""
            } else {
                return null2Length0
            }

        }
        return ""
    }

    fun getKarkTypeToName(unicast: Int?, payStatus: Int?): String? {
        if (unicast == 1) { // 独播
            return "独播"
        }
        if (payStatus == 8) { //免费
            return "免费"
        } else if (payStatus != null) {
            return "VIP"  //vip payStatus ！= 8
        }
        return "VIP"
    }

    fun getKarkBg(unicast: Int?, payStatus: Int?): Drawable? {
        if (unicast == 1) { // 独播
            return Utils.getDrawable(R.drawable.sdk_corner_mark_bg2)
        }
        if (payStatus == 8) { //免费
            return Utils.getDrawable(R.drawable.sdk_corner_mark_bg3)
        } else if (payStatus != null) {
            return Utils.getDrawable(R.drawable.sdk_corner_mark_bg4)  //vip payStatus ！= 8
        }
        return Utils.getDrawable(R.drawable.sdk_corner_mark_bg4)
    }


    companion object Builder {
        private var locationLeftOrRight = false
        private var isShowEpisodeVip = true
        private var episodeMargin: Int = -100
        private var albumIconMap: MutableMap<Int, Int>? = null
        private var episodeIconMap: MutableMap<Int, Int>? = null
        private var markType = 1

        /**
         * 设置角标的位置左右
         * 左： true
         * 右： false
         */
        fun setLocationLeftOrRight(leftOrRight: Boolean): Builder {
            locationLeftOrRight = leftOrRight
            return this
        }


        /**
         * 影视角标类型
         * 按照以下key添加
         *  0、专题, 1、专区, 2、独播, 3、免费, 4、VIP
         */
        fun setAlbumIconImage(albumIconDrawable: MutableMap<Int, Int>): Builder {
            albumIconMap = albumIconDrawable
            return this
        }

        /**
         *  设置集数角标图片
         *   0是免费，1是收费
         *  @param episodeIconDrawable 角标图片
         */
        fun setEpisodeIconMap(episodeIconDrawable: MutableMap<Int, Int>): Builder {
            episodeIconMap = episodeIconDrawable
            return this
        }

        /**
         * 集数角标是显示免费，还是付费的
         */
        fun setShowEpisodeVip(isShowEpisodeVip: Boolean): Builder {
            this.isShowEpisodeVip = isShowEpisodeVip
            return this
        }

        /**
         * 角标显示类型
         * @param markType 0: 没有; 1: 所有类型角标; 2：除vip角标 3: 除免费和vip 4: 只要免费和付费类型
         */
        fun setMarkType(markType: Int): Builder {
            this.markType = markType
            return this
        }

        fun setEpisodeMargin(episodeMargin: Int): Builder {
            this.episodeMargin = Utils.dp2px(episodeMargin.toFloat())
            return this
        }

        fun build(): MarkConfigHelp {
            return MarkConfigHelp(this)
        }
    }

    init {
        locationLeftOrRight = builder.locationLeftOrRight
        isShowEpisodeVip = builder.isShowEpisodeVip
        albumIconMap = builder.albumIconMap
        episodeIconMap = builder.episodeIconMap
        episodeMargin = builder.episodeMargin
        markType = builder.markType
    }
}