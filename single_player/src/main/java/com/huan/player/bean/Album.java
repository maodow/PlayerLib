package com.huan.player.bean;

import java.io.Serializable;

public class Album implements Serializable {

    private static final long serialVersionUID = 2233263498353702029L;
    private String newPicHz;
    private String newPicVt;
    private String picVtPath;
    private String picHzPath;
    private String title;
    private int type;
    private int payStatus;
    private String cid;
    private String episodeUpdated;
    private String episodeAll;
    private int unicast;
    private int showUpdated;
    private String score;
    private String publishDate;



    @Override
    public String toString() {
        return "Album{" +
                "newPicHz='" + newPicHz + '\'' +
                ", newPicVt='" + newPicVt + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", payStatus=" + payStatus +
                ", cid='" + cid + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getNewPicHz() {
        return newPicHz;
    }

    public void setNewPicHz(String newPicHz) {
        this.newPicHz = newPicHz;
    }

    public String getNewPicVt() {
        return newPicVt;
    }

    public void setNewPicVt(String newPicVt) {
        this.newPicVt = newPicVt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPicVtPath() {
        return picVtPath;
    }

    public void setPicVtPath(String picVtPath) {
        this.picVtPath = picVtPath;
    }

    public String getEpisodeUpdated() {
        return episodeUpdated;
    }

    public void setEpisodeUpdated(String episodeUpdated) {
        this.episodeUpdated = episodeUpdated;
    }

    public String getEpisodeAll() {
        return episodeAll;
    }

    public void setEpisodeAll(String episodeAll) {
        this.episodeAll = episodeAll;
    }

    public int getUnicast() {
        return unicast;
    }

    public void setUnicast(int unicast) {
        this.unicast = unicast;
    }

    public int getShowUpdated() {
        return showUpdated;
    }

    public void setShowUpdated(int showUpdated) {
        this.showUpdated = showUpdated;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPicHzPath() {
        return picHzPath;
    }

    public void setPicHzPath(String picHzPath) {
        this.picHzPath = picHzPath;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}
