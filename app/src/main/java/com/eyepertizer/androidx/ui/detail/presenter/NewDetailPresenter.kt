package com.eyepertizer.androidx.ui.detail.presenter

import com.eyepertizer.androidx.base.presenter.BaseMvpPresenter
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.ui.detail.model.VideoInfo
import com.eyepertizer.androidx.ui.detail.view.NewDetailMvpView
import com.eyepertizer.androidx.util.logD
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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
        logD(TAG, "VideoInfo: $videoInfo, videoId: $videoId")
    }

    override fun fetchVideoDetail() {
        addSubscribe(
            getDataManager().fetchVideoDetail(videoInfo?.playUrl!!, videoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    logD(TAG, response.toString())
                }, { error -> logD(TAG, error.message) }
                )
        )
    }

    override fun play() {
        getMvpView()?.play(videoInfo)
    }
}