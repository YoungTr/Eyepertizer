package com.eyepertizer.androidx.ui.detail.presenter

import com.eyepertizer.androidx.base.presenter.MvpPresenter
import com.eyepertizer.androidx.ui.detail.model.VideoInfo
import com.eyepertizer.androidx.ui.detail.view.NewDetailMvpView

interface NewDetailMvpPresenter<V : NewDetailMvpView> : MvpPresenter<V> {

    fun setInfo(videoInfo: VideoInfo?, videoId: Long)

    fun fetchVideoDetail()

    fun play()

    fun fetchVideoReplies()

    fun delayHideTitleBar(time: Long)

    fun delayHideBottomContainer(time: Long)
}