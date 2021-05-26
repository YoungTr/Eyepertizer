package com.eyepertizer.androidx.ui.detail.view

import com.eyepertizer.androidx.base.view.MvpView
import com.eyepertizer.androidx.data.network.model.VideoDetail
import com.eyepertizer.androidx.data.network.model.VideoReplies
import com.eyepertizer.androidx.ui.detail.model.VideoInfo

interface NewDetailMvpView : MvpView {

    fun play(videoInfo: VideoInfo?)

    fun setData(videoInfo: VideoInfo?, videoDetail: VideoDetail)

    fun addReplies(data: List<VideoReplies.Item>)

    fun closeHeaderOrFooter()

    fun finishLoadMoreWithNoMoreData()
}