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
import com.eyepertizer.androidx.util.GlobalUtil.setOnClickListener

class TextCardViewFooter2Binder :
    ItemViewBinder<Footer2Model, TextCardViewFooter2Binder.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, item: Footer2Model) {
        holder.tvFooterRightText2.text = item.text
        setOnClickListener(holder.tvFooterRightText2, holder.ivTooterInto2) {
//            ActionUrlUtil.process(fragment, item.actionUrl, item.text)
        }

    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(R.layout.item_text_card_type_footer_two.inflate(inflater, parent))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFooterRightText2: TextView = itemView.findViewById(R.id.tvFooterRightText2)
        val ivTooterInto2: ImageView = itemView.findViewById(R.id.ivTooterInto2)
    }

    companion object {
        const val TAG = "TextCardViewHeader5Binder"
    }

}