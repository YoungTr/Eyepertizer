package com.eyepertizer.androidx

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.eyepertizer.androidx.di.component.DaggerAppComponent
import com.eyepertizer.androidx.di.module.AppModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * @author youngtr
 * @data 2021/5/16
 */
class EyepertizerApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Any>


    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Application
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        val component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        component.inject(this)

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingActivityInjector
}