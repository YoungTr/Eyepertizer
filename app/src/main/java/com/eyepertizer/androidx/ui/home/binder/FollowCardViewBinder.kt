package com.eyepertizer.androidx.ui.home.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.extension.*
import com.eyepertizer.androidx.util.logD

class FollowCardViewBinder :
    ItemViewBinder<FollowCardModel, FollowCardViewBinder.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, item: FollowCardModel) {
        holder.ivVideo.load(item.followCard.cover.feed, 4f)
        holder.ivAvatar.load(item.icon)
        holder.tvVideoDuration.text = item.followCard.duration.conversionVideoDuration()
        holder.tvDescription.text = item.description
        holder.tvTitle.text = item.title
        if (item.followCard.ad) holder.tvLabel.visible() else holder.tvLabel.gone()
        if (item.followCard.library == "DAILY") holder.ivChoiceness.visible() else holder.ivChoiceness.gone()
        holder.ivShare.setOnClickListener {
//            showDialogShare(fragment.activity,
//                "${item.followCard.title}：${item.followCard.webUrl.raw}")
        }
        holder.itemView.setOnClickListener {
            item.followCard.run {
                logD(TAG, "it.context = ${it.context}")
                if (ad || author == null) {
                    logD(TAG, "ad start new detail")
//                    NewDetailActivity.start(it.context)
//                    NewDetailActivity.start(fragment.activity, id)
                } else {
                    logD(TAG, "start new detail")
//                    NewDetailActivity.start(it.context)
//                    NewDetailActivity.start(
//                        fragment.activity,
//                        NewDetailActivity.VideoInfo(
//                            id,
//                            playUrl,
//                            title,
//                            description,
//                            category,
//                            library,
//                            consumption,
//                            cover,
//                            author,
//                            webUrl)
//                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(R.layout.item_follow_card_type.inflate(inflater, parent))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivVideo: ImageView = itemView.findViewById(R.id.ivVideo)
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val tvVideoDuration: TextView = itemView.findViewById(R.id.tvVideoDuration)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val ivShare: ImageView = itemView.findViewById(R.id.ivShare)
        val tvLabel: TextView = itemView.findViewById(R.id.tvLabel)
        val ivChoiceness: ImageView = itemView.findViewById(R.id.ivChoiceness)
    }

    companion object {
        private const val TAG = "MultiTypeAdapter"
    }

}