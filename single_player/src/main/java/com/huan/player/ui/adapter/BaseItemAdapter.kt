package tv.huan.hwsystemsdk.player.adapter

import android.content.Context
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.huan.player.R
import com.huan.player.constant.Utils
import java.lang.reflect.Type

abstract class BaseItemAdapter<T> constructor(protected var title: String) {
    private var view: View? = null
    protected var context: Context? = null
    private var inAnimation: Animation? = null
    private var outAnimation: Animation? = null
    private var outAnimationAlpha: Animation? = null
    private var onSpreadListener: OnSpreadListener? = null
    protected var mPosition: Int = -1
    var selectPosition = -1

    protected abstract fun getLayoutId(): Int

    protected abstract fun onCreateView()

    protected abstract fun onBindItem(position: Int, data: T?)

    //标题设置透明度
    abstract fun onAlphaTitle(alpha:Float)

    //展开的view对象
    protected abstract fun getSpreadAnimView(): ViewGroup?

    /**
     * 展开动画监听
     */
    fun setOnSpreadListener(onSpreadListener: OnSpreadListener) {
        this.onSpreadListener = onSpreadListener
    }

    fun getDataType(): Type {
        return Utils.getClassType(this.javaClass)
    }

    /**
     * item 是否是展开状态
     */
    fun isViewSpread(): Boolean? {
        return getSpreadAnimView()?.isShown
    }

    /**
     * 查找是否，还有获焦View
     */
    fun findeFocusView(keyCode: Int?): View? {
        val code = if (keyCode == KeyEvent.KEYCODE_DPAD_UP)
            View.FOCUS_UP
        else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
            View.FOCUS_DOWN
        else
            return null
        return FocusFinder.getInstance()
            .findNextFocus(view as ViewGroup, view?.findFocus(), code)
    }

    /**
     * 重置转态
     */
    fun resetStatus() {
        onAlphaTitle(1f)
        val sView = getSpreadAnimView()
        sView?.visibility = View.GONE
        view?.visibility = View.VISIBLE
        selectPosition = -1
        inAnimation?.cancel()
        outAnimation?.cancel()
        outAnimationAlpha?.cancel()
    }

    open fun show(position: Int): Boolean {
        if (position != mPosition || Utils.null2False(view?.isShown)) return false
        view?.startAnimation(inAnimation)
        view?.visibility = View.VISIBLE
        return true
    }

    /**
     * 选中坐标以上的只有透明动画和站位
     */
    open fun hide(position: Int): Boolean {
        if (position != mPosition || !Utils.null2False(view?.isShown)) return false
        if (position > selectPosition) {
            view?.visibility = View.GONE
        } else {
            view?.startAnimation(outAnimationAlpha)
            view?.visibility = View.INVISIBLE
        }
        return true
    }

    /**
     * 折叠
     */
    open fun fold(position: Int): Boolean {
        if (position != mPosition) return false
        val animView = getSpreadAnimView()
        animView?.visibility = View.GONE
        return true
    }

    /**
     * 展开
     * @return 本次展开是否生效
     */
    open fun spread(position: Int): Boolean {
        val animView = getSpreadAnimView()
        if (position != mPosition || Utils.null2False(animView?.isShown)) return false
        selectPosition = position
        animView?.startAnimation(inAnimation)
        animView?.visibility = View.VISIBLE
        return true
    }

    fun create(context: Context, viewGroup: ViewGroup?) {
        this.context = context
        //进入动画
        inAnimation =
            AnimationUtils.loadAnimation(context, R.anim.show_in_bottom)
        //退出动画
       // outAnimation = AnimationUtils.loadAnimation(context, tv.huan.common.R.anim.hide_out_top)
        //退出透明动画
        outAnimationAlpha = AnimationUtils.loadAnimation(context, R.anim.alpha_out)
        view = LayoutInflater.from(context).inflate(getLayoutId(), viewGroup, false)
        viewGroup?.addView(view)
        onCreateView()
        initListener()
    }

    private fun initListener() {
        inAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                onSpreadListener?.onSpreadEnd(animation)
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
    }

    fun getView():View?{
        return view
    }

    fun <E> bindItem(position: Int, data: E?) {
        mPosition = position
        if (data == null) {
            onBindItem(position, null)
            return
        }
        try {
            onBindItem(position, data as T)
        } catch (e: Exception) {
            onBindItem(position, null)
        }
    }


    protected fun <T> getView(id: Int): T? {
        val view = this.view?.findViewById<View>(id)
        if (view == null) return null
        return view as T
    }

    open fun onDestroy() {
        inAnimation?.cancel()
        outAnimation?.cancel()
        outAnimationAlpha?.cancel()
        outAnimationAlpha = null
        outAnimation = null
        inAnimation = null
        onSpreadListener = null
        view = null
        context = null
    }

    interface OnSpreadListener {
        fun onSpreadEnd(animation: Animation?)
    }
}