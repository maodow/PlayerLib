package tv.huan.hwsystemsdk.player.presenter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.huan.player.constant.Utils
import com.huan.player.contract.DetainmentViewContract
import com.huan.player.ui.HandlerProgress
import tv.huan.hwsystemsdk.player.adapter.PlayerRecommendAdapter

class DetainmentViewPresenter : DetainmentViewContract {
    private var context: Context? = null
    private var viewGroup: ViewGroup? = null
    private var onButtonClick: ((isExit: Boolean) -> Unit)? = null
    private var onClick: (() -> Unit)? = null

   // private var disposable: Disposable? = null
   // private var detainmentRecycler: TvRecyclerView? = null
    private var playerRecommendAdapter: PlayerRecommendAdapter? = null

    private var page = 0
    private var size = 6
  //  private var recommendData: RecommendData? = null
    private var textCancel: TextView? = null
    private var textExit: TextView? = null


    override fun onCreate(context: Context, viewGroup: ViewGroup, handler: HandlerProgress?) {
        this.context = context
        this.viewGroup = viewGroup
//        val view = View.inflate(context, R.layout.sdk_detainment_view, viewGroup)
//        detainmentRecycler = view.findViewById<TvRecyclerView>(R.id.detainment_recycler)
//        textCancel = view.findViewById<TextView>(R.id.text_cancel)
//        textExit = view.findViewById<TextView>(R.id.text_exit)
        initListener()
    }

    private fun initListener() {
        textCancel?.setOnClickListener {
            if (onButtonClick != null) {
                onButtonClick?.invoke(false)
            }
            hide()
        }
        textExit?.setOnClickListener {
            if (onButtonClick != null) {
                onButtonClick?.invoke(true)
            }
            hide()
        }
        textCancel?.setOnFocusChangeListener { v, hasFocus ->
           // FocusBorderHelp.getFocusView(context, v, hasFocus, 0f, 0f)
        }
        textExit?.setOnFocusChangeListener { v, hasFocus ->
           // FocusBorderHelp.getFocusView(context, v, hasFocus, 0f, 0f)

        }
//        detainmentRecycler?.setOnItemListener(object : TvRecyclerView.OnItemListener {
//            override fun onItemSelected(parent: TvRecyclerView?, itemView: View?, position: Int) {
//               // FocusBorderHelp.getFocusView(context, itemView, true, 0f, 0f)
//            }
//
//            override fun onItemPreSelected(
//                parent: TvRecyclerView?,
//                itemView: View?,
//                position: Int
//            ) {
//                //FocusBorderHelp.getFocusView(context, itemView, false, 0f, 0f)
//            }
//
//            override fun onItemClick(parent: TvRecyclerView?, itemView: View?, position: Int) {
//                if (onClick != null) {
//                    onClick?.invoke()
//                }
//            }
//
//        })
    }

    private fun loadingData(classId: String) {
//        if (recommendData != null && recommendData!!.total / size > page) {
//            page++
//        } else {
//            page = 1
//        }

//        val flowble = RetrofitHelp.INSTANCE
//            .getDefaultInterface()
//            ?.getRecommendData(classId, page, size)
//        disposable = RetrofitHelp.INSTANCE.addSubscription(flowble,
//            object : OnResponseListenImpl<ResponseBean<RecommendData>>() {
//                override fun onNoxt(accept: ResponseBean<RecommendData>) {
//                    recommendData = accept.data
//                    initAdapter()
//                }
//
//                override fun onError(code: Int, msg: String?) {
//
//                }
//
//                override fun onComplete() {
//
//                }
//            })
    }

    private fun initAdapter() {
//        if (recommendData == null) return
//        if (playerRecommendAdapter == null) {
//            playerRecommendAdapter = PlayerRecommendAdapter(context!!)
//            detainmentRecycler?.setAdapter(playerRecommendAdapter)
//        }
//        playerRecommendAdapter?.setDataBean(recommendData?.albums)
    }

    override fun setButtonListener(onButtonClick: (isExit: Boolean) -> Unit) {
        this.onButtonClick = onButtonClick
    }

    /**
     * 点击推荐时，上播放记录
     */
    override fun setPlayerReport(onClick: () -> Unit) {
        this.onClick = onClick
    }


    override fun hide(tag: Int?) {
        viewGroup?.visibility = View.GONE
//        if (disposable != null)
//            RetrofitHelp.INSTANCE.remove(disposable)
    }

    override fun show(tag: String?) {
        viewGroup?.visibility = View.VISIBLE
        textExit?.setFocusable(true)
        textExit?.requestFocusFromTouch()
        loadingData(Utils.null2Length0(tag))
    }

    override fun onDestroy() {
//        if (disposable != null)
//            RetrofitHelp.INSTANCE.remove(disposable)
        viewGroup?.removeAllViews()
        onClick = null
        onButtonClick = null
        viewGroup = null
        context = null
 //       disposable =null
    }

    override fun isShown(): Boolean {
        return viewGroup?.isShown ?: false
    }
}