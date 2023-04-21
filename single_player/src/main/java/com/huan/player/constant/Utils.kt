package com.huan.player.constant

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.TypedValue
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

object Utils {
    private const val TYPE_ADD = 0x00 // 加法

    private const val TYPE_MULTIPLY = 0x01 // 乘法

    private const val TYPE_DIVIDE = 0x02 // 除法

    private const val TYPE_SUBTRACT = 0x03 // 减法

    private var sToast: Toast? = null
    private var application: Context? = null

    fun setContext(application: Context) {
        this.application = application
    }

    fun getApp(): Context? {
        return application
    }

    fun nullToInt(value: Int?): Int {
        return value ?: 0
    }

    fun null2False(value: Boolean?): Boolean {
        return value ?: false
    }

    fun nullToLong(value: Long?): Long {
        return value ?: 0L
    }

    fun null2Double(value: Double?): Double {
        return value ?: 0.0
    }

    fun nullToLong(str: String?): Long {
        if (TextUtils.isEmpty(str)) return 0
        return if (str!!.matches("^(^-?\\d+$)$".toRegex())) {
            str.toLong()
        } else 0
    }

    fun null2Length0(value: String?): String {
        return value ?: ""
    }


    fun <E> getClassType(eClass: Class<E>): Type {
        val genericSuperclass = eClass.genericSuperclass as ParameterizedType
        return genericSuperclass.actualTypeArguments[0]
    }

    fun getDrawable(@DrawableRes id: Int): Drawable? {
        if (application == null) return null
        return ContextCompat.getDrawable(application!!, id)
    }


    fun toast(text: CharSequence) {
        if (sToast == null) {
            sToast = Toast.makeText(application, text, Toast.LENGTH_SHORT)
        } else {
            sToast?.duration = Toast.LENGTH_SHORT
            sToast?.setText(text)
        }
        sToast?.show()
    }

    fun scanForActivity(context: Context?): Activity? {
        if (context == null) return null
        if (context is Activity) {
            return context
        } else if (context is ContextWrapper) {
            return scanForActivity(
                context.baseContext
            )
        }
        return null
    }


    fun dp2px(dpValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpValue,
            application?.resources?.displayMetrics
        ).toInt()
    }

    /**
     * sp转换成px
     */
    fun sp2px(spValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spValue,
            application?.resources?.displayMetrics
        ).toInt()
    }


    fun getColor(@ColorRes id: Int): Int {
        if (application == null) return 0
        return ContextCompat.getColor(application!!, id)
    }

    /**
     * 获取屏幕宽度
     *
     * @param context Context
     * @return 屏幕宽度（px）
     */
    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.defaultDisplay.getRealSize(point)
        } else {
            wm.defaultDisplay.getSize(point)
        }
        return point.x
    }

    /**
     * 获取屏幕高度
     *
     * @param context Context
     * @return 屏幕高度（px）
     */
    fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.defaultDisplay.getRealSize(point)
        } else {
            wm.defaultDisplay.getSize(point)
        }
        return point.y
    }

    /**
     * 除法 a/b
     * @param a
     * @param b
     * @return
     */
    fun divide(a: Double, b: Double): Double {
        if (b == 0.0) return 0.0
        return calc(a, b, -1, TYPE_DIVIDE, null)
    }

    /**
     * 计算
     * @param a
     * @param b
     * @param scale
     * @param type
     * @param mode
     * @return
     */
    private fun calc(
        a: Double,
        b: Double,
        scale: Int,
        type: Int,
        mode: RoundingMode?
    ): Double {
        var result: BigDecimal? = null
        val bgA = BigDecimal(a.toString())
        val bgB = BigDecimal(b.toString())
        when (type) {
            TYPE_ADD -> result = bgA.add(bgB)
            TYPE_MULTIPLY -> result = bgA.multiply(bgB)
            TYPE_DIVIDE -> result = try {
                bgA.divide(bgB)
            } catch (e: ArithmeticException) { // 防止无限循环而报错  采用四舍五入保留3位有效数字
                bgA.divide(bgB, 3, RoundingMode.HALF_DOWN)
            }
            TYPE_SUBTRACT -> result = bgA.subtract(bgB)
        }
        if (mode == null) {
            if (scale != -1) {
                result = result?.setScale(scale)
            }
        } else {
            if (scale != -1) {
                result = result?.setScale(scale, mode)
            }
        }
        return null2Double(result?.toDouble())
    }

    /**
     * 播放器时间格式00:00:00
     * @param timeMs 单位毫秒
     */
    fun toPlayFormatString(timeMs: Long): String? {
        var timeMs = timeMs
        timeMs = nullToLong(timeMs)
        val totalSeconds = timeMs / 1000
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
//        if (hours > 0) {
//            return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
//        } else {
//            return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
//        }
    }

    fun setAttribute(objClass: Class<*>?, instance: Any, attrName: String?, value: Any?) {
        try {
            val declaredField = instance.javaClass.getDeclaredField(attrName)
            declaredField.isAccessible = true
            declaredField[instance] = value
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}