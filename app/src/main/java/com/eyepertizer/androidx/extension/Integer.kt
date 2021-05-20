package com.eyepertizer.androidx.extension

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
