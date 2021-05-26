package com.eyepertizer.androidx.ui.detail.presenter

import com.eyepertizer.androidx.base.presenter.BaseMvpPresenter
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.data.network.api.VideoApi
import com.eyepertizer.androidx.data.network.model.VideoReplies
import com.eyepertizer.androidx.ui.detail.model.VideoInfo
import com.eyepertizer.androidx.ui.detail.view.NewDetailMvpView
import com.eyepertizer.androidx.util.logD
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject

class NewDetailPresenter<V : NewDetailMvpView> @Inject constructor(
    dataManager: AppDataManager,
    compositeDisposable: CompositeDisposable
) : BaseMvpPresenter<V>(dataManager, compositeDisposable), NewDetailMvpPresenter<V> {

    private var videoInfo: VideoInfo? = null
    private var videoId: Long = -1L
    private var url: String? = null

    private var hideTitleBarJob: Job? = null
    private var hideBottomContainerJob: Job? = null
    private val globalJob by lazy { Job() }


    override fun setInfo(videoInfo: VideoInfo?, videoId: Long) {
        this.videoInfo = videoInfo
        this.videoId = videoInfo?.videoId ?: videoId
        url = "${VideoApi.VIDEO_REPLIES_URL}${this.videoId}"
        logD(TAG, "url: $url")
    }

    override fun fetchVideoDetail() {
        addSubscribe(
            getDataManager().fetchVideoDetail(this.url!!, this.videoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    getMvpView()?.setData(videoInfo, response)
                    val videoReplies = response.videoReplies
                    onSuccess(videoReplies)
                },
                    { error -> onError(error) }
                )
        )
    }

    private fun onSuccess(videoReplies: VideoReplies) {
        url = videoReplies.nextPageUrl ?: ""
        val itemList = videoReplies.itemList
        getMvpView()?.let {
            when {
                itemList.isNullOrEmpty() -> it.finishLoadMoreWithNoMoreData()
                videoReplies.nextPageUrl.isNullOrEmpty() -> it.finishLoadMoreWithNoMoreData()
                else -> it.closeHeaderOrFooter()
            }
        }
    }

    private fun onError(error: Throwable) {
        getMvpView()?.showToast(error.message)
    }

    override fun fetchVideoReplies() {
        addSubscribe(
            getDataManager().getVideoReplies(url!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    getMvpView()?.addReplies(response.itemList)
                    onSuccess(response)
                }, { error -> onError(error) }
                )
        )
    }

    override fun play() {
        getMvpView()?.play(videoInfo)
    }

    override fun delayHideTitleBar(time: Long) {
        hideTitleBarJob?.cancel()
        hideTitleBarJob = CoroutineScope(globalJob).launch(Dispatchers.Main) {
            delay(time)
            getMvpView()?.hideTitleBar()
        }
    }

    override fun delayHideBottomContainer(time: Long) {
        hideBottomContainerJob?.cancel()
        hideBottomContainerJob = CoroutineScope(globalJob).launch(Dispatchers.Main) {
            delay(time)
            getMvpView()?.hideBottomContainer()
        }
    }

    override fun onDetach() {
        super.onDetach()
        globalJob.complete()
    }
}