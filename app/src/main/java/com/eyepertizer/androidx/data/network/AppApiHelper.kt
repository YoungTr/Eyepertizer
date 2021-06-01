package com.eyepertizer.androidx.data.network

import com.eyepertizer.androidx.data.network.api.MainPageApis
import com.eyepertizer.androidx.data.network.api.VideoApi
import com.eyepertizer.androidx.data.network.model.*
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiHelper @Inject constructor(
    private val mainPageApis: MainPageApis,
    private val videoApi: VideoApi
) : ApiHelper {


    override fun getHomePageRecommend(url: String): Observable<HomePageRecommend> {
        return mainPageApis.getHomePageRecommend(url)
    }

    override fun getVideoBeanForClient(videoId: Long): Observable<VideoBeanForClient> {
        return videoApi.getVideoBeanForClient(videoId)
    }

    override fun getVideoRelated(videoId: Long): Observable<VideoRelated> {
        return videoApi.getVideoRelated(videoId)
    }

    override fun getVideoReplies(url: String): Observable<VideoReplies> {
        return videoApi.getVideoReplies(url)
    }

    override fun getDiscovery(url: String): Observable<Discovery> {
        return mainPageApis.getDiscovery(url)
    }
}