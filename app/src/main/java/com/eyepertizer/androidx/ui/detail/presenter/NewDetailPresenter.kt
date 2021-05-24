package com.eyepertizer.androidx.ui.detail.presenter

import com.eyepertizer.androidx.base.presenter.BaseMvpPresenter
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.ui.detail.model.VideoInfo
import com.eyepertizer.androidx.ui.detail.view.NewDetailMvpView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewDetailPresenter<V : NewDetailMvpView> @Inject constructor(
    dataManager: AppDataManager,
    compositeDisposable: CompositeDisposable
) : BaseMvpPresenter<V>(dataManager, compositeDisposable), NewDetailMvpPresenter<V> {

    private var videoInfo: VideoInfo? = null
    private var videoId: Long = -1L

    override fun onAttach(view: V?) {
        super.onAttach(view)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun setInfo(videoInfo: VideoInfo?, videoId: Long) {
        this.videoInfo = videoInfo
        this.videoId = videoId
    }

    override fun play() {
        TODO("Not yet implemented")
    }
}