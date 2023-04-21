package com.huan.player.sdk

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceView
import android.view.View
import com.huan.player.constant.Logcat
import tv.huan.hwplayer.config.HWPlayerSettingOptions

class MySurfaceView : SurfaceView {

    private val TAG = MySurfaceView::class.java.name
    private var currentVideoWidth = 0
    private var currentVideoHeight = 0
    private var mVideoSarNum = 0
    private var mVideoSarDen = 0

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        currentVideoWidth = 0
        currentVideoHeight = 0
    }

    fun setVideoSize(
        currentVideoWidth: Int,
        currentVideoHeight: Int,
        videoSarNum: Int,
        videoSarDen: Int
    ) {
        if (videoSarNum > 0 && videoSarDen > 0) {
            mVideoSarNum = videoSarNum
            mVideoSarDen = videoSarDen
        }

        if (this.currentVideoWidth != currentVideoWidth || this.currentVideoHeight != currentVideoHeight) {
            this.currentVideoWidth = currentVideoWidth
            this.currentVideoHeight = currentVideoHeight
            requestLayout()
        }
    }

    override fun setRotation(rotation: Float) {
        if (rotation != getRotation()) {
            super.setRotation(rotation)
            requestLayout()
        }
    }

    override fun onMeasure(widthMeasureSpe: Int, heightMeasureSpe: Int) {
        super.onMeasure(widthMeasureSpe, heightMeasureSpe)
        var widthMeasureSpec = widthMeasureSpe
        var heightMeasureSpec = heightMeasureSpe

        Logcat.dTag("onMeasure " + " [" + this.hashCode() + "] ")
        val viewRotation = rotation.toInt()
        val videoWidth = currentVideoWidth
        var videoHeight = currentVideoHeight


        var parentHeight = (parent as View).measuredHeight
        var parentWidth = (parent as View).measuredWidth
        if (parentWidth != 0 && parentHeight != 0 && videoWidth != 0 && videoHeight != 0) {
            if (HWPlayerSettingOptions.VIDEO_IMAGE_DISPLAY_TYPE === HWPlayerSettingOptions.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT) {
                if (viewRotation == 90 || viewRotation == 270) {
                    val tempSize = parentWidth
                    parentWidth = parentHeight
                    parentHeight = tempSize
                }
                /**强制充满 */
                videoHeight = videoWidth * parentHeight / parentWidth
            }
        }
        // 如果判断成立，则说明显示的TextureView和本身的位置是有90度的旋转的，所以需要交换宽高参数。
        if (viewRotation == 90 || viewRotation == 270) {
            widthMeasureSpec = heightMeasureSpec
            heightMeasureSpec = widthMeasureSpec
        }

        var width = View.getDefaultSize(videoWidth, widthMeasureSpec)
        var height = View.getDefaultSize(videoHeight, heightMeasureSpec)
        if (videoWidth > 0 && videoHeight > 0) {
            val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
            val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
            val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
            val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
            Logcat.dTag(
                "widthMeasureSpec  [" + MeasureSpec.toString(widthMeasureSpec) + "]"
            )
            Logcat.dTag(
                "heightMeasureSpec [" + MeasureSpec.toString(heightMeasureSpec) + "]"
            )
            if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
                // the size is fixed
                width = widthSpecSize
                height = heightSpecSize
                // for compatibility, we adjust size based on aspect ratio
                if (videoWidth * height < width * videoHeight) {
                    width = height * videoWidth / videoHeight
                } else if (videoWidth * height > width * videoHeight) {
                    height = width * videoHeight / videoWidth
                }
            } else if (widthSpecMode == MeasureSpec.EXACTLY) {
                // only the width is fixed, adjust the height to match aspect ratio if possible
                width = widthSpecSize
                height = width * videoHeight / videoWidth
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // couldn't match aspect ratio within the constraints
                    height = heightSpecSize
                    width = height * videoWidth / videoHeight
                }
            } else if (heightSpecMode == MeasureSpec.EXACTLY) {
                // only the height is fixed, adjust the width to match aspect ratio if possible
                height = heightSpecSize
                width = height * videoWidth / videoHeight
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // couldn't match aspect ratio within the constraints
                    width = widthSpecSize
                    height = width * videoHeight / videoWidth
                }
            } else {
                // neither the width nor the height are fixed, try to use actual video size
                width = videoWidth
                height = videoHeight
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // too tall, decrease both width and height
                    height = heightSpecSize
                    width = height * videoWidth / videoHeight
                }
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // too wide, decrease both width and height
                    width = widthSpecSize
                    height = width * videoHeight / videoWidth
                }
            }
        } else {
            // no size yet, just adopt the given spec sizes
        }
        if (parentWidth != 0 && parentHeight != 0 && videoWidth != 0 && videoHeight != 0) {
            if (HWPlayerSettingOptions.VIDEO_IMAGE_DISPLAY_TYPE === HWPlayerSettingOptions.VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL) {
                /**原图 */
                height = videoHeight
                width = videoWidth
            } else if (HWPlayerSettingOptions.VIDEO_IMAGE_DISPLAY_TYPE === HWPlayerSettingOptions.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP) {
                if (viewRotation == 90 || viewRotation == 270) {
                    val tempSize = parentWidth
                    parentWidth = parentHeight
                    parentHeight = tempSize
                }
                /**充满剪切 */
                if (videoHeight.toDouble() / videoWidth > parentHeight.toDouble() / parentWidth) {
                    height =
                        (parentWidth.toDouble() / width.toDouble() * height.toDouble()).toInt()
                    width = parentWidth
                } else if (videoHeight.toDouble() / videoWidth < parentHeight.toDouble() / parentWidth) {
                    width =
                        (parentHeight.toDouble() / height.toDouble() * width.toDouble()).toInt()
                    height = parentHeight
                }
            } else if (HWPlayerSettingOptions.VIDEO_IMAGE_DISPLAY_TYPE === HWPlayerSettingOptions.VIDEO_16_9_TYPE_ADAPTER || HWPlayerSettingOptions.VIDEO_IMAGE_DISPLAY_TYPE === HWPlayerSettingOptions.VIDEO_4_3_TYPE_ADAPTER) {

                var displayAspectRatio = 0f
                if (HWPlayerSettingOptions.VIDEO_IMAGE_DISPLAY_TYPE === HWPlayerSettingOptions.VIDEO_16_9_TYPE_ADAPTER) {
                    displayAspectRatio = 16.0f / 9.0f
                    if (viewRotation == 90 || viewRotation == 270)
                        displayAspectRatio = 1.0f / displayAspectRatio
                } else {
                    displayAspectRatio = 4.0f / 3.0f
                    if (viewRotation == 90 || viewRotation == 270)
                        displayAspectRatio = 1.0f / displayAspectRatio
                }

                //容器view比例
                val specAspectRatio = parentWidth.toFloat() / parentHeight.toFloat()
                //容器比例和视频比例大小 (确定是哪一边抵触边缘用,true表示视频比容器胖 false表示表示比较瘦
                val shouldBeWider = displayAspectRatio > specAspectRatio
                if (shouldBeWider) {
                    // too wide, fix width
                    height = (parentWidth / displayAspectRatio).toInt()
                } else {
                    // too high, fix height
                    width = (parentHeight * displayAspectRatio).toInt()
                }
            }
        }

        setMeasuredDimension(width, height)
    }
}