package com.eyepertizer.androidx.ui.detail.view

import com.eyepertizer.androidx.base.view.MvpView
import com.eyepertizer.androidx.ui.detail.model.VideoInfo

interface NewDetailMvpView : MvpView {

    fun play(videoInfo: VideoInfo?)
}