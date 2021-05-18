package com.eyepertizer.androidx.di.dependes

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {
    @Provides
    fun provideSp(context: Context) = context.getSharedPreferences("Coorer", Context.MODE_PRIVATE)
}