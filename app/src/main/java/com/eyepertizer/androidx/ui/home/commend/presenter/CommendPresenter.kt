package com.eyepertizer.androidx.ui.home.commend.presenter

import com.eyepertizer.androidx.base.presenter.BaseMvpPresenter
import com.eyepertizer.androidx.constants.Const
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.data.network.api.MainPageService
import com.eyepertizer.androidx.ui.home.binder.*
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
        url = MainPageService.HOMEPAGE_RECOMMEND_URL
        addSubscribe(getDataManager().getHomePageRecommend(url!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    url = response.nextPageUrl

                    val items = mutableListOf<Any>()
                    val itemList = response.itemList

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
                            Const.ItemViewType.BANNER3 -> items.add(createBanner3(
                                value.data))
                            Const.ItemViewType.BANNER -> items.add(createBanner(
                                value.data))
                            Const.ItemViewType.TEXT_CARD_FOOTER2 -> items.add(createFooter2Model(
                                value.data))
                            Const.ItemViewType.TEXT_CARD_FOOTER3 -> items.add(createFooter3Model(
                                value.data))
                            Const.ItemViewType.INFORMATION_CARD -> items.add(
                                createInformationFollowModel(
                                    value.data))
                            Const.ItemViewType.UGC_SELECTED_CARD_COLLECTION -> items.add(
                                createUgcSelectedCardModel(
                                    value.data))
                            Const.ItemViewType.TAG_BRIEFCARD -> items.add(
                                createTagBriefCardModel(
                                    value.data))
                            Const.ItemViewType.TOPIC_BRIEFCARD -> items.add(
                                createTopicBriefCardModel(
                                    value.data))
                            Const.ItemViewType.AUTO_PLAY_VIDEO_AD -> items.add(
                                createAutoPlayModel(
                                    value.data))

                        }
                    }
                    getMvpView()?.let {
                        it.hideLoading()
                        it.closeHeaderOrFooter()
                        it.setData(items)
                    }
                },
                { err ->
                    getMvpView()?.let {
                        it.showToast(err.message)
                        it.closeHeaderOrFooter()
                    }
                    logD(TAG, "error: ${err.message}")
                })
        )
    }

    override fun loadMorePageRecommend() {
        addSubscribe(getDataManager().getHomePageRecommend(url!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    url = response.nextPageUrl

                    val items = mutableListOf<Any>()
                    val itemList = response.itemList

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
                            Const.ItemViewType.BANNER3 -> items.add(createBanner3(
                                value.data))
                            Const.ItemViewType.BANNER -> items.add(createBanner(
                                value.data))
                            Const.ItemViewType.TEXT_CARD_FOOTER2 -> items.add(createFooter2Model(
                                value.data))
                            Const.ItemViewType.TEXT_CARD_FOOTER3 -> items.add(createFooter3Model(
                                value.data))
                            Const.ItemViewType.INFORMATION_CARD -> items.add(
                                createInformationFollowModel(
                                    value.data))
                            Const.ItemViewType.UGC_SELECTED_CARD_COLLECTION -> items.add(
                                createUgcSelectedCardModel(
                                    value.data))
                            Const.ItemViewType.TAG_BRIEFCARD -> items.add(
                                createTagBriefCardModel(
                                    value.data))
                            Const.ItemViewType.TOPIC_BRIEFCARD -> items.add(
                                createTopicBriefCardModel(
                                    value.data))
                            Const.ItemViewType.AUTO_PLAY_VIDEO_AD -> items.add(
                                createAutoPlayModel(
                                    value.data))

                        }
                    }
                    getMvpView()?.let {
                        it.hideLoading()
                        it.closeHeaderOrFooter()
                        it.addData(items)
                    }
                },
                { err ->
                    getMvpView()?.let {
                        it.showToast(err.message)
                        it.closeHeaderOrFooter()
                    }
                    logD(TAG, "error: ${err.message}")
                })
        )
    }

    companion object {
        const val TAG = "CommendPresenter"
    }
}