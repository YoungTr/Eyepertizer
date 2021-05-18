package com.eyepertizer.androidx.di.ext

import android.content.Context
import dagger.Module
import dagger.Provides

// 父 Component 对应的 Module 用 subcomponents 属性指定拥有哪些子 Component
@Module(subcomponents = [ActivityComponent::class])
class AppModule(private val context: Context) {
    @Provides
    fun provideContext(): Context = context
}