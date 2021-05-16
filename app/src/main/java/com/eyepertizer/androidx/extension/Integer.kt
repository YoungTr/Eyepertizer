package com.eyepertizer.androidx.extension

import com.eyepertizer.androidx.EyepertizerApplication

/**
 * @author youngtr
 * @data 2021/5/16
 */

fun Int.getString(): String {
    return EyepertizerApplication.context.getString(this)
}