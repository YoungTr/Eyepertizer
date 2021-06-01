package com.eyepertizer.androidx.data.network

import com.eyepertizer.androidx.data.network.model.*
import io.reactivex.Observable
import retrofit2.http.Url

interface ApiHelper {

    fun getHomePageRecommend(url: String): Observable<HomePageRecommend>

    /**
     * 视频详情-视频信息
     */
    fun getVideoBeanForClient(videoId: Long): Observable<VideoBeanForClient>

    /**
     * 视频详情-推荐列表
     */
    fun getVideoRelated(videoId: Long): Observable<VideoRelated>

    /**
     * 视频详情-评论列表
     */
    fun getVideoReplies(url: String): Observable<VideoReplies>

    fun getDiscovery(@Url url: String): Observable<Discovery>
}