package com.eyepertizer.androidx.ui.home.binder

import android.app.Activity
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.extension.dp2px
import com.eyepertizer.androidx.extension.inflate
import com.eyepertizer.androidx.extension.load
import com.eyepertizer.androidx.util.ActionUrlUtil
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class InformationFollowCardViewBinder :
    ItemViewBinder<InformationFollowModel, InformationFollowCardViewBinder.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, item: InformationFollowModel) {
        holder.ivCover.load(item.backgroundImage, 4f, RoundedCornersTransformation.CornerType.TOP)
        holder.recyclerView.setHasFixedSize(true)
        if (holder.recyclerView.itemDecorationCount == 0) {
            holder.recyclerView.addItemDecoration(InformationCardFollowCardItemDecoration())
        }
        holder.recyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.recyclerView.adapter = InformationCardFollowCardAdapter(/*fragment.activity,*/
            item.actionUrl,
            item.titleList
        )
        holder.itemView.setOnClickListener {
            ActionUrlUtil.process(it.context as Activity, item.actionUrl)
        }

    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(R.layout.item_information_card_type.inflate(inflater, parent))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCover: ImageView = itemView.findViewById(R.id.ivCover)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
    }


    class InformationCardFollowCardAdapter(
        val actionUrl: String?,
        val dataList: List<String>,
    ) :
        RecyclerView.Adapter<InformationCardFollowCardAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvNews: TextView = view.findViewById(R.id.tvNews)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
        ): InformationCardFollowCardAdapter.ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_information_card_type_item, parent, false)
            )
        }

        override fun getItemCount() = dataList.size

        override fun onBindViewHolder(
            holder: InformationCardFollowCardAdapter.ViewHolder,
            position: Int,
        ) {
            val item = dataList[position]
            holder.tvNews.text = item
            holder.itemView.setOnClickListener {
                ActionUrlUtil.process(it.context as Activity, actionUrl)
            }
        }
    }

    class InformationCardFollowCardItemDecoration : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State,
        ) {
            val position = parent.getChildAdapterPosition(view) // item position
            val count = parent.adapter?.itemCount //item count
            count?.run {
                when (position) {
                    0 -> {
                        outRect.top = dp2px(18f)
                    }
                    count.minus(1) -> {
                        outRect.top = dp2px(13f)
                        outRect.bottom = dp2px(18f)
                    }
                    else -> {
                        outRect.top = dp2px(13f)
                    }
                }
            }
        }
    }

}