package com.huan.player.bean;

public class DefnInfo {
    private String mDefn;
    private String mDefnShowName;
    private int isVip;
    private int mDefnId;
    private long fileSize;
    private String mDefnName;
    private String mDefnRate;

    private boolean itemFocus;
    private boolean itemIconShow;

    public DefnInfo() {
    }

    public void setmFnName(String var1) {
        this.mDefnName = var1;
    }

    public String getmFnName() {
        return this.mDefnName;
    }

    public String getmDefnRate() {
        return this.mDefnRate;
    }

    public void setmDefnRate(String var1) {
        this.mDefnRate = var1;
    }

    public String getmDefn() {
        return this.mDefn;
    }

    public void setmDefnId(int var1) {
        this.mDefnId = var1;
    }

    public int getmDefnId() {
        return this.mDefnId;
    }

    public void setmDefn(String var1) {
        this.mDefn = var1;
    }

    public void setmDefnName(String var1) {
        this.mDefnShowName = var1;
    }

    public String getmDefnName() {
        return this.mDefnShowName;
    }

    public int isVip() {
        return this.isVip;
    }

    public void setVip(int var1) {
        this.isVip = var1;
    }

    public boolean isAudioOnly() {
        return "audio".equalsIgnoreCase(this.mDefn);
    }

    public void setFileSize(long var1) {
        this.fileSize = var1;
    }

    public long getFileSize() {
        return this.fileSize;
    }


    public boolean isItemFocus() {
        return itemFocus;
    }

    public void setItemFocus(boolean itemFocus) {
        this.itemFocus = itemFocus;
    }

    public boolean isItemIconShow() {
        return itemIconShow;
    }

    public void setItemIconShow(boolean itemIconShow) {
        this.itemIconShow = itemIconShow;
    }

}
