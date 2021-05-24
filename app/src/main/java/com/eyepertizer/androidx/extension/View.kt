package com.eyepertizer.androidx.extension

import android.view.View
import android.view.animation.AlphaAnimation

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

/**
 * 隐藏 view
 */
fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

/**
 * 显示view，带有渐显动画效果。
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.visibleAlphaAnimation(duration: Long = 500L) {
    this?.visibility = View.VISIBLE
    this?.startAnimation(AlphaAnimation(0f, 1f).apply {
        this.duration = duration
        fillAfter = true
    })
}

/**
 * 占位隐藏view，带有渐隐动画效果。
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.invisibleAlphaAnimation(duration: Long = 500L) {
    this?.visibility = View.INVISIBLE
    this?.startAnimation(AlphaAnimation(1f, 0f).apply {
        this.duration = duration
        fillAfter = true
    })
}

/**
 * 隐藏view，带有渐隐动画效果。
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.goneAlphaAnimation(duration: Long = 500L) {
    this?.visibility = View.GONE
    this?.startAnimation(AlphaAnimation(1f, 0f).apply {
        this.duration = duration
        fillAfter = true
    })
}
