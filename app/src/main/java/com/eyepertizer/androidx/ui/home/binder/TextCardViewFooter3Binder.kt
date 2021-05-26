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
import com.eyepertizer.androidx.extension.getString
import com.eyepertizer.androidx.extension.inflate
import com.eyepertizer.androidx.extension.setOnClickListener
import com.eyepertizer.androidx.extension.showToast
import com.eyepertizer.androidx.util.ActionUrlUtil

class TextCardViewFooter3Binder :
    ItemViewBinder<Footer3Model, TextCardViewFooter3Binder.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, item: Footer3Model) {
        holder.tvFooterRightText3.text = item.text
        setOnClickListener(holder.tvRefresh, holder.tvFooterRightText3, holder.ivTooterInto3) {
            if (this == holder.tvRefresh) {
                "${holder.tvRefresh.text},${R.string.currently_not_supported.getString()}".showToast()
            } else if (this == holder.tvFooterRightText3 || this == holder.ivTooterInto3) {
                ActionUrlUtil.process(context as Activity, item.actionUrl, item.text)
            }
        }

    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(R.layout.item_text_card_type_footer_three.inflate(inflater, parent))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRefresh: TextView = itemView.findViewById(R.id.tvRefresh)
        val tvFooterRightText3: TextView = itemView.findViewById(R.id.tvFooterRightText3)
        val ivTooterInto3: ImageView = itemView.findViewById(R.id.ivTooterInto3)
    }

    companion object {
        const val TAG = "TextCardViewHeader5Binder"
    }

}