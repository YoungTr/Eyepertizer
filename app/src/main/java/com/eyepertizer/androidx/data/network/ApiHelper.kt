package com.eyepertizer.androidx.data.network

import com.eyepertizer.androidx.data.network.model.*
import io.reactivex.Observable

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

    fun getDiscovery(url: String): Observable<Discovery>

    suspend fun getDaily(url: String): Daily

    suspend fun getCommunityRecommend(url: String): CommunityRecommend

    suspend fun getFollow(url: String): Follow

    suspend fun getPushMessage(url: String): PushMessage

    /**
     * 搜索-热搜关键词
     */
    suspend fun getHotSearch(): List<String>

}