package com.eyepertizer.androidx.base

/**
 * View 基类
 *
 * @author youngtr
 * @data 2021/5/14
 */
interface MvpView {

    fun showLoading()

    fun showToast(message: String?)
}