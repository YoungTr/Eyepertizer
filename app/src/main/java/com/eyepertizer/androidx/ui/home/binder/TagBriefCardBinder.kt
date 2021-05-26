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
import com.eyepertizer.androidx.ui.login.LoginActivity

class TagBriefCardBinder :
    ItemViewBinder<TagBriefCardModel, TagBriefCardBinder.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, item: TagBriefCardModel) {
        holder.ivPicture.load(item.icon, 4f)
        holder.tvDescription.text = item.description
        holder.tvTitle.text = item.title
        if (item.follow) holder.tvFollow.visible() else holder.tvFollow.gone()
        holder.tvFollow.setOnClickListener {
            LoginActivity.start(it.context)
        }
        holder.itemView.setOnClickListener {
            "${item.title},${R.string.currently_not_supported.getString()}".showToast()
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(R.layout.item_brief_card_tag_brief_card_type.inflate(inflater, parent))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPicture: ImageView = itemView.findViewById(R.id.ivPicture)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvFollow: TextView = itemView.findViewById(R.id.tvFollow)
    }

    companion object {
        const val TAG = "TextCardViewHeader5Binder"
    }

}