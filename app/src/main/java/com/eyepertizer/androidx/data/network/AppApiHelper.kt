package com.eyepertizer.androidx.data.network

import com.eyepertizer.androidx.data.network.api.MainPageApis
import com.eyepertizer.androidx.data.network.model.HomePageRecommend
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiHelper @Inject constructor(private val mainPageApis: MainPageApis) : ApiHelper {


    override fun getHomePageRecommend(): Observable<HomePageRecommend> {
        return mainPageApis.getHomePageRecommend()
    }
}