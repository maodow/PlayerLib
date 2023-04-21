package com.huan.player.bean;

import java.util.ArrayList;

/**
 * 设置本次播放的，缩略图数据
 * plString：缩略图下载地址&规格信息
 * lnk：缩略图对应的最终vid（存在vid映射，openMediaPlayer起播的vid可能不是最终的vid）
 * plType：plString的数据格式类型 一般是xml的
 *
 * definitionList 清晰度信息
 */
public class NetVideoInfo {

    private String mPL;
    private String mLnk;
    private int mPLType;

    private ArrayList<DefnInfo> definitionList;


    public String getmPL() {
        return mPL;
    }

    public void setmPL(String mPL) {
        this.mPL = mPL;
    }

    public String getmLnk() {
        return mLnk;
    }

    public void setmLnk(String mLnk) {
        this.mLnk = mLnk;
    }

    public int getmPLType() {
        return mPLType;
    }

    public void setmPLType(int mPLType) {
        this.mPLType = mPLType;
    }

    public ArrayList<DefnInfo> getDefinitionList() {
        return definitionList;
    }

    public void setDefinitionList(ArrayList<DefnInfo> definitionList) {
        this.definitionList = definitionList;
    }
}
