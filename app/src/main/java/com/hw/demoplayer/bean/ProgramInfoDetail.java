package com.hw.demoplayer.bean;

import com.huan.player.bean.Album;
import com.huan.player.bean.Columns;
import com.huan.player.bean.Media;
import com.huan.player.bean.ProgramDetail;

import java.io.Serializable;
import java.util.List;

public class ProgramInfoDetail implements Serializable {
    private static final long serialVersionUID = 6796339915858640152L;
    private ProgramDetail album;
    private Columns column;
    private List<Media> medias;
    private List<Album> recAlbums;
    private String recClassId;
    private String auth; //1鉴权通过 其它不通过
    private TryMediaBean tryMedia;
    private Media shortMedia; //小窗口自定义媒资


    public List<Album> getRecAlbums() {
        return recAlbums;
    }

    public void setRecAlbums(List<Album> recAlbums) {
        this.recAlbums = recAlbums;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ProgramDetail getAlbum() {
        return album;
    }

    public void setAlbum(ProgramDetail album) {
        this.album = album;
    }

    public Columns getColumn() {
        return column == null ? new Columns() : column;
    }

    public void setColumn(Columns column) {
        this.column = column;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public String getRecClassId() {
        return recClassId;
    }

    public void setRecClassId(String recClassId) {
        this.recClassId = recClassId;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    @Override
    public String toString() {
        return "";
    }

    public TryMediaBean getTryMedia() {
        return tryMedia;
    }

    public void setTryMedia(TryMediaBean tryMedia) {
        this.tryMedia = tryMedia;
    }

    public Media getShortMedia() {
        return shortMedia;
    }

    public void setShortMedia(Media shortMedia) {
        this.shortMedia = shortMedia;
    }

    public static class TryMediaBean {
        /**
         * id : null
         * vid : null
         * cid : mzc002001hfdcja
         * title : null
         * drm : null
         * duration : 0
         * pic : null
         * picPath : null
         * publishDate : null
         * cdnUrl : null
         * movieCode : null
         * zxPlayUrl : http://vodmedia-zx.sca.bcs.ottcn.com:8089/28000002/m_ystenvod0000000000000002113361/index.m3u8?x
         * hwPlayUrl : http://223.87.5.102:8082/EDS/RedirectPlay/icntv/vod/p_cptynrpt0000000000000002105542/m_cptynrpt0000000000000002113361
         * encryptionType : null
         * positiveTrailer : null
         * headTime : null
         * tailTime : null
         */

        private String id;
        private String vid;
        private String cid;
        private String title;
        private int drm;
        private int duration;
        private String pic;
        private String picPath;
        private String publishDate;
        private String cdnUrl;
        private String movieCode;
        private String zxPlayUrl;
        private String hwPlayUrl;
        private int encryptionType;
        private int positiveTrailer;
        private int headTime;
        private int tailTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getPicPath() {
            return picPath;
        }

        public void setPicPath(String picPath) {
            this.picPath = picPath;
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

        public String getMovieCode() {
            return movieCode;
        }

        public void setMovieCode(String movieCode) {
            this.movieCode = movieCode;
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

        public int getEncryptionType() {
            return encryptionType;
        }

        public void setEncryptionType(int encryptionType) {
            this.encryptionType = encryptionType;
        }

        public int getPositiveTrailer() {
            return positiveTrailer;
        }

        public void setPositiveTrailer(int positiveTrailer) {
            this.positiveTrailer = positiveTrailer;
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
    }
}
