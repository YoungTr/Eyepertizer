package com.eyepertizer.androidx.ui.home.commend.presenter

import com.eyepertizer.androidx.base.presenter.BaseMvpPresenter
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.ui.home.commend.CommendMvpView
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

    override fun getHomePageRecommend() {
        addSubscribe(getDataManager().getHomePageRecommend()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> logD(TAG, response.nextPageUrl) },
                { err -> logD(TAG, "error: ${err.message}") })
        )
    }

    companion object {
        const val TAG = "CommendPresenter"
    }
}