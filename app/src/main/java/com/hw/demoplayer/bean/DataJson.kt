package com.hw.demoplayer.bean

import com.alibaba.fastjson.JSON

//m3u8
//http://dbiptv.sn.chinamobile.com/36/16/20230414/279215226/279215226.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3266052%2C111.20.40.65%2C20230417125548%2Cprogram99242023041411480000%2C3266052%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421179564%2C1%2C%2C421626536%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2Oyw6CMBRE_6bLpgXksehKY2Ji0AR0ay70thIKxRZI_HuBsHA5k3MmMzqo8XISKo3DOIuCRGY84jxOWaoOquIRpmGQREA8fnIrAlKDMU2vcytX7VkcX5zRgCWU0YyU69rZgBZsA_Opq9DtYbEKdHNTo5Be0Rk8Ba0dahgb29O7ge_DmR0hWO7P-skYMq6hBN8uBXmDP9puAIfyavXGCQXGIxmgbkFjDh3-eTcnlxM_cxtW7-oAAAA
//http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA

//？格式
//http://zteres.sn.chinamobile.com:6060/huanshishaoer/32/movie24bb202302061533170010?AuthInfo=5MfDFjp9D9h%2F0lrqkysKiHPx5ZEnxLMjUQtr6vabE6cXkunNwmblyHwEuDZfcQQFlkNHQLmBSl9COP2D3sYpAg%3D%3D&version=v1.0&BreakPoint=0&virtualDomain=huanshishaoer.vod_hpd.zte.com&mescid=00000050280009372185&programid=&contentid=movie24bb202302061533170010&videoid=00000050280009372185&recommendtype=0&userid=A089E4CA0921&boid=&stbid=&terminalflag=1&profilecode=&usersessionid=529753020

class DataJson {
    private val data = "{\n" +
            "  \"album\": {\n" +
            "    \"id\": \"332833\",\n" +
            "    \"cid\": \"mzc002001vd5bz3\",\n" +
            "    \"title\": \"阿童木起源\",\n" +
            "    \"columnId\": \"0\",\n" +
            "    \"type\": 2,\n" +
            "    \"director\": \"[\\\"文鸿毅\\\"]\",\n" +
            "    \"language\": null,\n" +
            "    \"status\": 1,\n" +
            "    \"positiveTrailer\": \"1\",\n" +
            "    \"score\": \"7.2\",\n" +
            "    \"subtype\": \"[\\\"爱情\\\",\\\"古装\\\",\\\"科幻\\\"]\",\n" +
            "    \"copyright\": \"浙江东阳瑞毅影视文化有限公司\",\n" +
            "    \"leadingActor\": \"[\\\"许晓诺\\\",\\\"侯立\\\",\\\"甘望星\\\",\\\"权沛伦\\\",\\\"团团\\\"]\",\n" +
            "    \"guests\": \"\",\n" +
            "    \"publishDate\": \"2022-08-23\",\n" +
            "    \"episodeAll\": \"26\",\n" +
            "    \"episodeUpdated\": \"更新至18集\",\n" +
            "    \"newPicVt\": \"http://36.134.212.3:82/upload/shanxi/poster/fanju/atongmuqiyuan_V.jpg\",\n" +
            "    \"newPicHz\": \"http://36.134.212.3:82/upload/shanxi/poster/fanju/atongmuqiyuan_H.jpg\",\n" +
            "    \"payStatus\": 6,\n" +
            "    \"areaName\": \"内地\",\n" +
            "    \"year\": \"2022\",\n" +
            "    \"videoIds\": \"[\\\"p0044yozh1s\\\",\\\"h0044pqhfu9\\\",\\\"j0044zbqclj\\\",\\\"z0044mjmgpy\\\",\\\"k0044qkq9dq\\\",\\\"r0044wik47s\\\",\\\"g0044uj32sa\\\",\\\"p0044tx1y5q\\\",\\\"e00443itbkh\\\",\\\"c0044ys0trh\\\",\\\"l0044e884to\\\",\\\"h00444ldr8b\\\",\\\"b0044qntzmg\\\",\\\"c0044zs1efk\\\",\\\"e0044m9me24\\\",\\\"a0044kicwr4\\\",\\\"e00447onvuz\\\",\\\"n0044c4cw4d\\\",\\\"t00443k3wb9\\\",\\\"g0044rd58yp\\\",\\\"k00446zrvji\\\",\\\"y0044i2cja8\\\",\\\"u0044lj0zw9\\\",\\\"n0044abc9j0\\\",\\\"q0044l78s0h\\\",\\\"e0044cdy8mb\\\",\\\"n0044woyins\\\",\\\"g0044cuxsws\\\",\\\"o0044dok4rq\\\",\\\"r0044lss1xg\\\"]\",\n" +
            "    \"description\": \"十八线女明星意外进入到了剧本之中，成了剧中的角色苏向晚，在这个世界，苏向晚桃花傍身。为了回到现实世界，苏向晚必须改变剧情，创造完美结局，在这一过程中她认识了豫王赵容显，二人从相遇走到相爱......\",\n" +
            "    \"brief\": \"\",\n" +
            "    \"picHzPath\": \"\",\n" +
            "    \"picVtPath\": \"\",\n" +
            "    \"playType\": -1,\n" +
            "    \"cidContentStructureId\": null,\n" +
            "    \"showType\": 2\n" +
            "  },\n" +
            "  \"column\": null,\n" +
            "  \"medias\": [\n" +
            "    {\n" +
            "      \"id\": \"12245802\",\n" +
            "      \"vid\": \"p0044yozh1s\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_01\",\n" +
            "      \"drm\": 0,\n" +
            "      \"duration\": 584,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/p0044yozh1s/p0044yozh1s_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"movieb2b62022082414150000\",\n" +
            "      \"zxPlayUrl\": null,\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12245801\",\n" +
            "      \"vid\": \"h0044pqhfu9\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_02\",\n" +
            "      \"drm\": 0,\n" +
            "      \"duration\": 569,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/h0044pqhfu9/h0044pqhfu9_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": null,\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12245800\",\n" +
            "      \"vid\": \"j0044zbqclj\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_03\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 717,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/j0044zbqclj/j0044zbqclj_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12245811\",\n" +
            "      \"vid\": \"z0044mjmgpy\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_04\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 604,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/z0044mjmgpy/z0044mjmgpy_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12245797\",\n" +
            "      \"vid\": \"k0044qkq9dq\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_05\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 609,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/k0044qkq9dq/k0044qkq9dq_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12245796\",\n" +
            "      \"vid\": \"r0044wik47s\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_06\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 542,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/r0044wik47s/r0044wik47s_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12245795\",\n" +
            "      \"vid\": \"b0044qntzmg\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_07\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 589,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/b0044qntzmg/b0044qntzmg_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12245805\",\n" +
            "      \"vid\": \"c0044zs1efk\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_08\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 518,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/c0044zs1efk/c0044zs1efk_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12246811\",\n" +
            "      \"vid\": \"e0044m9me24\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_09\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 570,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/e0044m9me24/e0044m9me24_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12246809\",\n" +
            "      \"vid\": \"a0044kicwr4\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_10\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 569,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/a0044kicwr4/a0044kicwr4_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12247353\",\n" +
            "      \"vid\": \"e00447onvuz\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_11\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 617,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/e00447onvuz/e00447onvuz_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12247363\",\n" +
            "      \"vid\": \"n0044c4cw4d\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_12\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 545,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/n0044c4cw4d/n0044c4cw4d_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12256291\",\n" +
            "      \"vid\": \"t00443k3wb9\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_13\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 576,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/t00443k3wb9/t00443k3wb9_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12256312\",\n" +
            "      \"vid\": \"g0044rd58yp\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_14\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 552,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/g0044rd58yp/g0044rd58yp_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12259137\",\n" +
            "      \"vid\": \"k00446zrvji\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_15\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 570,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/k00446zrvji/k00446zrvji_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12259134\",\n" +
            "      \"vid\": \"y0044i2cja8\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_16\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 577,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/y0044i2cja8/y0044i2cja8_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12261234\",\n" +
            "      \"vid\": \"u0044lj0zw9\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_17\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 583,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/u0044lj0zw9/u0044lj0zw9_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"12261237\",\n" +
            "      \"vid\": \"n0044abc9j0\",\n" +
            "      \"cid\": null,\n" +
            "      \"title\": \"阿童木起源_18\",\n" +
            "      \"drm\": 1,\n" +
            "      \"duration\": 538,\n" +
            "      \"pic\": \"http://puui.qpic.cn/vpic_cover/n0044abc9j0/n0044abc9j0_hz.jpg/640\",\n" +
            "      \"picPath\": \"\",\n" +
            "      \"publishDate\": null,\n" +
            "      \"cdnUrl\": null,\n" +
            "      \"movieCode\": \"\",\n" +
            "      \"zxPlayUrl\": \"\",\n" +
            "      \"hwPlayUrl\": \"http://dbiptv.sn.chinamobile.com/36/16/20230419/279232427/279232427.ts/index.m3u8?fmt=ts2hls&zoneoffset=0&servicetype=0&icpid=&limitflux=-1&limitdur=-1&tenantId=8601&accountinfo=%2C3472350%2C111.20.40.12%2C20230420142053%2Cprogramee552023041909510000%2C3472350%2C-1%2C1%2C0%2C-1%2C1%2C1%2C100000209%2C%2C421954117%2C1%2C%2C421954551%2CEND&GuardEncType=2&it=H4sIAAAAAAAAAE2O0QqCMBiF32aXY24Z62JXRhCEBVq38bv9Lmk621To7VPxostz-L7DGQJoPB9VClzoJDlUVVrtmDASBa9T3ONuL1mqJYn4yb3iRINzTWdzbxbtUWTPhNFEcCol5YKRchk8ObCKrWw-thWGLcxigWFqNCoTazpBpGBtQAtD4zt6c_C9B7chBMvtXDc6R4YllBDfc0FeEDPf9hDQXLxdOVWDi0h60G-wmEOLf941mPnED-x2S_vtAAAA\",\n" +
            "      \"encryptionType\": \"1\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"recAlbums\": [\n" +
            "    {\n" +
            "      \"cid\": \"vbb35hm6m6da1wc\",\n" +
            "      \"title\": \"陈情令\",\n" +
            "      \"type\": 2,\n" +
            "      \"newPicVt\": \"http://puui.qpic.cn/vcover_vt_pic/0/vbb35hm6m6da1wc1561952321/260\",\n" +
            "      \"newPicHz\": \"http://puui.qpic.cn/vcover_hz_pic/0/vbb35hm6m6da1wc1561952369/0\",\n" +
            "      \"payStatus\": 6,\n" +
            "      \"publishDate\": null,\n" +
            "      \"picHzPath\": \"http://posterahydcmcc.huan.tv/public/tencentImg/vbb35hm6m6da1wc_album_hz\",\n" +
            "      \"picVtPath\": \"http://posterahydcmcc.huan.tv/public/tencentImg/vbb35hm6m6da1wc_album_vt\",\n" +
            "      \"cidContentStructureId\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"cid\": \"mzc00200v3lnbmd\",\n" +
            "      \"title\": \"且试天下\",\n" +
            "      \"type\": 2,\n" +
            "      \"newPicVt\": \"http://puui.qpic.cn/vcover_vt_pic/0/mzc00200v3lnbmd1649386229079/260\",\n" +
            "      \"newPicHz\": \"http://puui.qpic.cn/vcover_hz_pic/0/mzc00200v3lnbmd1650207570814/0\",\n" +
            "      \"payStatus\": 6,\n" +
            "      \"publishDate\": null,\n" +
            "      \"picHzPath\": \"\",\n" +
            "      \"picVtPath\": \"\",\n" +
            "      \"cidContentStructureId\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"cid\": \"mzc00200p51jpn7\",\n" +
            "      \"title\": \"梦华录\",\n" +
            "      \"type\": 2,\n" +
            "      \"newPicVt\": \"http://puui.qpic.cn/vcover_vt_pic/0/mzc00200p51jpn71653838845722/260\",\n" +
            "      \"newPicHz\": \"http://puui.qpic.cn/vcover_hz_pic/0/mzc00200p51jpn71648780963882/0\",\n" +
            "      \"payStatus\": 6,\n" +
            "      \"publishDate\": null,\n" +
            "      \"picHzPath\": \"\",\n" +
            "      \"picVtPath\": \"\",\n" +
            "      \"cidContentStructureId\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"cid\": \"mzc00200maswqzu\",\n" +
            "      \"title\": \"通天塔\",\n" +
            "      \"type\": 2,\n" +
            "      \"newPicVt\": \"http://puui.qpic.cn/vcover_vt_pic/0/mzc00200maswqzu1651902603987/260\",\n" +
            "      \"newPicHz\": \"http://puui.qpic.cn/vcover_hz_pic/0/mzc00200maswqzu1651902452584/0\",\n" +
            "      \"payStatus\": 6,\n" +
            "      \"publishDate\": null,\n" +
            "      \"picHzPath\": \"\",\n" +
            "      \"picVtPath\": \"\",\n" +
            "      \"cidContentStructureId\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"cid\": \"mzc00200vmd652y\",\n" +
            "      \"title\": \"月升沧海\",\n" +
            "      \"type\": 2,\n" +
            "      \"newPicVt\": \"http://puui.qpic.cn/vcover_vt_pic/0/mzc00200vmd652y1658930222301/260\",\n" +
            "      \"newPicHz\": \"http://puui.qpic.cn/vcover_hz_pic/0/mzc00200vmd652y1656904220599/0\",\n" +
            "      \"payStatus\": 6,\n" +
            "      \"publishDate\": null,\n" +
            "      \"picHzPath\": \"\",\n" +
            "      \"picVtPath\": \"\",\n" +
            "      \"cidContentStructureId\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"cid\": \"mzc00200crgj447\",\n" +
            "      \"title\": \"龙一，你要怎样\",\n" +
            "      \"type\": 2,\n" +
            "      \"newPicVt\": \"http://puui.qpic.cn/vcover_vt_pic/0/mzc00200crgj4471658298744050/260\",\n" +
            "      \"newPicHz\": \"http://puui.qpic.cn/vcover_hz_pic/0/mzc00200crgj4471658298754432/0\",\n" +
            "      \"payStatus\": 6,\n" +
            "      \"publishDate\": null,\n" +
            "      \"picHzPath\": \"\",\n" +
            "      \"picVtPath\": \"\",\n" +
            "      \"cidContentStructureId\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"cid\": \"mzc00200fivc1di\",\n" +
            "      \"title\": \"迷航昆仑墟\",\n" +
            "      \"type\": 2,\n" +
            "      \"newPicVt\": \"http://puui.qpic.cn/vcover_vt_pic/0/mzc00200fivc1di1658477264041/260\",\n" +
            "      \"newPicHz\": \"http://puui.qpic.cn/vcover_hz_pic/0/mzc00200fivc1di1659341666381/0\",\n" +
            "      \"payStatus\": 6,\n" +
            "      \"publishDate\": null,\n" +
            "      \"picHzPath\": \"\",\n" +
            "      \"picVtPath\": \"\",\n" +
            "      \"cidContentStructureId\": null\n" +
            "    }\n" +
            "  ],\n" +
            "  \"recClassId\": \"123\",\n" +
            "  \"auth\": null\n" +
            "}"


    fun getResponseBean(): ProgramInfoDetail? {
        return JSON.parseObject(data, ProgramInfoDetail::class.java)
    }


}