package com.eyepertizer.androidx.ui.home.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.extension.inflate
import com.eyepertizer.androidx.extension.load

class BannerBinder :
    ItemViewBinder<Banner, BannerBinder.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, item: Banner) {
        holder.ivPicture.load(item.image, 4f)
        holder.itemView.setOnClickListener {
//            ActionUrlUtil.process(fragment, item.actionUrl, item.title)
        }

    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(R.layout.item_banner_type.inflate(inflater, parent))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPicture: ImageView = itemView.findViewById(R.id.ivPicture)
    }

    companion object {
        private const val TAG = "MultiTypeAdapter"
    }

}