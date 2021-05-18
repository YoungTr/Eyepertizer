package com.eyepertizer.androidx.di.ext

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {
    @Provides
    fun provideSp(context: Context) = context.getSharedPreferences("Cooker", Context.MODE_PRIVATE)
}