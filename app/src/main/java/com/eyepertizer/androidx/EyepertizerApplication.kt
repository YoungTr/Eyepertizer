package com.eyepertizer.androidx

import android.annotation.SuppressLint
import android.app.Application

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
    }
}