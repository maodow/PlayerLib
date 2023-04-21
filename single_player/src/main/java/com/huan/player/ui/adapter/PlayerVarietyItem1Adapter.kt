package tv.huan.hwsystemsdk.player.adapter


import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.huan.player.R
import com.huan.player.bean.Media
import com.huan.player.constant.AlbumType
import com.huan.player.ui.PlayerDataHelp
import com.hw.recycler.contract.OnFocusItemListener
import com.hw.recycler.contract.OnInBorderKeyEventListener
import com.hw.recycler.widget.THRecyclerView

/**
 * 选集Item
 */
class PlayerVarietyItem1Adapter(title: String) : BaseItemAdapter<PlayerDataHelp>(title) {
    private var playerEpisodeParent: LinearLayout? = null
    private var titleName: TextView? = null
    private var titleIcon: ImageView? = null

    private var playerEpisodePointing: THRecyclerView? = null
    private var playerEpisode: THRecyclerView? = null
    private var playerFilmEpisode: THRecyclerView? = null
    private var playerVarietyEpisode: THRecyclerView? = null

    private var playerEpisodePointingAdapter: PlayerEpisodePointingAdapter? = null
    private var playerEpisodeAdapter: PlayerEpisodeAdapter? = null
    private var playerFilmEpisodeAdapter: PlayerFilmEpisodeAdapter? = null
    private var playerVarietyEpisodeAdapter: PlayerVarietyEpisodeAdapter? = null

    private var playerDataHelp: PlayerDataHelp? = null
    private var playType = -1

    private var next: (() -> Unit)? = null

    override fun getLayoutId(): Int = R.layout.sdk_player_variety_item1_adapter

    override fun onCreateView() {
        titleIcon = getView<ImageView>(R.id.icon_img1)
        titleName = getView<TextView>(R.id.icon_name1)
        playerEpisodeParent = getView<LinearLayout>(R.id.player_episode_parent)
        playerEpisodePointing = getView<THRecyclerView>(R.id.player_episode_pointing)
        playerEpisode = getView<THRecyclerView>(R.id.player_episode)
        playerFilmEpisode = getView<THRecyclerView>(R.id.player_film_episode)
        playerVarietyEpisode = getView<THRecyclerView>(R.id.player_variety_episode)

        titleName?.setText(title)

        initListener()
    }

    override fun onBindItem(position: Int, data: PlayerDataHelp?) {
        initAdapter(data)
    }


    private fun initAdapter(playerDataHelp: PlayerDataHelp?) {
        if (playerDataHelp == null) return
        this.playerDataHelp = playerDataHelp
        val type = playerDataHelp?.album?.type
        val medias = playerDataHelp?.medias
        if (type == AlbumType.FILM) {
            playType = playerDataHelp!!.album!!.playType
            playerEpisodePointing?.visibility = View.GONE
            playerEpisode?.visibility = View.GONE
            playerVarietyEpisode?.visibility = View.GONE
            initFilmEpisode(medias)
        } else if (type == AlbumType.VARIETY || type == AlbumType.GAME || type == AlbumType.MUSIC) {
            playType = playerDataHelp!!.columns!!.playType
            playerEpisodePointing?.visibility = View.GONE
            playerEpisode?.visibility = View.GONE
            playerFilmEpisode?.visibility = View.GONE
            initVarietyEpisode(medias)
        } else {
            playType = playerDataHelp!!.album!!.playType
            playerFilmEpisode?.visibility = View.GONE
            playerVarietyEpisode?.visibility = View.GONE
            initEpisodePointing(medias)
            initDetailEpisode(medias)
        }
    }

    //初始化电影集数的适配器
    private fun initFilmEpisode(medias: MutableList<Media>?) {
        if (playerFilmEpisodeAdapter == null) {
            playerFilmEpisodeAdapter =
                PlayerFilmEpisodeAdapter(context!!, playerDataHelp)
            playerFilmEpisode?.setAdapter(playerFilmEpisodeAdapter)
        }
        playerFilmEpisodeAdapter?.setDataBean(medias)
    }

    //初始化集数标签适配器
    private fun initEpisodePointing(medias: MutableList<Media>?) {
        if (medias == null || medias.size == 0) {
            playerEpisodePointing?.visibility = View.GONE
            playerEpisode?.visibility = View.GONE
            return
        }
        if (playerEpisodePointingAdapter == null) {
            playerEpisodePointingAdapter =
                PlayerEpisodePointingAdapter(context!!)
            playerEpisodePointing?.setAdapter(playerEpisodePointingAdapter)
        }
        playerEpisodePointingAdapter?.setDataBean(playerDataHelp?.episodePointingList)
    }

    //其他集数的适配器（电视剧等）
    private fun initDetailEpisode(medias: MutableList<Media>?) {
        if (playerEpisodeAdapter == null) {
            playerEpisodeAdapter =
                PlayerEpisodeAdapter(context!!, playType)
            playerEpisode?.setAdapter(playerEpisodeAdapter)
        }
        playerEpisodeAdapter?.setDataBean(medias)
    }

    //初始化综艺类型集数的适配器
    private fun initVarietyEpisode(medias: MutableList<Media>?) {
        if (playerVarietyEpisodeAdapter == null) {
            playerVarietyEpisodeAdapter =
                PlayerVarietyEpisodeAdapter(context!!, playType, playerDataHelp)
            playerVarietyEpisode?.setAdapter(playerVarietyEpisodeAdapter)
        }
        playerVarietyEpisodeAdapter?.setDataBean(medias)
    }


    private fun initListener() {
        playerEpisode?.setOnInBorderKeyEventListener(object : OnInBorderKeyEventListener {
            override fun onInBorderKeyEvent(direction: Int, focused: View?): Boolean {
                if (direction == View.FOCUS_UP) {
                    playerEpisodePointing?.requestFocus()
                    playerEpisodePointing?.setSelectedPosition(playerEpisodePointing!!.selectedPosition)
                    return true
                }
                return false
            }

        })

        playerEpisodePointing?.setOnFocusItemListener(object : OnFocusItemListener {
            override fun onItemClick(
                parent: View?,
                focusChild: View?,
                position: Int,
                itemType: Int
            ) {

            }

            override fun onItemPreSelected(
                parent: View?,
                focusChild: View?,
                position: Int,
                itemType: Int
            ) {
                playerEpisodePointingAdapter?.setSelecteStatus()
            }

            override fun onItemSelected(
                parent: View?,
                focusChild: View?,
                position: Int,
                itemType: Int
            ) {
                playerEpisodePointingAdapter?.selecteStatusReset()
                if (position != playerDataHelp?.currentPointingIndex)
                    setSelectedEpisode(position)
            }
        })


        playerEpisode?.setOnFocusItemListener(object : OnFocusItemListener {
            override fun onItemClick(
                parent: View?,
                focusChild: View?,
                position: Int,
                itemType: Int
            ) {
                if (next != null) {
                    next?.invoke()
                }
                playerDataHelp?.getNextEpisode(position)
                playerEpisodeAdapter?.notifyEpisode(position)
            }

            override fun onItemPreSelected(
                parent: View?,
                focusChild: View?,
                position: Int,
                itemType: Int
            ) {

            }

            override fun onItemSelected(
                parent: View?,
                focusChild: View?,
                position: Int,
                itemType: Int
            ) {
                if (playerDataHelp == null) return
                val pointingIndex = playerDataHelp!!.getPointingIndex(position)
                if (pointingIndex != playerDataHelp!!.currentPointingIndex) {
                    playerEpisodePointingAdapter?.selecteStatusReset()
                    playerEpisodePointing?.setItemActivated(pointingIndex)
                    playerEpisodePointingAdapter?.setSelecteView(
                        playerEpisodePointing?.getChildAt(
                            pointingIndex
                        )
                    )
                    playerDataHelp?.setCurrentPoinsettingIndex(pointingIndex)
                }
            }

        })

        playerFilmEpisode?.setOnFocusItemListener(object : OnFocusItemListener {
            override fun onItemClick(
                parent: View?,
                focusChild: View?,
                position: Int,
                itemType: Int
            ) {
                if (next != null) {
                    next?.invoke()
                }
                playerDataHelp?.getNextEpisode(position)
                playerFilmEpisodeAdapter?.notifyEpisode(position)
            }

            override fun onItemPreSelected(
                parent: View?,
                focusChild: View?,
                position: Int,
                itemType: Int
            ) {

            }

            override fun onItemSelected(
                parent: View?,
                focusChild: View?,
                position: Int,
                itemType: Int
            ) {
                focusChild?.setActivated(false)
            }

        })

        playerVarietyEpisode?.setOnFocusItemListener(object : OnFocusItemListener {
            override fun onItemClick(
                parent: View?,
                focusChild: View?,
                position: Int,
                itemType: Int
            ) {
                if (next != null) {
                    next?.invoke()
                }
                playerDataHelp?.getNextEpisode(position)
                playerVarietyEpisodeAdapter?.notifyEpisode(position)
            }

            override fun onItemPreSelected(
                parent: View?,
                focusChild: View?,
                position: Int,
                itemType: Int
            ) {

            }

            override fun onItemSelected(
                parent: View?,
                focusChild: View?,
                position: Int,
                itemType: Int
            ) {
                focusChild?.setActivated(false)
            }

        })
    }


    /**
     * 选中集数
     */
    private fun setSelectedEpisode(position: Int) {
        playerDataHelp?.getPagingStartEpisode(position)?.let {
            val pagingStartEpisode = it - 1
            playerEpisode?.setSelectedPosition(pagingStartEpisode, 30)
        }
    }


    /**
     * 当集数变换时，切换集数的状态
     */
    fun setDefaultEpisode(type: Int?) {
        val currentMediaIndex = playerDataHelp!!.currentMediaIndex
        if (type == AlbumType.FILM) {
            playerFilmEpisodeAdapter?.notifyEpisode(currentMediaIndex)
            playerFilmEpisode?.requestFocusSelectedPosition(currentMediaIndex)
        } else if (type == AlbumType.VARIETY || type == AlbumType.GAME || type == AlbumType.MUSIC) {
            playerVarietyEpisodeAdapter?.notifyEpisode(currentMediaIndex)
            playerVarietyEpisode?.requestFocusSelectedPosition(currentMediaIndex)
        } else {
            notifiyEpisodeStatus(playerDataHelp, playerDataHelp!!.currentMediaIndex)
        }
    }

    private fun notifiyEpisodeStatus(playerDataHelp: PlayerDataHelp?, currentMediaIndex: Int) {
        if (playerDataHelp == null) return
        val pointingIndex =
            playerDataHelp?.getPointingIndex(currentMediaIndex)
        playerEpisodePointingAdapter?.selecteStatusReset()
        playerEpisodePointing?.setItemActivated(pointingIndex)
        playerEpisodePointingAdapter?.setSelecteView(
            playerEpisodePointing?.getChildAt(
                pointingIndex
            )
        )
        playerDataHelp?.setCurrentPoinsettingIndex(pointingIndex)
        playerEpisodeAdapter?.notifyEpisode(currentMediaIndex)
        playerEpisode?.requestFocusSelectedPosition(currentMediaIndex)
    }


    fun setNextEpisodeListener(next: () -> Unit) {
        this.next = next
    }

    override fun onDestroy() {
        super.onDestroy()
        titleName?.removeCallbacks(showRun)
        playerEpisodePointing?.removeAllViews()
        playerEpisode?.removeAllViews()
        playerFilmEpisode?.removeAllViews()
        playerVarietyEpisode?.removeAllViews()
        showRun = null
        next = null
        playerDataHelp = null
    }

    override fun getSpreadAnimView(): ViewGroup? = playerEpisodeParent


    override fun spread(position: Int): Boolean {
        val spread = super.spread(position)
        if (spread)
            titleName?.postDelayed(showRun, 50)
        return spread
    }

    private var showRun: Runnable? = object : Runnable {
        override fun run() {
            setDefaultEpisode(playerDataHelp?.album?.type)
        }
    }

    override fun onAlphaTitle(alpha: Float) {
        titleIcon?.alpha = alpha
        titleName?.alpha = alpha
    }

}