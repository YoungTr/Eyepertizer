package com.eyepertizer.androidx.ui.splash.presenter

import com.eyepertizer.androidx.base.MvpPresenter
import com.eyepertizer.androidx.ui.splash.view.SplashMvpView
import com.permissionx.guolindev.request.PermissionBuilder

/**
 * @author youngtr
 * @data 2021/5/16
 */
interface SplashMvpPresenter<V : SplashMvpView> : MvpPresenter<V> {

    fun requestWriteExternalStorePermission(permissionBuilder: PermissionBuilder)

    fun requestReadPhoneStatePermission(permissionBuilder: PermissionBuilder)


}