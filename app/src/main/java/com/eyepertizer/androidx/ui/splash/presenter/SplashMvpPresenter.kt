package com.eyepertizer.androidx.ui.splash.presenter

import com.eyepertizer.androidx.base.presenter.MvpPresenter
import com.eyepertizer.androidx.ui.splash.view.SplashMvpView
import com.permissionx.guolindev.PermissionCollection

/**
 * @author youngtr
 * @data 2021/5/16
 */
interface SplashMvpPresenter<V : SplashMvpView> : MvpPresenter<V> {

    fun requestPermission(permissionCollection: PermissionCollection)

    fun openMainActivity()

}