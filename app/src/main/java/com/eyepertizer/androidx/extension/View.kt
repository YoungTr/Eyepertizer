package com.eyepertizer.androidx.extension

import android.view.View

/**
 * 显示 view
 */
fun View?.visible() {
    this?.visibility = View.VISIBLE
}

/**
 * 隐藏 view
 */
fun View?.gone() {
    this?.visibility = View.GONE
}