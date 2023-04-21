package tv.huan.hwsystemsdk.player.preview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.huan.player.constant.Logcat
import com.huan.player.constant.Utils
import com.huan.player.ui.preview.PreviewData
import com.huan.player.ui.preview.PreviewDrawable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tv.huan.hwplayer.config.HWPlayerSettingOptions
import tv.huan.hwsystemsdk.player.adapter.PlayerPreviewAdapter
import java.io.ByteArrayOutputStream

/**
 * description:
 *
 * @author
 * @date 2020/1/5
 */
class PreviewImageMng  constructor() {
    private val TAG = "HWPlayer"
    private var mPreviewData: PreviewData? = null
    private var mLnk: String? = null
    private var mPlInfo: String = ""
    private var mPlType: Int = 0
    private var mTotalItems = 0
    private var mTotalPages = 0
    private var mDrawableNumInOnePage = 0
    private val rect: Rect
    private var oldPageId: Int = -1
    private var bitmapCache = mutableMapOf<Int, Bitmap?>()
    private var pageIdCache = mutableListOf<Int>()
    private var imgUrlCache = mutableMapOf<Int, String?>()
   // private var retrofitInterface: RequestApi? = null
    private var launch: Job? = null
    var adapter: PlayerPreviewAdapter? = null
    var keyStatus: Int = 0
    var recycler: RecyclerView? = null
    private var notifySucceed = true

    fun clearData() {
        notifySucceed = true
        mTotalItems = 0
        mTotalPages = 0
        mDrawableNumInOnePage = 0
        oldPageId = -1
        launch?.cancel()
        bitmapCache?.iterator()?.forEach {
            it.value?.recycle()
        }
        bitmapCache?.clear()
        pageIdCache?.clear()
        imgUrlCache?.clear()
    }

    fun onDestroy() {
        clearData()
        recycler = null
        adapter = null
        mPreviewData = null
        //retrofitInterface = null
        launch = null
    }

    fun initData(previewData: PreviewData?, num: Int, lnk: String?, plInfo: String?, plType: Int?) {
        mLnk = lnk
        mPlType = Utils.nullToInt(plType)
        mPlInfo = Utils.null2Length0(plInfo)
        mTotalItems = num
        mPreviewData = previewData
        mDrawableNumInOnePage = mPreviewData!!.imageTotal
        mTotalPages = mTotalItems / mDrawableNumInOnePage
        if (mTotalItems % mDrawableNumInOnePage > 0) {
            mTotalPages = mTotalPages + 1
        }
        Logcat.iTag("mTotalPages ===" + mTotalPages)
    }


    private fun getPageIdByIndex(index: Int, numInOnePage: Int): Int {
        return if (numInOnePage == 0) 0 else index / numInOnePage
    }

    /**
     * 获取item在一张大图的位置
     *
     * @param index item下标
     */
    private fun getRectByIndex(index: Int, previewData: PreviewData?): Rect {
        val offset = index % previewData!!.imageTotal
        val row = offset / previewData.column
        val column = offset % previewData.column
        val left = column * previewData.width
        val top = row * previewData.height
        rect.set(left, top, left + previewData.width, top + previewData.height)
        return rect
    }

    /**
     * 拼接图片地址
     */
    private fun getPreviewPageDownloadUrl(
        pageId: Int,
        previewData: PreviewData?,
        lnk: String?
    ): String {
        return previewData?.url + lnk + "." + previewData?.fileName + "." + (pageId + 1) + ".jpg/0"
    }


    /**
     * 获取缓存图片
     */
    private fun getBitmapPage(pageId: Int): Bitmap? {
        return bitmapCache?.get(pageId)
    }


    /**
     * 获取缓存的Drawable
     */
    private fun getPreviewDrawable(index: Int): PreviewDrawable? {
        //return drawableCache?.get(index)
        return null
    }

    /**
     * 获取item的预览图
     *
     * @param index item 下标
     */
    fun getPreviewDrawableByIdx(index: Int): Drawable? {

//        val pageId = getPageIdByIndex(index, mDrawableNumInOnePage)
//        val imagePage = getBitmapPage(pageId)
//        if (imagePage == null) {
//            loadPreviewImagePage(pageId)
//            return ResourceUtil.getDrawable(R.drawable.load_def_m1)
//        }
//        LogUtils.iTag(TAG,"预览图Bitmap>>>" + imagePage.isRecycled)
//        //按需裁剪预览图
//        val rect = getRectByIndex(index, mPreviewData)
//        var previewDrawable = PreviewDrawable(imagePage, rect)
//        //savePreviewImageDrawable(previewDrawable, index)
//        LogUtils.iTag(TAG,"裁剪图>>>$previewDrawable")
//        return previewDrawable
        return null
    }


    private fun loadPreviewImagePage(pageId: Int) {
        if (oldPageId == pageId) return
        Logcat.iTag("预览图开始下载>>>")
        launch?.let {
            it.cancel()
        }

        val url = getPreviewPageDownloadUrl(pageId, mPreviewData, mLnk)

        launch = GlobalScope.launch(CoroutineExceptionHandler { ctx, e ->
            oldPageId = -1
            Logcat.eTag("图片下载异常>>> ${e.localizedMessage}")
        }) {
            //外网环境 !AppInfoManage.INSTANCE.isIntranet()
            if (!HWPlayerSettingOptions.INSTANCE.intranet) {
                loadBitmap(pageId, url)
                return@launch
            }
            //内网环境
            val imgUrl = imgUrlCache?.get(pageId)
            if (!TextUtils.isEmpty(imgUrl)) {
                loadBitmap(pageId, imgUrl)
                return@launch
            }
//            val responseBean = retrofitInterface?.downloadPict(mLnk, url, mPlInfo, mPlType)
//            if (responseBean != null && responseBean?.code == 200 && responseBean?.data != null
//            ) {
//                Logcat.iTag( "预览图获取内网地址成功>>>>${responseBean?.data?.pictUrl}")
//                imgUrlCache?.set(pageId, responseBean?.data?.pictUrl)
//                loadBitmap(pageId, responseBean?.data?.pictUrl)
//            } else {
//                oldPageId = -1
//            }
        }
        oldPageId = pageId
    }

    private suspend fun loadBitmap(pageId: Int, url: String?) {
        if (TextUtils.isEmpty(url)) {
            oldPageId = -1
            return
        }
        val bitmap = loadB(url)
        savePreviewImagePage(pageId, bitmap)
    }

    /**
     * 下载图片
     */
    private suspend fun loadB(url: String?): Bitmap? {
//        val download = retrofitInterface?.downloadImg(url)
//        if (download == null)
//            oldPageId = -1
//        download?.byteStream()?.use { read ->
//            ByteArrayOutputStream().use { write ->
//                var len: Int
//                val buffer = ByteArray(1024)
//                while (true) {
//                    len = read.read(buffer)
//                    if (len == -1) break
//                    write.write(buffer, 0, len)
//                }
//                val bu = write.toByteArray()
//                return BitmapFactory.decodeByteArray(bu, 0, bu.size)
//            }
//        }
        return null
    }


    /**
     * 只缓存两张大预览图
     */
    private fun savePreviewImagePage(pageId: Int, bitmap: Bitmap?) {
        if (bitmap == null) return
        val size = Utils.nullToInt(bitmapCache?.size)
        val last = if (pageIdCache?.isEmpty()) 0 else pageIdCache?.last()
        if (size < 2) {
            pageIdCache?.add(pageId)
            bitmapCache?.set(pageId, bitmap)
        } else if (pageId > last && size == 2) {
            val first = pageIdCache?.first()
            bitmapCache?.get(first)?.recycle()
            bitmapCache?.remove(first)
            pageIdCache?.removeFirst()

            pageIdCache?.add(pageId)
            bitmapCache?.set(pageId, bitmap)
        } else if (pageId < last && size == 2) {
            bitmapCache?.get(last)?.recycle()
            bitmapCache?.remove(last)
            pageIdCache?.removeLast()

            pageIdCache?.add(0, pageId)
            bitmapCache?.set(pageId, bitmap)
        }
        notifySucceed = false
        //更新drawable
        var count = 0
        for (i in 0 until mDrawableNumInOnePage) {
            val index = i + pageId * mDrawableNumInOnePage
            if (index >= mTotalItems) {
                continue
            }
            if (keyStatus == 0 && Utils.nullToInt(recycler?.scrollState) == 0) {
                count++
                adapter?.notifyItemChanged(index)
            }
        }
        //更新成功
        if (count == mDrawableNumInOnePage)
            notifySucceed = true

        Logcat.iTag("预览图更新是否成功:>>>>>>$notifySucceed")
    }

    /**
     * 更新item失败，刷新item
     */
    fun notifyFailingRefresh() {
        if (notifySucceed || bitmapCache.isEmpty()) return
        Logcat.iTag("预览图更新失败，刷新了！！！")
        var count = 0
        for (i in 0 until mDrawableNumInOnePage) {
            val index = i + oldPageId * mDrawableNumInOnePage
            if (index >= mTotalItems) {
                continue
            }
            if (keyStatus == 0 && Utils.nullToInt(recycler?.scrollState) == 0) {
                count++
                adapter?.notifyItemChanged(index)
            }
        }
        //更新成功
        if (count == mDrawableNumInOnePage)
            notifySucceed = true
        Logcat.iTag( "预览图刷新是否成功:>>>>>>$notifySucceed")
    }

    /**
     * 保存Item 的预览图, 两张大图的数量
     * @param index item 下标
     */
    private fun savePreviewImageDrawable(drawable: PreviewDrawable, index: Int) {

    }

    init {
        rect = Rect()
       // retrofitInterface = RetrofitHelp.INSTANCE.getInterface(RequestApi::class.java)
    }

//    interface RequestApi {
//
//        /**
//         * 外网转内网图片地址
//         */
//        @GET(RetrofitPath.PLAYER_DOWNLOAD_PICT)
//        suspend fun downloadPict(
//            @Query("vid") vid: String?,
//            @Query("pictUrl") pictUrl: String?,
//            @Query("pictInfo") plInfo: String?,
//            @Query("pictType") plType: Int?
//        ): ResponseBean<PicInfo?>?
//
//        @Streaming
//        @GET
//        suspend fun downloadImg(@Url url: String?): ResponseBody?
//
//
//        data class PicInfo(
//            var pictUrl: String?,
//            var pictInfo: String?,
//            var pictType: Int?
//        )
//    }
}