package com.eyepertizer.androidx.ui.home.commend.presenter

import com.eyepertizer.androidx.base.presenter.BaseMvpPresenter
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.data.network.api.MainPageApis
import com.eyepertizer.androidx.data.network.model.HomePageRecommend
import com.eyepertizer.androidx.ui.home.commend.view.CommendMvpView
import com.eyepertizer.androidx.util.logD
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author youngtr
 * @data 2021/5/22
 */
class CommendPresenter<V : CommendMvpView> @Inject constructor(
    dataManager: AppDataManager,
    compositeDisposable: CompositeDisposable,
) : BaseMvpPresenter<V>(dataManager, compositeDisposable), CommendMvpPresenter<V> {

    private var url: String? = null

    override fun getHomePageRecommend() {
        url = MainPageApis.HOMEPAGE_RECOMMEND_URL
        addSubscribe(
            getDataManager().getHomePageRecommend(url!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        onSuccess(response, true)
                    },
                    { err ->
                        onError(err)
                    })
        )
    }

    private fun onSuccess(response: HomePageRecommend, refresh: Boolean) {
        url = response.nextPageUrl
        val items = createCommendItems(response.itemList)
        getMvpView()?.let {
            it.hideLoading()
            it.closeHeaderOrFooter()
            if (refresh) {
                it.setData(items)
            } else {
                it.addData(items)
            }
        }
    }

    override fun loadMorePageRecommend() {
        addSubscribe(
            getDataManager().getHomePageRecommend(url!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        onSuccess(response, false)
                    },
                    { err ->
                        onError(err)
                    })
        )
    }

    private fun onError(err: Throwable) {
        getMvpView()?.let {
            it.showToast(err.message)
            it.closeHeaderOrFooter()
        }
        logD(TAG, "error: ${err.message}")
    }

    companion object {
        const val TAG = "CommendPresenter"
    }
}