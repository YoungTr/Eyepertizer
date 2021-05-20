package com.eyepertizer.androidx.util

import android.view.View
import com.eyepertizer.androidx.EyepertizerApplication

object GlobalUtil {
    /**
     * 批量设置控件点击事件。
     *
     * @param v 点击的控件
     * @param block 处理点击事件回调代码块
     */
    fun setOnClickListener(vararg v: View?, block: View.() -> Unit) {
        val listener = View.OnClickListener { it.block() }
        v.forEach { it?.setOnClickListener(listener) }
    }

    /**
     * 批量设置控件点击事件。
     *
     * @param v 点击的控件
     * @param listener 处理点击事件监听器
     */
    fun setOnClickListener(vararg v: View?, listener: View.OnClickListener) {
        v.forEach { it?.setOnClickListener(listener) }
    }

    /**
     * 获取当前应用名称
     */
    val appName: String
        get() = EyepertizerApplication.context
            .getString(EyepertizerApplication.context.applicationInfo.labelRes)

}