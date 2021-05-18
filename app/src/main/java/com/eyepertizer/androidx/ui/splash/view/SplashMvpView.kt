package com.eyepertizer.androidx.ui.splash.view

import com.eyepertizer.androidx.base.MvpView

/**
 * @author youngtr
 * @data 2021/5/16
 */
interface SplashMvpView : MvpView {

    fun setupView()

    fun openMainActivity()
}