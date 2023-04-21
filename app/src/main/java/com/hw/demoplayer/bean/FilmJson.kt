package com.hw.demoplayer.bean

import com.alibaba.fastjson.JSON

class FilmJson {
    private val data = "{\n" +
            "  \"album\": {\n" +
            "    \"id\": \"287539\",\n" +
            "    \"cid\": \"mzc00200b5n7vvf\",\n" +
            "    \"title\": \"楚留香之盗帅觉醒\",\n" +
            "    \"columnId\": \"0\",\n" +
            "    \"type\": 1,\n" +
            "    \"director\": \"[\\\"董伟\\\"]\",\n" +
            "    \"language\": null,\n" +
            "    \"status\": 1,\n" +
            "    \"positiveTrailer\": \"1\",\n" +
            "    \"score\": \"7.4\",\n" +
            "    \"subtype\": \"[\\\"武侠\\\",\\\"爱情\\\"]\",\n" +
            "    \"copyright\": \"网大电影\",\n" +
            "    \"leadingActor\": \"[\\\"郭品超\\\",\\\"康宁\\\",\\\"蔡卓音\\\",\\\"周小飞\\\"]\",\n" +
            "    \"guests\": \"\",\n" +
            "    \"publishDate\": \"2021-03-17\",\n" +
            "    \"episodeAll\": \"0\",\n" +
            "    \"episodeUpdated\": \"0\",\n" +
            "    \"newPicVt\": \"http://puui.qpic.cn/vcover_vt_pic/0/mzc00200b5n7vvf1614923370800/0\",\n" +
            "    \"newPicHz\": \"http://puui.qpic.cn/vcover_hz_pic/0/mzc00200b5n7vvf1614923384649/556\",\n" +
            "    \"payStatus\": 6,\n" +
            "    \"areaName\": \"内地\",\n" +
            "    \"year\": \"2021\",\n" +
            "    \"videoIds\": \"[\\\"h0036850rus\\\"]\",\n" +
            "    \"description\": \"初出江湖的楚留香结识了神水宫新任宫主慕千羽，由此卷入到一场危机之中，最终联手挫败了天枫十四郎刺杀抗倭名将的阴谋。二人在追凶的过程中深深相爱，却又因彼此的宿命天各一方。二十年后，名满天下的楚留香终于等来了爱人。\",\n" +
            "    \"brief\": \"郭品超变身楚留香再现经典\",\n" +
            "    \"picHzPath\": \"\",\n" +
            "    \"picVtPath\": \"\",\n" +
            "    \"playType\": -1,\n" +
            "    \"cidContentStructureId\": null\n" +
            "  },\n" +
            "  \"column\": null,\n" +
            "  \"medias\": [\n" +
            "    {\n" +
            "      \"id\": \"3960997\",\n" +
            "      \"vid\": \"h0036850rus\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"楚留香之盗帅觉醒\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 4662,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/h0036850rus/h0036850rus_hz.jpg/640\",\n" +
            "      \"picPath\": \"http://puui.qpic.cn/vpic_cover/h0036850rus/h0036850rus_hz.jpg/640\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"movieb55f2021031716250000\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"recAlbums\": [\n" +
            "    {\n" +
            "      \"cid\": \"7g1n1j0cc562bc8\",\n" +
            "      \"title\": \"后来的我们\",\n" +
            "      \"type\": 1,\n" +
            "      \"newPicVt\": \"http://puui.qpic.cn/vcover_vt_pic/0/7g1n1j0cc562bc81573708244/260\",\n" +
            "      \"newPicHz\": \"http://puui.qpic.cn/vcover_hz_pic/0/7g1n1j0cc562bc81566962969/0\",\n" +
            "      \"payStatus\": 6,\n" +
            "      \"publishDate\": null,\n" +
            "      \"picHzPath\": \"http://posterahydcmcc.huan.tv/public/tencentImg/7g1n1j0cc562bc8_album_hz\",\n" +
            "      \"picVtPath\": \"http://posterahydcmcc.huan.tv/public/tencentImg/7g1n1j0cc562bc8_album_vt\",\n" +
            "      \"cidContentStructureId\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"cid\": \"f3qroleq1lyuavy\",\n" +
            "      \"title\": \"唐人街探案\",\n" +
            "      \"type\": 1,\n" +
            "      \"newPicVt\": \"http://puui.qpic.cn/vcover_vt_pic/0/f3qroleq1lyuavy1575430841/260\",\n" +
            "      \"newPicHz\": \"http://puui.qpic.cn/vcover_hz_pic/0/f3qroleq1lyuavy1519272571/0\",\n" +
            "      \"payStatus\": 6,\n" +
            "      \"publishDate\": null,\n" +
            "      \"picHzPath\": \"\",\n" +
            "      \"picVtPath\": \"\",\n" +
            "      \"cidContentStructureId\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"cid\": \"z6j3ixjjcokafyc\",\n" +
            "      \"title\": \"西虹市首富\",\n" +
            "      \"type\": 1,\n" +
            "      \"newPicVt\": \"http://posterahydcmcc.huan.tv/public/localImg/z6j3ixjjcokafyc_album_vt/260.png\",\n" +
            "      \"newPicHz\": \"http://puui.qpic.cn/vcover_hz_pic/0/z6j3ixjjcokafyc1566896578/0\",\n" +
            "      \"payStatus\": 6,\n" +
            "      \"publishDate\": null,\n" +
            "      \"picHzPath\": \"http://posterahydcmcc.huan.tv/public/tencentImg/z6j3ixjjcokafyc_album_hz\",\n" +
            "      \"picVtPath\": \"http://posterahydcmcc.huan.tv/public/localImg/z6j3ixjjcokafyc_album_vt/0.png\",\n" +
            "      \"cidContentStructureId\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"cid\": \"m5zzglrbt5zdv6d\",\n" +
            "      \"title\": \"叶问4\",\n" +
            "      \"type\": 1,\n" +
            "      \"newPicVt\": \"http://posterahydcmcc.huan.tv/public/localImg/m5zzglrbt5zdv6d_album_vt/260.png\",\n" +
            "      \"newPicHz\": \"http://puui.qpic.cn/vcover_hz_pic/0/m5zzglrbt5zdv6d1552897431/0\",\n" +
            "      \"payStatus\": 6,\n" +
            "      \"publishDate\": null,\n" +
            "      \"picHzPath\": \"\",\n" +
            "      \"picVtPath\": \"http://posterahydcmcc.huan.tv/public/localImg/m5zzglrbt5zdv6d_album_vt/0.png\",\n" +
            "      \"cidContentStructureId\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"cid\": \"mzc0020046kygiu\",\n" +
            "      \"title\": \"大脚怪2\",\n" +
            "      \"type\": 1,\n" +
            "      \"newPicVt\": \"http://puui.qpic.cn/vcover_vt_pic/0/mzc0020046kygiu1656478681255/260\",\n" +
            "      \"newPicHz\": \"http://puui.qpic.cn/vcover_hz_pic/0/mzc0020046kygiu1656478692055/0\",\n" +
            "      \"payStatus\": 6,\n" +
            "      \"publishDate\": null,\n" +
            "      \"picHzPath\": \"\",\n" +
            "      \"picVtPath\": \"\",\n" +
            "      \"cidContentStructureId\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"cid\": \"fse52rd4klx7qn2\",\n" +
            "      \"title\": \"大话西游之大圣娶亲·加长纪念版\",\n" +
            "      \"type\": 1,\n" +
            "      \"newPicVt\": \"http://puui.qpic.cn/vcover_vt_pic/0/fse52rd4klx7qn21566961078/260\",\n" +
            "      \"newPicHz\": \"http://puui.qpic.cn/vcover_hz_pic/0/fse52rd4klx7qn21566961089/0\",\n" +
            "      \"payStatus\": 6,\n" +
            "      \"publishDate\": null,\n" +
            "      \"picHzPath\": \"http://posterahydcmcc.huan.tv/public/tencentImg/fse52rd4klx7qn2_album_hz\",\n" +
            "      \"picVtPath\": \"http://posterahydcmcc.huan.tv/public/tencentImg/fse52rd4klx7qn2_album_vt\",\n" +
            "      \"cidContentStructureId\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"cid\": \"mzc00200h97lhuv\",\n" +
            "      \"title\": \"《你好，李焕英》独家纪录片\",\n" +
            "      \"type\": 1,\n" +
            "      \"newPicVt\": \"http://puui.qpic.cn/vcover_vt_pic/0/mzc00200h97lhuv1612403655500/260\",\n" +
            "      \"newPicHz\": \"http://puui.qpic.cn/vcover_hz_pic/0/mzc00200h97lhuv1612407828333/0\",\n" +
            "      \"payStatus\": 6,\n" +
            "      \"publishDate\": null,\n" +
            "      \"picHzPath\": \"\",\n" +
            "      \"picVtPath\": \"\",\n" +
            "      \"cidContentStructureId\": null\n" +
            "    }\n" +
            "  ],\n" +
            "  \"recClassId\": \"108\",\n" +
            "  \"auth\": null\n" +
            "}"

    fun getResponseBean(): ProgramInfoDetail? {
        return JSON.parseObject(data, ProgramInfoDetail::class.java)
    }
}