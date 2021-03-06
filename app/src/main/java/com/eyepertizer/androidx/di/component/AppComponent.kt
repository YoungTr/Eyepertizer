package com.eyepertizer.androidx.di.component

import com.eyepertizer.androidx.EyepertizerApplication
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.di.module.ActivityBindModule
import com.eyepertizer.androidx.di.module.AppModule
import com.eyepertizer.androidx.di.module.FragmentBindModule
import com.eyepertizer.androidx.di.module.HttpModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    ActivityBindModule::class,
    AppModule::class,
    FragmentBindModule::class,
    HttpModule::class])
interface AppComponent {
    fun inject(application: EyepertizerApplication)

    fun getDataManager(): AppDataManager
}