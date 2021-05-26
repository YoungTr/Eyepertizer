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
import com.eyepertizer.androidx.extension.setOnClickListener

class TextCardViewHeader7Binder :
    ItemViewBinder<Header7Model, TextCardViewHeader7Binder.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, item: Header7Model) {
        holder.tvTitle7.text = item.text
        holder.tvRightText7.text = item.rightText
        setOnClickListener(holder.tvRightText7, holder.ivInto7) {
            // TODO: 2021/5/26
//            ActionUrlUtil.process(fragment, model.actionUrl, "${model.text},${model.rightText}")
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(R.layout.item_text_card_type_header_seven.inflate(inflater, parent))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle7: TextView = itemView.findViewById(R.id.tvTitle7)
        val tvRightText7: TextView = itemView.findViewById(R.id.tvRightText7)
        val ivInto7: ImageView = itemView.findViewById(R.id.ivInto7)
    }

    companion object {
        private const val TAG = "MultiTypeAdapter"
    }

}