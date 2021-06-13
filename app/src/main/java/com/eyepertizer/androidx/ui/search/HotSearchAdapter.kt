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

package com.eyepertizer.androidx.ui.search

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eyepertizer.androidx.BuildConfig
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.constants.Const
import com.eyepertizer.androidx.data.db.dao.repository.search.model.SearchHistory
import com.eyepertizer.androidx.extension.getString
import com.eyepertizer.androidx.extension.gone
import com.eyepertizer.androidx.extension.inflate
import com.eyepertizer.androidx.extension.showToast
import com.eyepertizer.androidx.util.GlobalUtil

/**
 * 热搜关键词Adapter
 *
 * @author vipyinzhiwei
 * @since  2020/5/26
 */
class HotSearchAdapter(val fragment: SearchFragment, var dataList: MutableList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val histories: MutableList<SearchHistory> = mutableListOf()

    override fun getItemCount() = dataList.size + histories.size + 2

    override fun getItemViewType(position: Int) = when (position) {
        0 -> Const.ItemViewType.CUSTOM_HEADER
        (dataList.size + 1) -> HOT_SEARCH_HISTORY
        else -> HOT_SEARCH_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        Const.ItemViewType.CUSTOM_HEADER, HOT_SEARCH_HISTORY -> HeaderViewHolder(
            R.layout.item_search_header.inflate(
                parent
            )
        )
        else -> HotSearchViewHolder(R.layout.item_search.inflate(parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val text =
                    if (position == 0) R.string.hot_keywords.getString() else R.string.search_history.getString()
                holder.tvTitle.text = text
            }
            is HotSearchViewHolder -> {
                if (position <= dataList.size) {
                    val item = dataList[position - 1]
                    holder.tvKeywords.text = item
                } else {
                    val gap = dataList.size + 2
                    val item = histories[position - gap]
                    holder.tvKeywords.text = item.value
                }
                holder.itemView.setOnClickListener {
                    "${holder.tvKeywords.text}${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
                }
            }
            else -> {
                holder.itemView.gone()
                if (BuildConfig.DEBUG) "${TAG}:${Const.Toast.BIND_VIEWHOLDER_TYPE_WARN}\n${holder}".showToast()
            }
        }
    }

    fun setData(dataList: MutableList<String>) {
        if (!dataList.isNullOrEmpty()) {
            this.dataList.clear()
            this.dataList.addAll(dataList)
            notifyDataSetChanged()
        }
    }

    fun addHistories(histories: List<SearchHistory>) {
        if (!histories.isNullOrEmpty()) {
            this.histories.addAll(histories)
            notifyDataSetChanged()
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    }

    inner class HotSearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvKeywords = view.findViewById<TextView>(R.id.tvKeywords)
    }

    companion object {
        const val TAG = "HotSearchAdapter"
        const val HOT_SEARCH_TYPE = Const.ItemViewType.MAX
        const val HOT_SEARCH_HISTORY = 1000
    }
}