package com.eyepertizer.androidx.ui.home.binder

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.extension.inflate
import com.eyepertizer.androidx.extension.setOnClickListener
import com.eyepertizer.androidx.util.ActionUrlUtil

class TextCardViewHeader8Binder :
    ItemViewBinder<Header8Model, TextCardViewHeader8Binder.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, item: Header8Model) {
        holder.tvTitle8.text = item.text
        holder.tvRightText8.text = item.rightText
        setOnClickListener(holder.tvRightText8, holder.ivInto8) {
            ActionUrlUtil.process(
                context as Activity,
                item.actionUrl,
                item.text
            )
        }

    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(R.layout.item_text_card_type_header_eight.inflate(inflater, parent))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle8: TextView = itemView.findViewById(R.id.tvTitle8)
        val tvRightText8: TextView = itemView.findViewById(R.id.tvRightText8)
        val ivInto8: ImageView = itemView.findViewById(R.id.ivInto8)
    }

}