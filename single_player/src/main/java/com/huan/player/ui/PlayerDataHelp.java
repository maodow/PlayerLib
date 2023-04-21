package com.huan.player.ui;


import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.huan.player.bean.Columns;
import com.huan.player.bean.Media;
import com.huan.player.bean.ProgramDetail;
import com.huan.player.constant.AlbumType;
import com.huan.player.constant.Logcat;
import com.huan.player.constant.Utils;
import com.huan.player.contract.BufferViewContract;
import com.huan.player.contract.ControllerViewContract;
import com.huan.player.contract.DetainmentViewContract;
import com.huan.player.contract.OnPlayerButEventListener;
import com.huan.player.contract.OnPlayerListener;
import com.huan.player.contract.OnPlayerSettingsCallback;
import com.huan.player.contract.OnVideoEpisodeChoice;
import com.huan.player.ui.view.VideoEpisodeChoiceView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PlayerDataHelp {
    private final String TAG = "HWPlayer";
    private Set listener = Collections.synchronizedSet(new HashSet<OnVideoEpisodeChoice>());
    private List<String> episodePointingList = new ArrayList<>(); //集数标签集合
    private int SECTION_NUM;  //分页展示多少集
    private int pagingCount;// 集数分多少页
    private int currentPointingIndex; // 当前集数标签的数据下标
    private int currentMediaIndex = 0; //当前播放 List<Media> 下标
    private int lastCurrentMediaIndex = 0; //之前的播放 List<Media> 下标
    private int nextMediaIndex = 0; //记录切换时的下标
    private int selectPlayerType;  //播放器类型 0 腾讯播放器, 1 清流播放器
    private long historyProgress;
    private int historyMediaIndex;
    private boolean isContinuousPlay; //是否开放续播功能
    private boolean isPosterVisible;

    private String userId;  // 用户账号
    private String classId;

    private boolean filmTryAndSee;
    private int filmTryAndSeeTimeMillisecond;
    private boolean moreFunctions;
    private boolean backDetention;

    private List<Media> medias;
    private ProgramDetail album;
    private Columns column;

    private OnPlayerListener onPlayerListener;
    private OnPlayerSettingsCallback onPlayerSettingsCallback;
    private OnPlayerButEventListener onPlayerButEventListener;

    private PlayerDataHelp(Builder builder) {
        medias = builder.medias;
        album = builder.album;
        column = builder.column;
        SECTION_NUM = builder.sectionNum;
        selectPlayerType = builder.selectPlayerType;
        userId = builder.userId;
        classId = builder.classId;
        filmTryAndSee = builder.filmTryAndSee;
        filmTryAndSeeTimeMillisecond = builder.filmTryAndSeeTimeMillisecond;
        isContinuousPlay = builder.isContinuousPlay;
        isPosterVisible = builder.isPosterVisible;
        currentMediaIndex = builder.currentMediaIndex;
        moreFunctions = builder.moreFunctions;
        backDetention = builder.backDetention;
        onPlayerListener = builder.onPlayerListener;
        onPlayerSettingsCallback = builder.onPlayerSettingsCallback;
    }

    public int getSelectPlayerType() {
        return selectPlayerType;
    }

    public String getCookie() {
        return "";

    }


    public OnPlayerListener getOnPlayerListener() {
        return onPlayerListener;
    }

    public OnPlayerSettingsCallback getOnPlayerSettingsCallback() {
        return onPlayerSettingsCallback;
    }

    public OnPlayerButEventListener getOnPlayerButEventListener() {
        return onPlayerButEventListener;
    }

    public String getClassId() {
        return classId;
    }

    public long getHistoryProgress() {
        return historyMediaIndex == currentMediaIndex ? historyProgress : 0;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public ProgramDetail getAlbum() {
        return album;
    }

    public Columns getColumns() {
        return column;
    }

    /**
     * 不是电影和是会员，以及免费影视，不试看
     */
    public boolean isFilmTryAndSee() {
        if (!isPay(currentMediaIndex, false)) {
            return false;
        }
        return isTypeFilmTryAndSee();
    }

    public boolean isMoreFunctions() {
        return moreFunctions;
    }

    public boolean isBackDetention() {
        return backDetention;
    }

    /**
     * 只有电影才可以试看
     *
     * @return true 可以试看 false 不可以试看
     */
    public boolean isTypeFilmTryAndSee() {
        return album.getType() == AlbumType.FILM && filmTryAndSee;
    }

    public int getFilmTryAndSeeTimeMillisecond() {
        return filmTryAndSeeTimeMillisecond;
    }

    public boolean isPlayerFinish() {
        return nextMediaIndex >= medias.size() || isPay();
    }

    public boolean isPosterVisible() {
        return isPosterVisible;
    }

    public int getLastCurrentMediaIndex() {
        return lastCurrentMediaIndex;
    }

    public void setCurrentMediaIndex(int position) {
        this.lastCurrentMediaIndex = this.currentMediaIndex;
        this.currentMediaIndex = position;
    }

    public int getSectionNum() {
        return SECTION_NUM;
    }

    /**
     * 返回媒资数据的下标
     */
    public int getCurrentMediaIndex() {
        return currentMediaIndex;
    }

    /**
     * 返回媒资数据的下标
     *
     * @param index 点击分页集数的下标
     */
    public int getCurrentMediaIndex(int index) {
        return getPagingStartEpisode() + index - 1;
    }


    /**
     * 返回当前的媒资对象
     */
    public Media getCurrentMedia() {
        if (medias == null) {
            new NullPointerException("请先设置影视数据!!!");
        }
        return getCurrentMedia(currentMediaIndex, false);
    }

    public Media getCurrentMedia(int mediaIndex) {
        if (medias == null) {
            new NullPointerException("请先设置影视数据!!!");
        }
        return getCurrentMedia(mediaIndex, false);
    }


    /**
     * 切换下一集
     *
     * @param mediaIndex 媒资list的下标
     */
    public void getNextEpisode(Integer mediaIndex) {
        mediaIndex = Utils.INSTANCE.nullToInt(mediaIndex);
        getCurrentMedia(mediaIndex, true);
        onListenerInvoke();
    }


    /**
     * 切换下一集
     * 没有提示，直接播放完
     */
    public void getNextEpisode() {
        getNextEpisode(currentMediaIndex + 1);
    }

    public boolean getNextEpisode(Integer mediaIndex, boolean isHint) {
        if (mediaIndex >= medias.size() || mediaIndex < 0) {
            if (isHint) {
                Utils.INSTANCE.toast("抱歉，没有当前集数");
            }
            return false;
        }

        if (mediaIndex == currentMediaIndex) {
            if (isHint) {
                Utils.INSTANCE.toast("当前播放的已是第" + (currentMediaIndex + 1) + "集");
            }
            return false;
        }
        getNextEpisode(mediaIndex);
        return true;
    }

    public boolean getNextEpisode(boolean isHint) {
        int nextEpisode = currentMediaIndex + 1;
        if (nextEpisode >= medias.size()) {
            if (isHint) {
                Utils.INSTANCE.toast("已经是最后一集");
            }
            return false;
        }
        getNextEpisode(nextEpisode);
        return true;
    }


    /**
     * 切换上一集
     */
    public boolean getPrevEpisode(boolean isHint) {
        int prevEpisode = currentMediaIndex - 1;
        if (prevEpisode < 0) {
            if (isHint) {
                Utils.INSTANCE.toast("已经是第一集");
            }
            return false;
        }
        getNextEpisode(prevEpisode);
        return true;
    }


    /**
     * 返回集数标签数据
     */
    public List<String> getEpisodePointingList() {
        if (medias == null || SECTION_NUM == 0) {
            new NullPointerException("请先设置媒体数据: 媒体数据集合和分页展示的数量");
        }
        episodePointingList.clear();
        if (medias.size() % SECTION_NUM == 0) {
            pagingCount = medias.size() / SECTION_NUM;
        } else {
            if (medias.size() <= SECTION_NUM) {
                pagingCount = 1;
            } else {
                pagingCount = medias.size() / SECTION_NUM + 1;
            }
        }
        for (int i = 0; i < pagingCount; i++) {
            int start = i * SECTION_NUM + 1;
            int end = start + SECTION_NUM - 1;
            if (end > medias.size()) {
                end = medias.size();
            }
            String temp = "";
            if (start == medias.size()) {
                temp = String.valueOf(start);
            } else {
                temp = start + "-" + end;
            }
            episodePointingList.add(temp);
        }
        return episodePointingList;
    }

    /**
     * 当前分页集数集合
     *
     * @param index 集数标签数据的下标
     */
    public List<Media> getPagingEpisode(Integer index) {
        if (index == null)
            index = 0;
        if (medias == null) {
            new NullPointerException("请先设置媒体数据: medias集合");
        }
        currentPointingIndex = index;
        int count = (index + 1) * SECTION_NUM;
        if (count > medias.size()) {
            count = medias.size();
        }
        return medias.subList(index * SECTION_NUM, count);
    }

    /**
     * 返回集数标签的下标
     */
    public int getCurrentPointingIndex() {
        return currentPointingIndex;
    }

    /**
     * 设置集数标签的下标
     *
     * @param index 集数标签的下标
     */
    public void setCurrentPoinsettingIndex(int index) {
        currentPointingIndex = index;
    }


    /**
     * @return 返回分页的起始集数
     */
    public int getPagingStartEpisode() {
        return currentPointingIndex * SECTION_NUM + 1;
    }

    /**
     * 调用此方法说明集数标签切换了
     *
     * @param currentPointingIndex 集数标签的下标
     * @return 返回分页的起始集数
     */
    public int getPagingStartEpisode(int currentPointingIndex) {
        this.currentPointingIndex = currentPointingIndex;
        return currentPointingIndex * SECTION_NUM + 1;
    }

    /**
     * 集数的recyclerView 是Grid模式时，需要获取集数的下标
     * 返回当前集数的item下标
     */
    public int getEpisodeItemIndex() {
        return currentMediaIndex % SECTION_NUM;
    }

    /**
     * 集数的recyclerView 是Grid模式时，需要获取集数的下标
     * 返回当前集数的item下标
     *
     * @param currentMediaIndex 当前数据集合的下标
     */
    public int getEpisodeItemIndex(Integer currentMediaIndex) {
        if (currentMediaIndex == null) return 0;
        return currentMediaIndex % SECTION_NUM;
    }

    /**
     * 返回集数标签的下标
     *
     * @param mediaIndex 集数的下标
     */
    public int getPointingIndex(Integer mediaIndex) {
        if (mediaIndex == null) return 0;
        return mediaIndex / SECTION_NUM;
    }

    /**
     * 集数切换监听
     */
    public void setOnVideoEpisodeChoice(OnVideoEpisodeChoice onVideoEpisodeChoice) {
        if (onVideoEpisodeChoice == null) return;
        if (!listener.contains(onVideoEpisodeChoice)) {
            listener.add(onVideoEpisodeChoice);
        }
    }


    /**
     * 返回当前的媒资对象
     *
     * @param mediaIndex 媒资list的下标
     */
    private Media getCurrentMedia(int mediaIndex, boolean isSave) {
        if (medias == null) {
            new NullPointerException("请先设置影视数据!!!");
        }
        if (mediaIndex >= medias.size()) {
            if (isSave)
                nextMediaIndex = mediaIndex;
            return null;
        }

        if (isSave) {
            this.lastCurrentMediaIndex = this.currentMediaIndex;
            currentMediaIndex = mediaIndex;
            nextMediaIndex = mediaIndex;
        }

        Media media = medias.get(mediaIndex);

        String platform = onPlayerSettingsCallback.getBoxPlatform();
        String urlParameter = onPlayerSettingsCallback.getPlayerUrlParameter();

        if ("hw".equals(platform)) {
            if (!onPlayerSettingsCallback.isThirdPartyPlayerUrl())
                media.setCdnUrl(media.getHwPlayUrl() + urlParameter);
        } else if ("zx".equals(platform)) {
            if (!onPlayerSettingsCallback.isThirdPartyPlayerUrl())
                media.setCdnUrl(media.getZxPlayUrl() + urlParameter);
        }
        return media;
    }

    /**
     * 调用设置监听的对象
     */
    private void onListenerInvoke() {
        if (listener == null || medias == null) return;
        for (Object item : listener) {
            if (nextMediaIndex >= medias.size()) {
                ((OnVideoEpisodeChoice) item).onCompletion();
            } else if (isPay()) {
                Logcat.INSTANCE.dTag("自动切换下一集，触发支付：" + currentMediaIndex);
                ((OnVideoEpisodeChoice) item).onPay();
            } else {
                ((OnVideoEpisodeChoice) item).onEpisode(this);
            }
        }
    }

    /**
     * 调用支付监听
     */
    public void onPayInvoke() {
        for (Object item :
                listener) {
            ((OnVideoEpisodeChoice) item).onPay();
        }
    }

    /**
     * 获取腾讯的播放凭证
     */
    public void onVirtualTvskey() {
        for (Object item :
                listener) {
            ((OnVideoEpisodeChoice) item).onVirtualTvskey();
        }
    }

    /**
     * 资源错误处理
     */
    public void onErrorInvoke(int code, String msg) {
        for (Object item :
                listener) {
            ((OnVideoEpisodeChoice) item).onError(code, msg);
        }
    }

    /**
     * 是否需要去支付，如果是收费节目，是会员状态也不需要去支付
     *
     * @return true 去支付 false 不需要
     */
    public boolean isPay() {
        return isPay(currentMediaIndex, true);
    }

    public boolean isPay(Integer currentMediaIndex) {
        return isPay(currentMediaIndex, true);
    }

    public boolean isPay(Integer currentMediaIndex, boolean isTryAndSee) {
        currentMediaIndex = Utils.INSTANCE.nullToInt(currentMediaIndex);
        int playType = -1;

        int type = album.getType();

        if (type == AlbumType.VARIETY || type == AlbumType.GAME || type == AlbumType.MUSIC) {
            playType = column.getPlayType();
        } else {
            playType = album.getPlayType();
        }
        int authStatus = onPlayerSettingsCallback.getAuth();

        // LogUtils.dTag(TAG, currentMediaIndex + ">>>>>>>isPay authStatus:" + authStatus);
        if (authStatus == 1) {
            return false;
        }

        //  LogUtils.dTag(TAG, currentMediaIndex + ">>>>>>>isPay playType: " + playType);
        //如果是试看，就不跳转支付
        if (isTypeFilmTryAndSee() && isTryAndSee) {
            return false;
        }

        //  LogUtils.dTag(TAG,  ">>>>>>>isFusion: " + trimEmpty);
        if (playType == 0) {
            return true;
        }
        //  LogUtils.dTag(TAG,  ">>>>>>>isPay currentMediaIndex: " + currentMediaIndex);
        Media media = getCurrentMedia(currentMediaIndex);
        if (media == null) return false;
        if (album.getType() != AlbumType.FILM) {
            if (playType == -1 && media.getDrm() != 0) {
                return true;
            }
            if (playType > 0 && currentMediaIndex + 1 > playType) {
                return true;
            }
        } else {
            if (media.getDrm() != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 历史的播放记录
     * @param endFlag 是否播放完了
     */
    public void setHistoryProgress(@Nullable Integer endFlag, @Nullable Integer historyMadieIndex, @Nullable Integer historyProgress) {
        if (isContinuousPlay && historyMadieIndex != null && historyProgress != null && endFlag != null) {
            this.historyProgress = historyProgress;
            this.historyMediaIndex = historyMadieIndex;
            this.currentMediaIndex = historyMadieIndex;
            if (endFlag == 1)
                this.historyProgress = 0;
        }
    }

    public void onDestroy() {
        listener.clear();
        onPlayerButEventListener = null;
        onPlayerSettingsCallback = null;
        onPlayerListener = null;
    }

    public static class Builder {
        private List<Media> medias;
        private ProgramDetail album;
        private Columns column;
        private String classId;  //挽留数据需要的参数
        private int sectionNum;  //分页展示多少集
        private String userId;  // 用户账号

        private int currentMediaIndex = 0; //当前播放 List<Media> 下标
        private boolean isContinuousPlay = true; //是否开放续播功能
        private boolean filmTryAndSee = false; //是否开启免费试看
        private int filmTryAndSeeTimeMillisecond;
        private int selectPlayerType = 1;
        private boolean isPosterVisible = true;
        private boolean moreFunctions = true;
        private boolean backDetention = true;
        private OnPlayerListener onPlayerListener;
        private OnPlayerSettingsCallback onPlayerSettingsCallback;
        private OnPlayerButEventListener onPlayerButEventListener;

        /**
         * 自己定义UI
         */
        private BufferViewContract buffView;
        private ControllerViewContract controllerView;
        private DetainmentViewContract detainmentView;
        private VideoEpisodeChoiceView videoEpisodeChoiceView;

        public Builder() {

        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setMedias(List<Media> medias) {
            this.medias = medias;
            return this;
        }

        public Builder setAlbum(ProgramDetail album) {
            this.album = album;
            return this;
        }

        public Builder setColumn(Columns column) {
            this.column = column;
            return this;
        }

        public Builder setClassId(String classId) {
            this.classId = classId;
            return this;
        }

        /**
         * 设置集数分页的个数
         *
         * @param sectionNum
         */
        public Builder setSectionNum(int sectionNum) {
            this.sectionNum = sectionNum;
            return this;
        }

        /**
         * 选择使用哪种播放器  selectPlayerType 一定要跟接口的 encryption 的值对应
         *
         * @param selectPlayerType 0 腾讯播放器 1 系统播放器
         * @see Media
         * 当选择系统播放器是 根据接口中的 encryption 自动切换到腾讯播放器
         * 当选择腾讯播放器是，encryption 都是使用的腾讯播放器
         */
        public Builder setSelectPlayerType(int selectPlayerType) {
            this.selectPlayerType = selectPlayerType;
            return this;
        }


        /**
         * @param filmTryAndSee 是否开起电影试看，
         * @param millisecond   试看时长（单位毫秒
         */
        public Builder setFilmTryAnd(boolean filmTryAndSee, int millisecond) {
            this.filmTryAndSee = filmTryAndSee;
            filmTryAndSeeTimeMillisecond = millisecond;
            return this;
        }

        public Builder setContinuousPlay(boolean isContinuousPlay) {
            this.isContinuousPlay = isContinuousPlay;
            return this;
        }

        /**
         * @param isPosterVisible 设置海报是否显示
         *                        默认值显示
         */
        public Builder setPosterVisible(boolean isPosterVisible) {
            this.isPosterVisible = isPosterVisible;
            return this;
        }

        /**
         * 起始播放的集数
         * 0~n
         *
         * @param currentMediaIndex 媒资集合的下标
         */
        public Builder setPlayerEpisode(int currentMediaIndex) {
            this.currentMediaIndex = currentMediaIndex;
            return this;
        }

        /**
         * 是否开启更多功能
         * 默认开启
         *
         * @param moreFunctions true 开启 false 关闭
         */
        public Builder setMoreFunctions(boolean moreFunctions) {
            this.moreFunctions = moreFunctions;
            return this;
        }

        /**
         * 是否显示退出挽留
         * 默认开启
         *
         * @param backDetention true 开启 false 关闭
         */
        public Builder setBackDetention(boolean backDetention) {
            this.backDetention = backDetention;
            return this;
        }

        public Builder setOnPlayerListener(OnPlayerListener onPlayerListener) {
            this.onPlayerListener = onPlayerListener;
            return this;
        }

        public Builder setOnPlayerSettingsCallback(OnPlayerSettingsCallback onPlayerSettingsCallback) {
            this.onPlayerSettingsCallback = onPlayerSettingsCallback;
            return this;
        }

        public Builder setOnPlayerButEventListener(OnPlayerButEventListener onPlayerButEventListener) {
            this.onPlayerButEventListener = onPlayerButEventListener;
            return this;
        }

        public PlayerDataHelp build() {
            if (medias == null || album == null || column == null
                    || onPlayerListener == null || onPlayerButEventListener == null
                    || TextUtils.isEmpty(classId)) {
                new NullPointerException("必要参数为Null ! ! !");
            }
            return new PlayerDataHelp(this);
        }
    }
}
