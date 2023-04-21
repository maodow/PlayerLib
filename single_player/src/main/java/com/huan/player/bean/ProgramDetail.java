package com.huan.player.bean;

import android.text.TextUtils;

import java.io.Serializable;

public class ProgramDetail implements Serializable {
    private static final long serialVersionUID = 3920932587437194358L;

    private String id;
    private String cid;
    private String title;
    private String columnId;
    private int type;
    private int isTrailer;
    private String seconditle;
    private String director;
    private String langue;
    private int status;
    private int positiveTrailer;
    private String score;  //评分
    private String subtype;
    private String tag;
    private String copyright;
    private String leadingActor;
    private String guests;
    private String publishDate;
    private String episodeAll;
    private String episodeUpdated;
    private String newPicVt;
    private String newPicHz;
    private String picVtPath;
    private String picHzPath;
    private int payStatus; //付费标识  8免费 其它付费
    private String areaName;
    private String year;
    private String videoIds;
    private String description;
    private String resolutionList;
    private String url;
    private String encryptionType;
    private int playType = -1;
    private int unicast;
    private int times;//观看次数
    private int favNum;//收藏次数
    private int showUpdated;//是否显示更新集数
    private String praise; //点赞数
    private int showType = 1; //小窗口播放类型，1、不播放 2、自动播放第一集 3、自定义视频
    private String seriCode; // 安徽的融合包剧头code

    private String rhSeriCode; // 陕西融合包剧头code


    @Override
    public String toString() {
        return "ProgramDetail{" +
                "id='" + id + '\'' +
                ", cid='" + cid + '\'' +
                ", title='" + title + '\'' +
                ", columnId='" + columnId + '\'' +
                ", type=" + type +
                ", isTrailer=" + isTrailer +
                ", seconditle='" + seconditle + '\'' +
                ", director='" + director + '\'' +
                ", language='" + langue + '\'' +
                ", status=" + status +
                ", positiveTrailer=" + positiveTrailer +
                ", score='" + score + '\'' +
                ", subtype='" + subtype + '\'' +
                ", tag='" + tag + '\'' +
                ", copyright='" + copyright + '\'' +
                ", leadingActor='" + leadingActor + '\'' +
                ", guests='" + guests + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", episodeAll='" + episodeAll + '\'' +
                ", episodeUpdated='" + episodeUpdated + '\'' +
                ", newPicVt='" + newPicVt + '\'' +
                ", newPicHz='" + newPicHz + '\'' +
                ", picVtPath='" + picVtPath + '\'' +
                ", picHzPath='" + picHzPath + '\'' +
                ", payStatus=" + payStatus +
                ", areaName='" + areaName + '\'' +
                ", year='" + year + '\'' +
                ", videoIds='" + videoIds + '\'' +
                ", description='" + description + '\'' +
                ", resolutionList='" + resolutionList + '\'' +
                ", url='" + url + '\'' +
                ", encryptionType='" + encryptionType + '\'' +
                '}';
    }

    public String getRhSeriCode() {
        return rhSeriCode;
    }

    public void setRhSeriCode(String rhSeriCode) {
        this.rhSeriCode = rhSeriCode;
    }

    public int getShowUpdated() {
        return showUpdated;
    }

    public void setShowUpdated(int showUpdated) {
        this.showUpdated = showUpdated;
    }

    public int getPlayType() {
        return playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsTrailer() {
        return isTrailer;
    }

    public void setIsTrailer(int isTrailer) {
        this.isTrailer = isTrailer;
    }

    public String getSeconditle() {
        return seconditle;
    }

    public void setSeconditle(String seconditle) {
        this.seconditle = seconditle;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPositiveTrailer() {
        return positiveTrailer;
    }

    public void setPositiveTrailer(int positiveTrailer) {
        this.positiveTrailer = positiveTrailer;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getLeadingActor() {
        return leadingActor;
    }

    public void setLeadingActor(String leadingActor) {
        this.leadingActor = leadingActor;
    }

    public String getGuests() {
        return guests;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getEpisodeAll() {
        return episodeAll;
    }

    public void setEpisodeAll(String episodeAll) {
        this.episodeAll = episodeAll;
    }

    public String getEpisodeUpdated() {
        return episodeUpdated;
    }

    public void setEpisodeUpdated(String episodeUpdated) {
        this.episodeUpdated = episodeUpdated;
    }

    public String getNewPicVt() {
        return newPicVt;
    }

    public void setNewPicVt(String newPicVt) {
        this.newPicVt = newPicVt;
    }

    public String getNewPicHz() {
        return newPicHz;
    }

    public void setNewPicHz(String newPicHz) {
        this.newPicHz = newPicHz;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVideoIds() {
        return videoIds;
    }

    public void setVideoIds(String videoIds) {
        this.videoIds = videoIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResolutionList() {
        return resolutionList;
    }

    public void setResolutionList(String resolutionList) {
        this.resolutionList = resolutionList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicVtPath() {
        return TextUtils.isEmpty(picVtPath) ? newPicVt : picVtPath;
    }

    public void setPicVtPath(String picVtPath) {
        this.picVtPath = picVtPath;
    }

    public String getPicHzPath() {
        return picHzPath;
    }

    public void setPicHzPath(String picHzPath) {
        this.picHzPath = picHzPath;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public int getUnicast() {
        return unicast;
    }

    public void setUnicast(int unicast) {
        this.unicast = unicast;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getFavNum() {
        return favNum;
    }

    public void setFavNum(int favNum) {
        this.favNum = favNum;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getSeriCode() {
        return seriCode;
    }

    public void setSeriCode(String seriCode) {
        this.seriCode = seriCode;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }
}
