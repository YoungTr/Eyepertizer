/*
 * Copyright (c) 2020. vipyinzhiwei <vipyinzhiwei@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eyepertizer.androidx.ui.home.discovery.adapter

import android.app.Activity
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eyepertizer.androidx.BuildConfig
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.constants.Const
import com.eyepertizer.androidx.data.network.model.Discovery
import com.eyepertizer.androidx.extension.*
import com.eyepertizer.androidx.ui.common.*
import com.eyepertizer.androidx.ui.detail.model.VideoInfo
import com.eyepertizer.androidx.ui.detail.view.NewDetailActivity
import com.eyepertizer.androidx.ui.home.binder.AutoPlayVideoBinder.Companion.startAutoPlay
import com.eyepertizer.androidx.ui.login.LoginActivity
import com.eyepertizer.androidx.util.ActionUrlUtil
import com.eyepertizer.androidx.util.GlobalUtil
import com.eyepertizer.androidx.widget.GridListItemDecoration
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class DiscoveryAdapter(val dataList: ArrayList<Discovery.Item>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = dataList.size

    override fun getItemViewType(position: Int) =
        RecyclerViewHelp.getItemViewType(dataList[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecyclerViewHelp.getViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        when (holder) {
            is TextCardViewHeader5ViewHolder -> {
                holder.tvTitle5.text = item.data.text
                if (item.data.actionUrl != null) holder.ivInto5.visible() else holder.ivInto5.gone()
                if (item.data.follow != null) holder.tvFollow.visible() else holder.tvFollow.gone()
                holder.tvFollow.setOnClickListener {
                    LoginActivity.start(it.context)
                }
                setOnClickListener(
                    holder.tvTitle5,
                    holder.ivInto5
                ) {
                    ActionUrlUtil.process(context as Activity, item.data.actionUrl, item.data.text)
                }
            }
            is TextCardViewHeader7ViewHolder -> {
                holder.tvTitle7.text = item.data.text
                holder.tvRightText7.text = item.data.rightText
                setOnClickListener(holder.tvRightText7, holder.ivInto7) {
                    ActionUrlUtil.process(
                        context as Activity,
                        item.data.actionUrl,
                        "${item.data.text},${item.data.rightText}"
                    )
                }
            }
            is TextCardViewHeader8ViewHolder -> {
                holder.tvTitle8.text = item.data.text
                holder.tvRightText8.text = item.data.rightText
                setOnClickListener(holder.tvRightText8, holder.ivInto8) {
                    ActionUrlUtil.process(
                        context as Activity,
                        item.data.actionUrl,
                        item.data.text
                    )
                }
            }
            is TextCardViewFooter2ViewHolder -> {
                holder.tvFooterRightText2.text = item.data.text
                setOnClickListener(
                    holder.tvFooterRightText2,
                    holder.ivTooterInto2
                ) {
                    ActionUrlUtil.process(
                        context as Activity,
                        item.data.actionUrl,
                        item.data.text
                    )
                }
            }
            is TextCardViewFooter3ViewHolder -> {
                holder.tvFooterRightText3.text = item.data.text
                setOnClickListener(
                    holder.tvRefresh,
                    holder.tvFooterRightText3,
                    holder.ivTooterInto3
                ) {
                    if (this == holder.tvRefresh) {
                        "${holder.tvRefresh.text},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
                    } else if (this == holder.tvFooterRightText3 || this == holder.ivTooterInto3) {
                        ActionUrlUtil.process(
                            context as Activity,
                            item.data.actionUrl,
                            item.data.text
                        )
                    }
                }
            }
            is FollowCardViewHolder -> {
                holder.ivVideo.load(item.data.content.data.cover.feed, 4f)
                holder.ivAvatar.load(item.data.header.icon)
                holder.tvVideoDuration.text =
                    item.data.content.data.duration.conversionVideoDuration()
                holder.tvDescription.text = item.data.header.description
                holder.tvTitle.text = item.data.header.title
                if (item.data.content.data.ad) holder.tvLabel.visible() else holder.tvLabel.gone()
                if (item.data.content.data.library == "DAILY") holder.ivChoiceness.visible() else holder.ivChoiceness.gone()
                holder.ivShare.setOnClickListener {
                    showDialogShare(
                        it.context as Activity,
                        "${item.data.content.data.title}：${item.data.content.data.webUrl.raw}"
                    )
                }
                holder.itemView.setOnClickListener {
                    item.data.content.data.run {
                        if (ad || author == null) {
                            NewDetailActivity.start(it.context as Activity, id)
                        } else {
                            NewDetailActivity.start(
                                it.context as Activity,
                                VideoInfo(
                                    id,
                                    playUrl,
                                    title,
                                    description,
                                    category,
                                    library,
                                    consumption,
                                    cover,
                                    author,
                                    webUrl
                                )
                            )
                        }
                    }
                }
            }
            is HorizontalScrollCardViewHolder -> {
                holder.bannerViewPager.run {
                    setCanLoop(false)
                    setRoundCorner(dp2px(4f))
                    setRevealWidth(GlobalUtil.getDimension(R.dimen.listSpaceSize))
                    if (item.data.itemList.size == 1) setPageMargin(0) else setPageMargin(dp2px(4f))
                    setIndicatorVisibility(View.GONE)
                    setAdapter(HorizontalScrollCardAdapter())
                    removeDefaultPageTransformer()
                    setOnPageClickListener { position ->
                        ActionUrlUtil.process(
                            context as Activity,
                            item.data.itemList[position].data.actionUrl,
                            item.data.itemList[position].data.title
                        )
                    }
                    create(item.data.itemList)
                }
            }
            is SpecialSquareCardCollectionViewHolder -> {
                holder.tvTitle.text = item.data.header.title
                holder.tvRightText.text = item.data.header.rightText
                setOnClickListener(
                    holder.tvRightText,
                    holder.ivInto
                ) { "${item.data.header.rightText},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast() }
                holder.recyclerView.setHasFixedSize(true)
                holder.recyclerView.isNestedScrollingEnabled = true
                holder.recyclerView.layoutManager =
                    GridLayoutManager(holder.recyclerView.context, 2).apply {
                        orientation = GridLayoutManager.HORIZONTAL
                    }
                if (holder.recyclerView.itemDecorationCount == 0) {
                    holder.recyclerView.addItemDecoration(SpecialSquareCardCollectionItemDecoration())
                }
                val list =
                    item.data.itemList.filter { it.type == "squareCardOfCategory" && it.data.dataType == "SquareCard" }
                holder.recyclerView.adapter = SpecialSquareCardCollectionAdapter(list)
            }
            is ColumnCardListViewHolder -> {
                holder.tvTitle.text = item.data.header.title
                holder.tvRightText.text = item.data.header.rightText
                setOnClickListener(
                    holder.tvRightText,
                    holder.ivInto
                ) { "${item.data.header.rightText},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast() }
                holder.recyclerView.setHasFixedSize(true)
                holder.recyclerView.layoutManager =
                    GridLayoutManager(holder.recyclerView.context, 2)
                if (holder.recyclerView.itemDecorationCount == 0) {
                    holder.recyclerView.addItemDecoration(GridListItemDecoration(2))
                }
                val list =
                    item.data.itemList.filter { it.type == "squareCardOfColumn" && it.data.dataType == "SquareCard" }
                holder.recyclerView.adapter = ColumnCardListAdapter(list)
            }
            is BannerViewHolder -> {
                holder.ivPicture.load(item.data.image, 4f)
                holder.itemView.setOnClickListener {
                    ActionUrlUtil.process(
                        it.context as Activity,
                        item.data.actionUrl,
                        item.data.title
                    )
                }
            }
            is Banner3ViewHolder -> {
                holder.ivPicture.load(item.data.image, 4f)
                holder.ivAvatar.load(item.data.header.icon)
                holder.tvTitle.text = item.data.header.title
                holder.tvDescription.text = item.data.header.description
                if (item.data.label?.text.isNullOrEmpty()) holder.tvLabel.invisible() else holder.tvLabel.visible()
                holder.tvLabel.text = item.data.label?.text ?: ""
                holder.itemView.setOnClickListener {
                    ActionUrlUtil.process(
                        it.context as Activity,
                        item.data.actionUrl,
                        item.data.header.title
                    )
                }
            }
            is VideoSmallCardViewHolder -> {
                holder.ivPicture.load(item.data.cover.feed, 4f)
                holder.tvDescription.text =
                    if (item.data.library == "DAILY") "#${item.data.category} / 开眼精选" else "#${item.data.category}"
                holder.tvTitle.text = item.data.title
                holder.tvVideoDuration.text = item.data.duration.conversionVideoDuration()
                holder.ivShare.setOnClickListener {
                    showDialogShare(
                        it.context as Activity,
                        "${item.data.title}：${item.data.webUrl.raw}"
                    )
                }
                holder.itemView.setOnClickListener {
                    item.data.run {
                        NewDetailActivity.start(
                            it.context as Activity,
                            VideoInfo(
                                id,
                                playUrl,
                                title,
                                description,
                                category,
                                library,
                                consumption,
                                cover,
                                author,
                                webUrl
                            )
                        )
                    }
                }
            }
            is TagBriefCardViewHolder -> {
                holder.ivPicture.load(item.data.icon, 4f)
                holder.tvDescription.text = item.data.description
                holder.tvTitle.text = item.data.title
                if (item.data.follow != null) holder.tvFollow.visible() else holder.tvFollow.gone()
                holder.tvFollow.setOnClickListener { LoginActivity.start(it.context as Activity) }
                holder.itemView.setOnClickListener { "${item.data.title},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast() }
            }
            is TopicBriefCardViewHolder -> {
                holder.ivPicture.load(item.data.icon, 4f)
                holder.tvDescription.text = item.data.description
                holder.tvTitle.text = item.data.title
                holder.itemView.setOnClickListener { "${item.data.title},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast() }
            }
            is AutoPlayVideoAdViewHolder -> {
                item.data.detail?.run {
                    holder.ivAvatar.load(item.data.detail.icon)
                    holder.tvTitle.text = item.data.detail.title
                    holder.tvDescription.text = item.data.detail.description
                    startAutoPlay(
                        holder.tvDescription.context as Activity,
                        holder.videoPlayer,
                        position,
                        url,
                        imageUrl,
                        "CommendAdapter",
                        object : GSYSampleCallBack() {
                            override fun onPrepared(url: String?, vararg objects: Any?) {
                                super.onPrepared(url, *objects)
                                GSYVideoManager.instance().isNeedMute = true
                            }

                            override fun onClickBlank(url: String?, vararg objects: Any?) {
                                super.onClickBlank(url, *objects)
                                ActionUrlUtil.process(
                                    holder.tvDescription.context as Activity,
                                    item.data.detail.actionUrl
                                )
                            }
                        })
                    setOnClickListener(holder.videoPlayer.thumbImageView, holder.itemView) {
                        ActionUrlUtil.process(context as Activity, item.data.detail.actionUrl)
                    }
                }
            }
            else -> {
                holder.itemView.gone()
                if (BuildConfig.DEBUG) "${TAG}:${Const.Toast.BIND_VIEWHOLDER_TYPE_WARN}\n${holder}".showToast()
            }
        }
    }

    fun addData(dataList: List<Discovery.Item>) {
        val oldCount = this.dataList.size
        val addCount = dataList.size
        this.dataList.addAll(dataList)
        notifyItemRangeChanged(oldCount, addCount)
    }

    fun setData(dataList: List<Discovery.Item>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    inner class HorizontalScrollCardAdapter :
        BaseBannerAdapter<Discovery.ItemX, HorizontalScrollCardAdapter.ViewHolder>() {

        inner class ViewHolder(val view: View) : BaseViewHolder<Discovery.ItemX>(view) {

            override fun bindData(item: Discovery.ItemX, position: Int, pageSize: Int) {
                val ivPicture = findView<ImageView>(R.id.ivPicture)
                val tvLabel = findView<TextView>(R.id.tvLabel)
                if (item.data.label?.text.isNullOrEmpty()) tvLabel.invisible() else tvLabel.visible()
                tvLabel.text = item.data.label?.text ?: ""
                ivPicture.load(item.data.image, 4f)
            }
        }

        override fun getLayoutId(viewType: Int): Int {
            return R.layout.item_banner_item_type
        }

        override fun createViewHolder(view: View, viewType: Int): ViewHolder {
            return ViewHolder(view)
        }

        override fun onBind(
            holder: ViewHolder,
            data: Discovery.ItemX,
            position: Int,
            pageSize: Int
        ) {
            holder.bindData(data, position, pageSize)
        }
    }

    inner class SpecialSquareCardCollectionAdapter(val dataList: List<Discovery.ItemX>) :
        RecyclerView.Adapter<SpecialSquareCardCollectionAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val ivPicture = view.findViewById<ImageView>(R.id.ivPicture)
            val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): SpecialSquareCardCollectionAdapter.ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_special_square_card_collection_type_item, parent, false)
            )
        }

        override fun getItemCount() = dataList.size

        override fun onBindViewHolder(
            holder: SpecialSquareCardCollectionAdapter.ViewHolder,
            position: Int
        ) {
            val item = dataList[position]
            holder.ivPicture.load(item.data.image, 4f)
            holder.tvTitle.text = item.data.title
            holder.itemView.setOnClickListener { "${item.data.title},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast() }
        }
    }

    inner class ColumnCardListAdapter(val dataList: List<Discovery.ItemX>) :
        RecyclerView.Adapter<ColumnCardListAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val ivPicture = view.findViewById<ImageView>(R.id.ivPicture)
            val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ColumnCardListAdapter.ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_column_card_list_type_item, parent, false)
            )
        }

        override fun getItemCount() = dataList.size

        override fun onBindViewHolder(holder: ColumnCardListAdapter.ViewHolder, position: Int) {
            val item = dataList[position]
            holder.ivPicture.load(item.data.image, 4f)
            holder.tvTitle.text = item.data.title
            holder.itemView.setOnClickListener { "${item.data.title},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast() }
        }
    }

    inner class SpecialSquareCardCollectionItemDecoration : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view) // item position
            val count = parent.adapter?.itemCount //item count
            val spanIndex = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
            val spanCount = 2
            val lastRowFirstItemPostion = count?.minus(spanCount)   //最后一行,第一个item索引
            val space = dp2px(2f)
            val rightCountSpace = dp2px(14f)

            when (spanIndex) {
                0 -> {
                    outRect.bottom = space
                }
                spanCount - 1 -> {
                    outRect.top = space
                }
                else -> {
                    outRect.top = space
                    outRect.bottom = space
                }
            }
            when {
                position < spanCount -> {
                    outRect.right = space
                }
                position < lastRowFirstItemPostion!! -> {
                    outRect.left = space
                    outRect.right = space
                }
                else -> {
                    outRect.left = space
                    outRect.right = rightCountSpace
                }
            }
        }
    }

    companion object {
        const val TAG = "DiscoveryAdapter"
    }
}