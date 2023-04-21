package com.huan.player.ui.preview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * description:
 *
 * @author
 * @date 2020/1/5
 */
public class PreviewDrawable extends Drawable {
    private final Bitmap mPreview;
    private final Rect mSrc = new Rect(0, 0, 0, 0);

    public PreviewDrawable(Bitmap preview, Rect src) {
        this.mPreview = preview;
        if (src != null) {
            this.mSrc.set(src);
        }
    }

    public void setSrcRect(Rect src) {
        mSrc.set(src);
        invalidateSelf();
    }

    @Override
    public int getIntrinsicWidth() {
        return mSrc.width();
    }

    @Override
    public int getIntrinsicHeight() {
        return mSrc.height();
    }

    @Override
    public int getMinimumWidth() {
        return mSrc.width();
    }

    @Override
    public int getMinimumHeight() {
        return mSrc.height();
    }

    @Override
    public void draw(Canvas canvas) {
        if (mPreview != null &&!mPreview.isRecycled())
        canvas.drawBitmap(mPreview, mSrc, getBounds(), null);
    }

    @Override
    public void setAlpha(int alpha) {
        // EMPTY
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        // EMPTY
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
