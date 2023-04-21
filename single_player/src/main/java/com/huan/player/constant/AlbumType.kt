package com.huan.player.constant

/**
 *  影片类型对应 classId
 */
interface AlbumType {
    companion object {
        const val DEFAULT = 0 //其他类型

        const val FILM = 1 //电影

        const val TELEPLAY = 2 //电视剧

        const val ANIME = 3 //动漫

        const val SPORTS = 4 //体育

        const val RECREATION = 5 //娱乐

        const val GAME = 6 //游戏

        const val DOCUMENTARY = 9 //纪录片

        const val VARIETY = 10 //综艺

        const val MUSIC = 22 //音乐

        const val NEWS = 23 //新闻

        const val FASHION = 25 //时尚

        const val TOURISM = 26 //旅游

        const val EDUCATION = 27 //教育

        const val KEJI = 28 //科技

        const val CAR = 29 //汽车

        const val LIFE = 31 //生活

        const val COOPERATION = 50 //合作

        const val MUYING = 60 //母婴

        const val CHILDREN = 106 //少儿

        fun getTypeToName(type: Int?): String {
            var mType = if (type == null) 0 else type
            when (mType) {
                DEFAULT -> {
                    return "其他类型"
                }
                FILM -> {
                    return "电影"
                }
                TELEPLAY -> {
                    return "电视剧"
                }
                ANIME -> {
                    return "动漫"
                }
                SPORTS -> {
                    return "体育"
                }
                RECREATION -> {
                    return "娱乐"
                }
                GAME -> {
                    return "游戏"
                }
                DOCUMENTARY -> {
                    return "纪录片"
                }
                VARIETY -> {
                    return "综艺"
                }
                MUSIC -> {
                    return "音乐"
                }
                NEWS -> {
                    return "新闻"
                }
                FASHION -> {
                    return "时尚"
                }
                TOURISM -> {
                    return "旅游"
                }
                EDUCATION -> {
                    return "教育"
                }
                KEJI -> {
                    return "科技"
                }
                CAR -> {
                    return "汽车"
                }
                LIFE -> {
                    return "生活"
                }
                COOPERATION -> {
                    return "合作"
                }
                MUYING -> {
                    return "母婴"
                }
                CHILDREN -> {
                    return "少儿"
                }
            }
            return "其他类型"
        }
    }
}