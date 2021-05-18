package com.eyepertizer.androidx.di.ext

import com.eyepertizer.androidx.MainActivity
import dagger.Subcomponent


// 子 Component 用 @SubComponent 注解
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

    // 声明一个 Builder 来告诉父 Component 如何创建自己
    @Subcomponent.Builder
    interface Builder {
        fun build(): ActivityComponent
    }
}