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
import com.eyepertizer.androidx.extension.gone
import com.eyepertizer.androidx.extension.inflate
import com.eyepertizer.androidx.extension.setOnClickListener
import com.eyepertizer.androidx.extension.visible
import com.eyepertizer.androidx.ui.login.LoginActivity
import com.eyepertizer.androidx.util.ActionUrlUtil
import com.eyepertizer.androidx.util.logD

class TextCardViewHeader5Binder :
    ItemViewBinder<Header5Model, TextCardViewHeader5Binder.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, item: Header5Model) {
        holder.tvTitle5.text = item.text
        logD(TAG, item.toString())
        if (item.actionUrl != null) holder.ivInto5.visible() else holder.ivInto5.gone()
        if (item.follow) holder.tvFollow.visible() else holder.tvFollow.gone()
        holder.tvFollow.setOnClickListener {
            LoginActivity.start(it.context)
        }
        setOnClickListener(holder.tvTitle5, holder.ivInto5) {
            ActionUrlUtil.process(
                context as Activity,
                item.actionUrl,
                item.text
            )
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(R.layout.item_text_card_type_header_five.inflate(inflater, parent))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle5: TextView = itemView.findViewById(R.id.tvTitle5)
        val tvFollow: TextView = itemView.findViewById(R.id.tvFollow)
        val ivInto5: ImageView = itemView.findViewById(R.id.ivInto5)
    }

    companion object {
        const val TAG = "TextCardViewHeader5Binder"
    }

}