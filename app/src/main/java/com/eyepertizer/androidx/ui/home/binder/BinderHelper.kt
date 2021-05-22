package com.eyepertizer.androidx.ui.home.binder

import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.AUTO_PLAY_VIDEO_AD
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.BANNER
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.BANNER3
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.COLUMN_CARD_LIST
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.FOLLOW_CARD
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.HORIZONTAL_SCROLL_CARD
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.INFORMATION_CARD
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.SPECIAL_SQUARE_CARD_COLLECTION
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.TAG_BRIEFCARD
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.TEXT_CARD_FOOTER2
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.TEXT_CARD_FOOTER3
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.TEXT_CARD_HEADER4
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.TEXT_CARD_HEADER5
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.TEXT_CARD_HEADER7
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.TEXT_CARD_HEADER8
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.TOPIC_BRIEFCARD
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.UGC_SELECTED_CARD_COLLECTION
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.UNKNOWN
import com.eyepertizer.androidx.constants.Const.ItemViewType.Companion.VIDEO_SMALL_CARD
import com.eyepertizer.androidx.data.network.model.FollowCard
import com.eyepertizer.androidx.data.network.model.HomePageRecommend
import com.eyepertizer.androidx.util.logD

/**
 * @author youngtr
 * @data 2021/5/22
 */

private const val TAG = "BinderHelper"

fun getItemViewType(type: String, dataType: String) = when (type) {

    "horizontalScrollCard" -> {
        when (dataType) {
            "HorizontalScrollCard" -> HORIZONTAL_SCROLL_CARD
            else -> UNKNOWN
        }
    }
    "specialSquareCardCollection" -> {
        when (dataType) {
            "ItemCollection" -> SPECIAL_SQUARE_CARD_COLLECTION
            else -> UNKNOWN
        }
    }
    "columnCardList" -> {
        when (dataType) {
            "ItemCollection" -> COLUMN_CARD_LIST
            else -> UNKNOWN
        }
    }
    /*"textCard" -> {
        when (item.data.type) {
            "header5" -> TEXT_CARD_HEADER5
            "header7" -> TEXT_CARD_HEADER7
            "header8" -> TEXT_CARD_HEADER8
            "footer2" -> TEXT_CARD_FOOTER2
            "footer3" -> TEXT_CARD_FOOTER3
            else -> UNKNOWN
        }
    }*/
    "banner" -> {
        when (dataType) {
            "Banner" -> BANNER
            else -> UNKNOWN
        }
    }
    "banner3" -> {
        when (dataType) {
            "Banner" -> BANNER3
            else -> UNKNOWN
        }
    }
    "videoSmallCard" -> {
        when (dataType) {
            "VideoBeanForClient" -> VIDEO_SMALL_CARD
            else -> UNKNOWN
        }
    }
    "briefCard" -> {
        when (dataType) {
            "TagBriefCard" -> TAG_BRIEFCARD
            "TopicBriefCard" -> TOPIC_BRIEFCARD
            else -> UNKNOWN
        }
    }
    "followCard" -> {
        when (dataType) {
            "FollowCard" -> FOLLOW_CARD
            else -> UNKNOWN
        }
    }
    "informationCard" -> {
        when (dataType) {
            "InformationCard" -> INFORMATION_CARD
            else -> UNKNOWN
        }
    }
    "ugcSelectedCardCollection" -> {
        when (dataType) {
            "ItemCollection" -> UGC_SELECTED_CARD_COLLECTION
            else -> UNKNOWN
        }
    }
    "autoPlayVideoAd" -> {
        when (dataType) {
            "AutoPlayVideoAdDetail" -> AUTO_PLAY_VIDEO_AD
            else -> UNKNOWN
        }
    }
    else -> UNKNOWN
}

private fun getTextCardType(type: String) = when (type) {
    "header4" -> TEXT_CARD_HEADER4
    "header5" -> TEXT_CARD_HEADER5
    "header7" -> TEXT_CARD_HEADER7
    "header8" -> TEXT_CARD_HEADER8
    "footer2" -> TEXT_CARD_FOOTER2
    "footer3" -> TEXT_CARD_FOOTER3
    else -> UNKNOWN
}

//fun getItemViewType(item: Discovery.Item): Int {
//    return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(item.type, item.data.dataType)
//}
//
fun getItemViewType(item: HomePageRecommend.Item): Int {
    return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(item.type,
        item.data.dataType)
}
//
//fun getItemViewType(item: Daily.Item): Int {
//    return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(item.type, item.data.dataType)
//}
//
//fun getItemViewType(item: Follow.Item): Int {
//    return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(item.type, item.data.dataType)
//}
//
//fun getItemViewType(item: VideoRelated.Item): Int {
//    return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(item.type, item.data.dataType)
//}
//
//fun getItemViewType(item: VideoReplies.Item): Int {
//    return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(item.type, item.data.dataType)
//}

data class Header5Model(val text: String, val actionUrl: String?, val follow: Boolean) {
    override fun toString(): String {
        return "Header5Model(text='$text', actionUrl='$actionUrl', follow=$follow)"
    }
}

data class Header7Model(val text: String, val rightText: String, val actionUrl: String?) {
    override fun toString(): String {
        return "Header7Model(text='$text', rightText='$rightText', actionUrl=$actionUrl)"
    }
}

data class Header8Model(val text: String, val rightText: String, val actionUrl: String?) {
    override fun toString(): String {
        return "Header7Model(text='$text', rightText='$rightText', actionUrl=$actionUrl)"
    }
}

data class VideoSmallModel(val data: HomePageRecommend.Data)

data class FollowCardModel(
    val icon: String,
    val description: String,
    val title: String,
    val followCard: FollowCard,

    ) {
    override fun toString(): String {
        return "FollowCardModel(icon='$icon', description='$description', title='$title', followCard=$followCard)"
    }
}

fun createHeader5Model(item: HomePageRecommend.Data): Header5Model {
    logD(TAG, "item follow: ${item.follow}")
    return Header5Model(item.text, item.actionUrl, item.follow != null)
}

fun createHeader7Model(item: HomePageRecommend.Data): Header7Model {
    logD(TAG, "item follow: ${item.text}")
    return Header7Model(item.text, item.rightText, item.actionUrl)
}

fun createHeader8Model(item: HomePageRecommend.Data): Header8Model {
    logD(TAG, "item follow: ${item.text}")
    return Header8Model(item.text, item.rightText, item.actionUrl)
}

fun createFollowCardModel(item: HomePageRecommend.Data): FollowCardModel {
    return FollowCardModel(item.header.icon,
        item.header.description,
        item.header.title,
        item.content.data)
}

fun createVideoSmallModel(item: HomePageRecommend.Data): VideoSmallModel {
    return VideoSmallModel(item)
}