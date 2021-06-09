package com.eyepertizer.androidx.di.module

import com.eyepertizer.androidx.ui.detail.view.NewDetailActivity
import com.eyepertizer.androidx.ui.login.LoginActivity
import com.eyepertizer.androidx.ui.main.view.MainActivity
import com.eyepertizer.androidx.ui.splash.view.SplashActivity
import com.eyepertizer.androidx.ui.ugc.UgcDetailActivity
import com.eyepertizer.androidx.ui.web.WebViewActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindModule {

    @ContributesAndroidInjector
    abstract fun splashActivityInjector(): SplashActivity

    @ContributesAndroidInjector
    abstract fun mainActivityInjector(): MainActivity

    @ContributesAndroidInjector
    abstract fun newDetailActivityInjector(): NewDetailActivity

    @ContributesAndroidInjector
    abstract fun loginActivityInjector(): LoginActivity

    @ContributesAndroidInjector
    abstract fun webActivityInjector(): WebViewActivity

    @ContributesAndroidInjector
    abstract fun gucActivityInjector(): UgcDetailActivity
}