package com.eyepertizer.androidx

import android.annotation.SuppressLint
import android.app.Application
import com.eyepertizer.androidx.di.ext.AppModule
import com.eyepertizer.androidx.di.ext.DaggerAppComponent

/**
 * @author youngtr
 * @data 2021/5/16
 */
class EyepertizerApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Application
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent.inject(this)

        val activityComponent = appComponent.activityComponent()
            .build()
        activityComponent.inject(this)
    }
}