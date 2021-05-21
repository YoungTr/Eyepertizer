package com.eyepertizer.androidx.ui.home.discovery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.data.network.model.Discovery
import com.eyepertizer.androidx.extension.gone
import com.eyepertizer.androidx.extension.inflate
import com.eyepertizer.androidx.extension.visible
import com.eyepertizer.androidx.util.GlobalUtil.setOnClickListener

class TextCardViewHeader5Binder :
    ItemViewBinder<Discovery.Item, TextCardViewHeader5Binder.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, item: Discovery.Item) {
        holder.tvTitle5.text = item.data.text
        if (item.data.actionUrl != null) holder.ivInto5.visible() else holder.ivInto5.gone()
        if (item.data.follow != null) holder.tvFollow.visible() else holder.tvFollow.gone()
        holder.tvFollow.setOnClickListener {
            // TODO: 2021/5/21  
//            LoginActivity.start(fragment.activity)
        }
        setOnClickListener(holder.tvTitle5, holder.ivInto5) {
            // TODO: 2021/5/21  
//            ActionUrlUtil.process(
//                fragment,
//                item.data.actionUrl,
//                item.data.text
//            )
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(R.layout.item_text_card_type_header_five.inflate(inflater, parent))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle5 = itemView.findViewById<TextView>(R.id.tvTitle5)
        val tvFollow = itemView.findViewById<TextView>(R.id.tvFollow)
        val ivInto5 = itemView.findViewById<ImageView>(R.id.ivInto5)
    }


}