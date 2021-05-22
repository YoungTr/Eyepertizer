package com.eyepertizer.androidx.ui.home.commend.presenter

import com.eyepertizer.androidx.base.presenter.BaseMvpPresenter
import com.eyepertizer.androidx.constants.Const
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.ui.home.binder.*
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

                    val start = System.currentTimeMillis()

                    for (value in itemList) {
                        val itemViewType = getItemViewType(value)
                        logD(TAG, "item type: $itemViewType")
                        when (itemViewType) {
                            Const.ItemViewType.TEXT_CARD_HEADER5 -> items.add(createHeader5Model(
                                value.data))
                            Const.ItemViewType.TEXT_CARD_HEADER7 -> items.add(createHeader7Model(
                                value.data))
                            Const.ItemViewType.TEXT_CARD_HEADER8 -> items.add(createHeader8Model(
                                value.data))
                            Const.ItemViewType.FOLLOW_CARD -> items.add(createFollowCardModel(
                                value.data))
                            Const.ItemViewType.VIDEO_SMALL_CARD -> items.add(createVideoSmallModel(
                                value.data))

                        }
//                        if (itemViewType == Const.ItemViewType.TEXT_CARD_HEADER5) {
//                            items.add(createHeader5Model(value.data))
//                        }
                    }
                    val parserDuration = System.currentTimeMillis() - start
                    getMvpView()?.setData(items)
                    val updateDuration = System.currentTimeMillis() - start

                    logD(TAG, "parser: $parserDuration, update: $updateDuration")

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