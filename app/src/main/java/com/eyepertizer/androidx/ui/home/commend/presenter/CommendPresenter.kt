package com.eyepertizer.androidx.ui.home.commend.presenter

import com.eyepertizer.androidx.base.presenter.BaseMvpPresenter
import com.eyepertizer.androidx.constants.Const
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.ui.home.binder.createHeader5Model
import com.eyepertizer.androidx.ui.home.binder.getItemViewType
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
                { response ->
                    getMvpView()?.hideLoading()

                    val items = mutableListOf<Any>()
                    val itemList = response.itemList
                    for (value in itemList) {
                        val itemViewType = getItemViewType(value)
                        if (itemViewType == Const.ItemViewType.TEXT_CARD_HEADER5) {
                            items.add(createHeader5Model(value.data))
                        }
                    }
                    getMvpView()?.setData(items)
                },
                { err ->
                    getMvpView()?.showToast(err.message)
                    logD(TAG, "error: ${err.message}")
                })
        )
    }

    companion object {
        const val TAG = "CommendPresenter"
    }
}