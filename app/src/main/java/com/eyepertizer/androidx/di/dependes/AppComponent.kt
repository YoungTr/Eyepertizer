package com.eyepertizer.androidx.di.dependes

import android.content.Context
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(context: Context)
    fun context(): Context
}