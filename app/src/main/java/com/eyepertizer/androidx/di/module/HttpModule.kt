package com.eyepertizer.androidx.di.module

import com.eyepertizer.androidx.data.network.ServiceCreator
import com.eyepertizer.androidx.data.network.api.MainPageApis
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author youngtr
 * @data 2021/5/22
 */
@Module
class HttpModule {

    @Singleton
    @Provides
    fun provideMainApi(): MainPageApis {
        return ServiceCreator.create(MainPageApis::class.java)
    }


}