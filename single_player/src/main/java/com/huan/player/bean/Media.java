package com.huan.player.bean;

import java.io.Serializable;

public class Media implements Serializable {
    private static final long serialVersionUID = -4315976953910550837L;

    private int id;
    private String vid;
    private String cid;
    private String title;
    private int drm; //0免费视频 1普通付费视频 2 drm付费视频
    private int duration;
    private String pic;
    private String picPath;
    private String publishDate;//发布时间
    private String cdnUrl;
    private String zxPlayUrl;
    private String hwPlayUrl;
    private int encryptionType;  //0 腾讯播放器， 1 系统播放器
    private int positiveTrailer = 1;
    private int headTime; //单位是秒
    private int tailTime; // 单位是秒
    private String code;
    private String movieCode;
    private String programCode; // 这个值跟code一样，原因是因为在自定义小窗口视频时，返回的字段不一样
    private int playerType = -1;  //-1 使用原来的encryptionType字段；playerType = 0 （加密、ott）1（清流、ott）2 （清流、iptv） 3(加密、ott)

    private String rhProgramCode;// 陕西融合包的节目code
    private String rhMovieCode = "";//陕西融合包的剧集code

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", vid='" + vid + '\'' +
                ", cid='" + cid + '\'' +
                ", title='" + title + '\'' +
                ", drm=" + drm +
                ", duration=" + duration +
                ", pic='" + pic + '\'' +
                ", picPath='" + picPath + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", cdnUrl='" + cdnUrl + '\'' +
                ", zxPlayUrl='" + zxPlayUrl + '\'' +
                ", hwPlayUrl='" + hwPlayUrl + '\'' +
                ", encryptionType='" + encryptionType + '\'' +
                '}';
    }

    public int getPositiveTrailer() {
        return positiveTrailer;
    }

    public void setPositiveTrailer(int positiveTrailer) {
        this.positiveTrailer = positiveTrailer;
    }

    public int getEncryptionType() {
        if (playerType == 0 || playerType == 3) {
            return 0;
        } else if (playerType == 2 || playerType == 1) {
            return 1;
        } else if (playerType == -1) {
            return encryptionType;
        }
        return playerType;
    }

    public void setEncryptionType(int encryptionType) {
        this.encryptionType = encryptionType;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrm() {
        return drm;
    }

    public void setDrm(int drm) {
        this.drm = drm;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getCdnUrl() {
        return cdnUrl;
    }

    public void setCdnUrl(String cdnUrl) {
        this.cdnUrl = cdnUrl;
    }

    public String getZxPlayUrl() {
        return zxPlayUrl;
    }

    public void setZxPlayUrl(String zxPlayUrl) {
        this.zxPlayUrl = zxPlayUrl;
    }

    public String getHwPlayUrl() {
        return hwPlayUrl;
    }

    public void setHwPlayUrl(String hwPlayUrl) {
        this.hwPlayUrl = hwPlayUrl;
    }

    public int getHeadTime() {
        return headTime;
    }

    public void setHeadTime(int headTime) {
        this.headTime = headTime;
    }

    public int getTailTime() {
        return tailTime;
    }

    public void setTailTime(int tailTime) {
        this.tailTime = tailTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMovieCode() {
        return movieCode;
    }

    public void setMovieCode(String movieCode) {
        this.movieCode = movieCode;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getRhProgramCode() {
        return rhProgramCode;
    }

    public void setRhProgramCode(String rhProgramCode) {
        this.rhProgramCode = rhProgramCode;
    }

    public String getRhMovieCode() {
        return rhMovieCode;
    }

    public void setRhMovieCode(String rhMovieCode) {
        this.rhMovieCode = rhMovieCode;
    }
}
