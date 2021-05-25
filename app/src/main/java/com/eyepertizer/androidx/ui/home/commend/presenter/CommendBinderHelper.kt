package com.eyepertizer.androidx.ui.home.commend.presenter

import com.eyepertizer.androidx.constants.Const
import com.eyepertizer.androidx.data.network.model.HomePageRecommend
import com.eyepertizer.androidx.ui.home.binder.*
import com.eyepertizer.androidx.util.logD


private const val TAG = "CommendBinderHelper"

fun createCommendItems(itemList: List<HomePageRecommend.Item>): List<Any> {
    val items = mutableListOf<Any>()

    for (value in itemList) {
        val itemViewType = getItemViewType(value)
        logD(TAG, "item type: $itemViewType")
        when (itemViewType) {
            Const.ItemViewType.TEXT_CARD_HEADER5 -> items.add(
                createHeader5Model(
                    value.data
                )
            )
            Const.ItemViewType.TEXT_CARD_HEADER7 -> items.add(
                createHeader7Model(
                    value.data
                )
            )
            Const.ItemViewType.TEXT_CARD_HEADER8 -> items.add(
                createHeader8Model(
                    value.data
                )
            )
            Const.ItemViewType.FOLLOW_CARD -> items.add(
                createFollowCardModel(
                    value.data
                )
            )
            Const.ItemViewType.VIDEO_SMALL_CARD -> items.add(
                createVideoSmallModel(
                    value.data
                )
            )
            Const.ItemViewType.BANNER3 -> items.add(
                createBanner3(
                    value.data
                )
            )
            Const.ItemViewType.BANNER -> items.add(
                createBanner(
                    value.data
                )
            )
            Const.ItemViewType.TEXT_CARD_FOOTER2 -> items.add(
                createFooter2Model(
                    value.data
                )
            )
            Const.ItemViewType.TEXT_CARD_FOOTER3 -> items.add(createFooter3Model(value.data))
            Const.ItemViewType.INFORMATION_CARD -> items.add(createInformationFollowModel(value.data))
            Const.ItemViewType.UGC_SELECTED_CARD_COLLECTION -> items.add(
                createUgcSelectedCardModel(value.data)
            )
            Const.ItemViewType.TAG_BRIEFCARD -> items.add(createTagBriefCardModel(value.data))
            Const.ItemViewType.TOPIC_BRIEFCARD -> items.add(createTopicBriefCardModel(value.data))
            Const.ItemViewType.AUTO_PLAY_VIDEO_AD -> items.add(createAutoPlayModel(value.data))

        }

    }
    return items
}

