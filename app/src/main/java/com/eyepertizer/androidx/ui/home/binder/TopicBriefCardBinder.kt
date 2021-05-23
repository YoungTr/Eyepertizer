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
import com.eyepertizer.androidx.util.GlobalUtil

class TopicBriefCardBinder :
    ItemViewBinder<TopicBriefCardModel, TopicBriefCardBinder.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, item: TopicBriefCardModel) {
        holder.ivPicture.load(item.icon, 4f)
        holder.tvDescription.text = item.description
        holder.tvTitle.text = item.title
        holder.itemView.setOnClickListener { "${item.title},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast() }

    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(R.layout.item_brief_card_topic_brief_card_type.inflate(inflater, parent))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPicture: ImageView = itemView.findViewById(R.id.ivPicture)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }

    companion object {
        const val TAG = "TextCardViewHeader5Binder"
    }

}