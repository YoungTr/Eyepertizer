package com.eyepertizer.androidx.di.dependes

import android.app.Activity
import dagger.Component

@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: Activity)
}