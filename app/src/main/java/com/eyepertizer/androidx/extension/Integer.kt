package com.eyepertizer.androidx.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.eyepertizer.androidx.EyepertizerApplication

/**
 * @author youngtr
 * @data 2021/5/16
 */

fun Int.getString(): String {
    return EyepertizerApplication.context.getString(this)
}

/**
 * 弹出Toast提示。
 *
 * @param duration 显示消息的时间  Either {@link #LENGTH_SHORT} or {@link #LENGTH_LONG}
 */
fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(EyepertizerApplication.context, this, duration).show()
}

/**
 * 解析xml布局
 *
 * @param parent 父布局
 * @param attachToRoot 是否依附到父布局
 */
fun Int.inflate(parent: ViewGroup, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(parent.context).inflate(this, parent, attachToRoot)
}

/**
 * 解析xml布局
 *
 * @param parent 父布局
 * @param attachToRoot 是否依附到父布局
 */
fun Int.inflate(inflater: LayoutInflater, parent: ViewGroup, attachToRoot: Boolean = false): View {
    return inflater.inflate(this, parent, attachToRoot)
}

