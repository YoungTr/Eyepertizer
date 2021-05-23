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
import com.eyepertizer.androidx.extension.invisible
import com.eyepertizer.androidx.extension.load
import com.eyepertizer.androidx.extension.visible

class Banner3Binder :
    ItemViewBinder<Banner3, Banner3Binder.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, item: Banner3) {
        holder.ivPicture.load(item.data.image, 4f)
        holder.ivAvatar.load(item.data.header.icon)
        holder.tvTitle.text = item.data.header.title
        holder.tvDescription.text = item.data.header.description
        if (item.data.label?.text.isNullOrEmpty()) holder.tvLabel.invisible() else holder.tvLabel.visible()
        holder.tvLabel.text = item.data.label?.text ?: ""
        holder.itemView.setOnClickListener {
//            ActionUrlUtil.process(fragment,
//                item.data.actionUrl,
//                item.data.header.title)
        }


    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(R.layout.item_text_card_type_header_seven.inflate(inflater, parent))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPicture: ImageView = itemView.findViewById(R.id.ivPicture)
        val tvLabel: TextView = itemView.findViewById(R.id.tvLabel)
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }

    companion object {
        private const val TAG = "MultiTypeAdapter"
    }

}