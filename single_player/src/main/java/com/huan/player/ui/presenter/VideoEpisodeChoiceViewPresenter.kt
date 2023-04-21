package tv.huan.hwsystemsdk.player.presenter

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import com.huan.player.R
import com.huan.player.bean.NetVideoInfo
import com.huan.player.constant.Utils
import com.huan.player.contract.PlayerViewContract
import com.huan.player.contract.VideoEpisodeChoiceViewContract
import com.huan.player.ui.HandlerProgress
import com.huan.player.ui.PlayerDataHelp
import tv.huan.hwsystemsdk.player.adapter.*


class VideoEpisodeChoiceViewPresenter : VideoEpisodeChoiceViewContract,
    BaseItemAdapter.OnSpreadListener {
    private var viewGroup: ViewGroup? = null
    private var context: Context? = null


    private var playerDataHelp: PlayerDataHelp? = null

    private var Item1Adapter: PlayerVarietyItem1Adapter? = null
    private var Item0Adapter: PlayerVarietyItem0Adapter? = null

    private var parentViewAnim: LayoutAnimationController? = null

    private var defSelectorPosition = 1
    private var selectorPosition = defSelectorPosition
    private var listAdapter: MutableList<BaseItemAdapter<*>>? = mutableListOf()
    private var isAnimEnd = true
    private var showStatus = 0 //显示状态
    private var listCount = 0
    private var mediaPlayer: PlayerViewContract? = null

    override fun onCreate(context: Context, viewGroup: ViewGroup, handler: HandlerProgress?) {
        this.viewGroup = viewGroup
        this.context = context


        viewGroup?.background = Utils.getDrawable(R.drawable.bg_shadow3)
        parentViewAnim =
            AnimationUtils.loadLayoutAnimation(context, R.anim.list_layout_anim_top)

        Item0Adapter = PlayerVarietyItem0Adapter("更多功能")
        Item1Adapter = PlayerVarietyItem1Adapter("正片")

        listAdapter?.add(Item0Adapter!!)
        listAdapter?.add(Item1Adapter!!)
        listAdapter?.add(PlayerVarietyItem2Adapter("画面比例"))


        for (item in listAdapter!!) {
            item?.create(context, viewGroup)
            item?.setOnSpreadListener(this)
        }

        initListener()
    }

    override fun setData(playerDataHelp: PlayerDataHelp?, mediaPlayer: PlayerViewContract?) {
        if (playerDataHelp == null || this.playerDataHelp != null) return
        this.playerDataHelp = playerDataHelp
        this.mediaPlayer = mediaPlayer

        if (!playerDataHelp.isMoreFunctions && listAdapter?.size == 3) {
            Item0Adapter?.onDestroy()
            viewGroup?.removeViewAt(0)
            listAdapter?.removeAt(0)
            defSelectorPosition = 0
            selectorPosition = 0
        }

        for (i in 0 until Utils.nullToInt(listAdapter?.size)) {
            val adapter = listAdapter?.get(i)
            val dataType = adapter?.getDataType()
            if (dataType == PlayerDataHelp::class.java)
                adapter?.bindItem(i, playerDataHelp)
            else
                adapter?.bindItem(i, mediaPlayer)
        }
    }

    override fun onVideoPrepared(playerView: PlayerViewContract?, playerDataHelp: PlayerDataHelp?) {

    }

    override fun setDefnInfo(videoInfo: NetVideoInfo?) {

    }

    override fun setPlayerReport(onClick: () -> Unit) {
        Item0Adapter?.setPlayerReport(onClick)
    }

    /**
     * 集数更新监听
     */
    override fun onUpdateEpisodeListener() {
        if (isShown() && !Utils.null2False(Item1Adapter?.isViewSpread()))
            Item1Adapter?.setDefaultEpisode(playerDataHelp?.album?.type)
    }

    override fun setNextEpisodeListener(next: () -> Unit) {
        Item0Adapter?.setNextEpisodeListener(next)
        Item1Adapter?.setNextEpisodeListener(next)
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN && isAnimEnd) {
            val findeFocusView = listAdapter?.get(selectorPosition)?.findeFocusView(event?.keyCode)
            if (findeFocusView == null)
                spreadItem(selectorPosition, event?.keyCode)
        }
        return !isAnimEnd
    }

    override fun hide(tag: Int?) {
        viewGroup?.visibility = View.GONE
        showStatus = 0
        listCount = 0
        isAnimEnd = true
        selectorPosition = defSelectorPosition
        parentViewAnim?.animation?.cancel()
        for (item in listAdapter!!) {
            item.resetStatus()
        }
    }

    override fun show(tag: String?) {
        if (isShown() || playerDataHelp == null) {
            //快速打开列表
            if (showStatus == 1 && !isAnimEnd && playerDataHelp != null
            ) {
                showStatus = 3
                parentViewAnim?.animation?.cancel()
                viewGroup?.visibility = View.VISIBLE
                spreadDefItem()
            }
            return
        }
        showStatus = 1
        isAnimEnd = false
        viewGroup?.setLayoutAnimation(parentViewAnim)
        viewGroup?.visibility = View.VISIBLE
    }

    /**
     * 展开指定的item
     */
    private fun spreadItem(position: Int, keyCode: Int?) {
        val select = if (keyCode == KeyEvent.KEYCODE_DPAD_UP)
            position - 1
        else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
            position + 1
        else
            return
        if (select < 0 || select >= Utils.nullToInt(listAdapter?.size))
            return
        isAnimEnd = false
        val firstPosition = getFirstPosition(select)
        val lastPosition = getLastPosition(select)
        var spreadA: BaseItemAdapter<*>? = null
        for (i in 0 until Utils.nullToInt(listAdapter?.size)) {
            val adapter = listAdapter?.get(i)
            adapter?.selectPosition = select
            if (i == select) {
                spreadA = adapter
                continue
            }

            if (i != firstPosition && i != lastPosition) {
                adapter?.hide(i)
            } else {
                if (selectorPosition == i) {
                    adapter?.onAlphaTitle(0.7f)
                    adapter?.fold(i)
                } else {
                    adapter?.show(i)
                }
            }
        }
        selectorPosition = select
        spreadA?.onAlphaTitle(1f)
        spreadA?.spread(select)
    }

    /**
     * 展开默认的item
     */
    private fun spreadDefItem() {
        val firstPosition = getFirstPosition(defSelectorPosition)
        val lastPosition = getLastPosition(defSelectorPosition)
        var spreadA: BaseItemAdapter<*>? = null
        for (i in 0 until Utils.nullToInt(listAdapter?.size)) {
            val adapter = listAdapter?.get(i)
            adapter?.selectPosition = defSelectorPosition
            if (i == defSelectorPosition) {
                spreadA = adapter
                continue
            }
            adapter?.onAlphaTitle(0.7f)
            if (i != firstPosition && i != lastPosition) {
                adapter?.hide(i)
            }
        }
        spreadA?.spread(defSelectorPosition)
    }

    private fun getFirstPosition(select: Int): Int? {
        val f = select - 1
        if (f < 0) return null
        return f
    }

    private fun getLastPosition(select: Int): Int? {
        val l = select + 1
        if (l == listAdapter?.size) return null
        return l
    }

    private fun initListener() {
        parentViewAnim?.animation?.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {

            }

            //每个item 动画结束都会调用一次
            override fun onAnimationEnd(animation: Animation?) {
                listCount++
                if (listCount == Utils.nullToInt(listAdapter?.size) && showStatus == 1) {
                    showStatus = 4
                    spreadDefItem()
                    listCount = 0
                }

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
    }


    override fun onDestroy() {
        viewGroup?.removeAllViews()
        parentViewAnim?.animation?.cancel()
        parentViewAnim?.animation?.setAnimationListener(null)
        listAdapter?.clear()
        listAdapter = null
        playerDataHelp = null
        viewGroup = null
        context = null
        parentViewAnim = null
        mediaPlayer = null
    }

    override fun isShown(): Boolean {
        return viewGroup?.isShown ?: false
    }


    /**
     * 展开动画结束
     */
    override fun onSpreadEnd(animation: Animation?) {
        isAnimEnd = true
    }

}