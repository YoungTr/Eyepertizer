package com.eyepertizer.androidx.ui.splash.presenter

import android.Manifest
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.BaseMvpPresenter
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.extension.getString
import com.eyepertizer.androidx.ui.splash.view.SplashMvpView
import com.permissionx.guolindev.PermissionCollection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author youngtr
 * @data 2021/5/16
 */
class SplashPresenter<V : SplashMvpView> @Inject constructor(dataManager: AppDataManager) :
    BaseMvpPresenter<V>(dataManager), SplashMvpPresenter<V> {

    private val splashDuration = 3 * 1000L
    private val job by lazy { Job() }


    override fun requestPermission(permissionCollection: PermissionCollection) {
        permissionCollection.permissions(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
        ).onExplainRequestReason { scope, deniedList ->
            val message = R.string.request_permissions.getString()
            scope.showRequestReasonDialog(
                deniedList,
                message,
                R.string.ok.getString(),
                R.string.cancel.getString()
            )
        }.request { allGranted, grantedList, deniedList ->
            getMvpView()?.setupView()
            openMainActivity()
            getDataManager().setFirstEntryApp(false)
        }
    }

    override fun openMainActivity() {
        CoroutineScope(job).launch {
            delay(splashDuration)
            getMvpView()?.openMainActivity()
        }
    }

    override fun onDetach() {
        super.onDetach()
        job.cancel()
    }
}