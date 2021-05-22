package com.eyepertizer.androidx.data.network

import com.eyepertizer.androidx.data.network.model.HomePageRecommend
import io.reactivex.Observable

interface ApiHelper {

    fun getHomePageRecommend(): Observable<HomePageRecommend>
}