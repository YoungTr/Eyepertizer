package com.eyepertizer.androidx.ui.detail.presenter

import com.eyepertizer.androidx.base.presenter.BaseMvpPresenter
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.data.network.api.VideoApi
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
    private var url: String? = null

    override fun setInfo(videoInfo: VideoInfo?, videoId: Long) {
        this.videoInfo = videoInfo
        this.videoId = videoInfo?.videoId ?: videoId
        url = "${VideoApi.VIDEO_REPLIES_URL}${this.videoId}"
        logD(TAG, "VideoInfo: $videoInfo, videoId: $videoId, url: $url")

    }

    override fun fetchVideoDetail() {
        addSubscribe(
            getDataManager().fetchVideoDetail(this.url!!, this.videoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    url = response.videoReplies.nextPageUrl ?: ""
                    getMvpView()?.let {
                        it.setData(videoInfo, response)
                        when {
                            response.videoReplies.itemList.isNullOrEmpty() -> it.finishLoadMoreWithNoMoreData()
                            response.videoReplies.nextPageUrl.isNullOrEmpty() -> it.finishLoadMoreWithNoMoreData()
                            else -> it.closeHeaderOrFooter()
                        }
                    }
                }, { error ->
                    getMvpView()?.showToast(error.message)
                }
                )
        )
    }

    override fun fetchVideoReplies() {
        addSubscribe(
            getDataManager().getVideoReplies(url!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    val itemList = response.itemList
                    getMvpView()?.let {
                        it.addReplies(itemList)
                        url = response.nextPageUrl
                        when {
                            itemList.isNullOrEmpty() -> it.finishLoadMoreWithNoMoreData()
                            response.nextPageUrl.isNullOrEmpty() -> it.finishLoadMoreWithNoMoreData()
                            else -> it.closeHeaderOrFooter()
                        }
                    }

                }, { error ->
                    getMvpView()?.showToast(error.message)

                })
        )
    }

    override fun play() {
        getMvpView()?.play(videoInfo)
    }
}