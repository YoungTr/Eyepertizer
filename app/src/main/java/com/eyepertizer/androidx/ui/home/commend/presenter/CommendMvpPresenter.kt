package com.eyepertizer.androidx.ui.home.commend.presenter

import com.eyepertizer.androidx.base.presenter.MvpPresenter
import com.eyepertizer.androidx.ui.home.commend.CommendMvpView

/**
 * @author youngtr
 * @data 2021/5/22
 */
interface CommendMvpPresenter<V : CommendMvpView> : MvpPresenter<V> {

    fun getHomePageRecommend()
}