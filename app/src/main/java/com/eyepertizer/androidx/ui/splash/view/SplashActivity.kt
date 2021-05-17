package com.eyepertizer.androidx.ui.splash.view

import android.os.Bundle
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.BaseActivity
import com.eyepertizer.androidx.ui.splash.presenter.SplashPresenter
import com.permissionx.guolindev.PermissionX

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashActivity : BaseActivity(), SplashMvpView {

    var presenter: SplashPresenter<SplashMvpView> = SplashPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onAttach(this)
        presenter.requestPermission(PermissionX.init(this))
    }

    override fun setView() {
        setContentView(R.layout.activity_splash)
    }

}