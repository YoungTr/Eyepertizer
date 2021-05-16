package com.eyepertizer.androidx.ui.splash.presenter

import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.BaseMvpPresenter
import com.eyepertizer.androidx.extension.getString
import com.eyepertizer.androidx.ui.splash.view.SplashMvpView
import com.permissionx.guolindev.request.PermissionBuilder

/**
 * @author youngtr
 * @data 2021/5/16
 */
class SplashPresenter<V : SplashMvpView> : BaseMvpPresenter<V>(), SplashMvpPresenter<V> {


    override fun requestWriteExternalStorePermission(permissionBuilder: PermissionBuilder) {
        permissionBuilder.onExplainRequestReason { scope, deniedList ->
            val message = R.string.request_permission_access_phone_info.getString()
            scope.showRequestReasonDialog(deniedList,
                message,
                R.string.ok.getString(),
                R.string.cancel.getString())
        }.onForwardToSettings { scop, deniedList ->
            val message = R.string.request_permission_access_phone_info.getString()
            scop.showForwardToSettingsDialog(deniedList,
                message,
                R.string.settings.getString(),
                R.string.cancel.getString())
        }.request { allGranted, grantedList, deniedList ->
            getMvpView()?.let {
                it.setView()
                it.showLoading()
            }
        }
    }

    override fun requestReadPhoneStatePermission(permissionBuilder: PermissionBuilder) {
        TODO("Not yet implemented")
    }
}