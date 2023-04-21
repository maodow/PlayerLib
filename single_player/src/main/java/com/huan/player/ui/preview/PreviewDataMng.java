package com.huan.player.ui.preview;


import android.graphics.drawable.Drawable;

import androidx.recyclerview.widget.RecyclerView;

import com.huan.player.constant.Logcat;

import java.util.concurrent.TimeUnit;

import tv.huan.hwsystemsdk.player.adapter.PlayerPreviewAdapter;
import tv.huan.hwsystemsdk.player.preview.PreviewImageMng;

/**
 * description:
 *
 * @author
 */
public class PreviewDataMng {
    private final String TAG = "HWPlayer";

    /**
     * 缩略图协议数据
     */
    private PreviewData mPreviewData = null;

    private int mTotalItems;

    private int mIntervalMillis;

    private long mDurationMillis;

    private String mlnk;
    private String mPlInfo = "";
    private int mPlType = 0;

    private PreviewImageMng previewImageMng;


    public PreviewDataMng() {
        previewImageMng = new PreviewImageMng();
    }

    /**
     * @param plString 预览图播放规则
     * @param lnk      播放器返回的vid
     * @param plType   预览图播放规则，的格式
     */
    public void setPreviewDataMng(String plString, String lnk, int plType) {
        mTotalItems = 0;
        mIntervalMillis = 0;
        mDurationMillis = 0;
        mlnk = lnk;
        mPlInfo = plString;
        mPlType = plType;
        previewImageMng.clearData();
        mPreviewData = PreviewUtils.getPreviewData(plString, plType);
        Logcat.INSTANCE.dTag("### setPreviewDataMng mlnk:" + mlnk);
    }

    public void setVideoDuration(long durationMillis) {
        mDurationMillis = durationMillis;
        //结合mDurationMillis、mPreviewData计算缩略图总Item个数
        if (mPreviewData != null) {
            mIntervalMillis = (int) TimeUnit.SECONDS.toMillis(mPreviewData.cd);
            mTotalItems = (int) (durationMillis / mIntervalMillis + 1);
        }
        Logcat.INSTANCE.dTag("### setVideoDuration durationMillis:" + mDurationMillis);
        Logcat.INSTANCE.dTag("### setVideoDuration mIntervalMillis:" + mIntervalMillis);
        Logcat.INSTANCE.dTag("### setVideoDuration mTotalItems:" + mTotalItems);

        if (isPreviewDataValid())
            previewImageMng.initData(mPreviewData, mTotalItems, mlnk, mPlInfo, mPlType);
    }

    public void setAdapter(PlayerPreviewAdapter adapter) {
        previewImageMng.setAdapter(adapter);
    }

    public void setRecycler(RecyclerView recycler) {
        previewImageMng.setRecycler(recycler);
    }

    public int getTotalItems() {
        return mTotalItems;
    }

    public int getIntervalMillis() {
        return mIntervalMillis;
    }

    public String getMlnk() {
        return mlnk;
    }

    public int getItemIndexByPosition(int videoPosition) {
        if (mPreviewData != null && mIntervalMillis != 0) {
            return videoPosition / mIntervalMillis;
        }
        return 0;
    }

    public int getVideoPositionByIdx(int index) {
        if (mIntervalMillis > 0) {
            return index * mIntervalMillis;
        } else {
            return 0;
        }
    }

    /**
     *暂时关闭图片预览图功能
     * 图片是从后台拉取的，不是用播放器截取的
     */
    public boolean isPreviewDataValid() {
        if (mPreviewData == null || !mPreviewData.isValid() || mTotalItems <= 0 || true) {
            return false;
        }
        return true;
    }

    public Drawable getPreviewDrawableByIdx(int index) {
        if (isPreviewDataValid())
            return previewImageMng.getPreviewDrawableByIdx(index);
        return null;
    }

    /**
     * 0 按键抬起， 1 按下
     */
    public void onKeyStatus(int keyStatus) {
        previewImageMng.setKeyStatus(keyStatus);
    }

    public void notifyFailingRefresh() {
        if (isPreviewDataValid())
            previewImageMng.notifyFailingRefresh();
    }

    public void onDestroy() {
        previewImageMng.onDestroy();
        mPreviewData = null;
        previewImageMng = null;
    }
}
