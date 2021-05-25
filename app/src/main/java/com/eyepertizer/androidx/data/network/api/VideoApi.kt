package com.eyepertizer.androidx.data.network.api

import com.eyepertizer.androidx.data.network.model.VideoBeanForClient
import com.eyepertizer.androidx.data.network.model.VideoRelated
import com.eyepertizer.androidx.data.network.model.VideoReplies
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface VideoApi {

    /**
     * 视频详情-视频信息
     */
    @GET("api/v2/video/{id}")
    fun getVideoBeanForClient(@Path("id") videoId: Long): Observable<VideoBeanForClient>

    /**
     * 视频详情-推荐列表
     */
    @GET("api/v4/video/related")
    fun getVideoRelated(@Query("id") videoId: Long): Observable<VideoRelated>

    /**
     * 视频详情-评论列表
     */
    @GET
    fun getVideoReplies(@Url url: String): Observable<VideoReplies>
}