package com.eyepertizer.androidx.ui.home.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.extension.inflate
import com.eyepertizer.androidx.extension.load
import com.eyepertizer.androidx.extension.showToast
import com.eyepertizer.androidx.extension.visible
import com.eyepertizer.androidx.util.logD
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class UgcSelectedCardCollectionBinder :
    ItemViewBinder<UgcSelectedCardModel, UgcSelectedCardCollectionBinder.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, item: UgcSelectedCardModel) {
        holder.tvTitle.text = item.data.header.title
        holder.tvRightText.text = item.data.header.rightText
        holder.tvRightText.setOnClickListener {
//            EventBus.getDefault()
//                .post(SwitchPagesEvent(com.eyepetizer.android.ui.community.commend.CommendFragment::class.java))
//            EventBus.getDefault().post(RefreshEvent(CommunityFragment::class.java))
        }
        item.data.itemList.forEachIndexed { index, it ->
            logD(TAG, "index=$index, url={${it.data.url}}")
            when (index) {
                LEFT_COVER -> {
                    holder.ivCoverLeft.load(
                        it.data.url,
                        4f,
                        RoundedCornersTransformation.CornerType.LEFT
                    )
                    if (!it.data.urls.isNullOrEmpty() && it.data.urls.size > 1) holder.ivLayersLeft.visible()
                    holder.ivAvatarLeft.load(it.data.userCover)
                    holder.tvNicknameLeft.text = it.data.nickname
                }
                RIGHT_TOP_COVER -> {
                    holder.ivCoverRightTop.load(
                        it.data.url,
                        4f,
                        RoundedCornersTransformation.CornerType.TOP_RIGHT
                    )
                    if (!it.data.urls.isNullOrEmpty() && it.data.urls.size > 1) holder.ivLayersRightTop.visible()
                    holder.ivAvatarRightTop.load(it.data.userCover)
                    holder.tvNicknameRightTop.text = it.data.nickname
                }
                RIGHT_BOTTOM_COVER -> {
                    holder.ivCoverRightBottom.load(
                        it.data.url,
                        4f,
                        RoundedCornersTransformation.CornerType.BOTTOM_RIGHT
                    )
                    if (!it.data.urls.isNullOrEmpty() && it.data.urls.size > 1) holder.ivLayersRightBottom.visible()
                    holder.ivAvatarRightBottom.load(it.data.userCover)
                    holder.tvNicknameRightBottom.text = it.data.nickname
                }
            }
        }
        holder.itemView.setOnClickListener { R.string.currently_not_supported.showToast() }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(R.layout.item_ugc_selected_card_collection_type.inflate(inflater, parent))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvRightText: TextView = itemView.findViewById(R.id.tvRightText)
        val ivCoverLeft: ImageView = itemView.findViewById(R.id.ivCoverLeft)
        val ivLayersLeft: ImageView = itemView.findViewById(R.id.ivLayersLeft)
        val ivAvatarLeft: ImageView = itemView.findViewById(R.id.ivAvatarLeft)
        val tvNicknameLeft: TextView = itemView.findViewById(R.id.tvNicknameLeft)
        val ivCoverRightTop: ImageView = itemView.findViewById(R.id.ivCoverRightTop)
        val ivLayersRightTop: ImageView = itemView.findViewById(R.id.ivLayersRightTop)
        val ivAvatarRightTop: ImageView = itemView.findViewById(R.id.ivAvatarRightTop)
        val tvNicknameRightTop: TextView = itemView.findViewById(R.id.tvNicknameRightTop)
        val ivCoverRightBottom: ImageView = itemView.findViewById(R.id.ivCoverRightBottom)
        val ivLayersRightBottom: ImageView = itemView.findViewById(R.id.ivLayersRightBottom)
        val ivAvatarRightBottom: ImageView = itemView.findViewById(R.id.ivAvatarRightBottom)
        val tvNicknameRightBottom: TextView = itemView.findViewById(R.id.tvNicknameRightBottom)

    }

    companion object {
        private const val TAG = "UgcSelected"
        private const val LEFT_COVER = 0
        private const val RIGHT_TOP_COVER = 1
        private const val RIGHT_BOTTOM_COVER = 2
    }

}