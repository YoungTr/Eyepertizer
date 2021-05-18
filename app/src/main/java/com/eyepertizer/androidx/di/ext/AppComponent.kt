package com.eyepertizer.androidx.di.ext

import android.content.Context
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(context: Context)

    //    fun context(): Context 不再需要

    // 父 Component 声明一个抽象方法来获取子 Component 的 Builder
    fun activityComponent(): ActivityComponent.Builder
}