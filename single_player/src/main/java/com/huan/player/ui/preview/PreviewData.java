package com.huan.player.ui.preview;

import android.text.TextUtils;

/**
 * description:
 *
 * @author jeremyyang
 * @date 2020/1/3
 */
public class PreviewData {

    /**
     * （1）缩略图协议字段
     * 例如：
     * <pl><cnt>3</cnt>
     * <pd><cd>10</cd><h>45</h><w>80</w><r>10</r><c>10</c><fmt>40001</fmt><fn>q1</fn><url>http://puui.qpic.cn/video_caps/0/</url></pd>
     * <pd><cd>10</cd><h>90</h><w>160</w><r>5</r><c>5</c><fmt>40002</fmt><fn>q2</fn><url>http://puui.qpic.cn/video_caps/0/</url></pd>
     * <pd><cd>10</cd><h>135</h><w>240</w><r>5</r><c>5</c><fmt>40003</fmt><fn>q3</fn><url>http://puui.qpic.cn/video_caps/0/</url></pd>
     * </pl>
     *
     * 名称	父节点	含义	                    可为空	样例
     * pd	pl
     * r	pd	一张物理截图有多少行	        不为空	5
     * c	pd	一张物理截图每行有几张小图	    不为空	5
     * w	pd	预览截图宽度	                不为空	240  px
     * h	pd	预览截图长度	                不为空	135  px
     * cd	pd	截图与截图间的时间间隔	        不为空	10  单位秒
     * fmt	pd	截图格式编号	                不为空	40003
     * fn	pd	截图格式对应的（部分）文件名	不为空	q3
     * ur	pdl	截图下载地址前缀	            不为空	http://puui.qpic.cn/video_caps/0/
     *
     * 预览图（大图）的下载地址拼接：${url}${vid}${fn}.${idx}.jpg/0
     * 其中idx不是从0开始，是从1开始
     * 样例：http://puui.qpic.cn/video_caps/0/g0027ko5qhe.q3.1.jpg/0
     *
     * （2）缩略图协议数据的获取方式
     * 调用openMediaPlayer起播，后台鉴权通过后会回调onNetVideoInfo（就是返回清晰度列表的回调）返回相应信息
     * videoInfo.getPLString()：就是上面（1）中 缩略图协议string
     * videoInfo.getLnk()： 就是上面（1）中拼接下载地址中的${vid}参数
     * videoInfo.getPLType()：mPLString的格式，一般是XML格式
     *
     * （3）缩略图的数量
     * 缩略图协议中只返回了每张大图的下载地址，但是一共有多少张，或者说有效的是多少张，要结合后台的时长控制。
     * 后台可以控制每个vid的播放时长，比如试看的vid，只返回了5分钟的时长。
     * 时长是onVideoPrepared回调时，通过KTTV_IMediaPlayer的getDuration()获取。
     *
     * 通过vid的总时长，（1）中的cd字段（截图与截图间的时间间隔），就可以知道这个vid一共有多少张有效缩略图。
     * 通过（1）中的r（一张物理截图有多少行）、c（一张物理截图每行有几张小图），就知道需要下载几张大图，
     * 就知道了下载地址中的${idx}有效范围。
     *
     */


    /**
     * 小图共几列
     */
    public int column;

    /**
     * 小图共几行
     */
    public int row;

    /**
     * 截图格式对应的（部分）文件名
     */
    public String fileName;

    /**
     * 请求前缀
     */
    public String url;

    /**
     * 图片时间间隔
     */
    public int cd;

    /**
     * 预览截图宽度
     */
    public int width;

    /**
     * 预览截图高度
     */
    public int height;

    /**
     * 一张大图的时间总长度
     */
    public int getImagePierod() {
        return column * row * cd;
    }

    /**
     * 一张大图的小图总个数
     */
    public int getImageTotal() {
        return column * row;
    }

    /**
     * 判断是否有效
     *
     * @return
     */
    public boolean isValid() {
        return column > 0 && row > 0 && !TextUtils.isEmpty(fileName) && !TextUtils.isEmpty(url) && cd > 0 && width > 0 && height > 0;
    }
}
