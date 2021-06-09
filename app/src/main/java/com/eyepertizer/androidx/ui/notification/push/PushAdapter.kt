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

package com.eyepertizer.androidx.ui.notification.push

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.data.network.model.PushMessage
import com.eyepertizer.androidx.extension.inflate
import com.eyepertizer.androidx.extension.load
import com.eyepertizer.androidx.util.ActionUrlUtil
import com.eyepertizer.androidx.util.DateUtil


class PushAdapter(val fragment: PushFragment, var dataList: ArrayList<PushMessage.Message>) :
    RecyclerView.Adapter<PushAdapter.ViewHolder>() {

    override fun getItemCount() = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PushAdapter.ViewHolder {
        val holder = ViewHolder(R.layout.item_notification_push.inflate(parent))
        holder.itemView.setOnClickListener {
            val item = dataList[holder.bindingAdapterPosition]
            ActionUrlUtil.process(fragment, item.actionUrl, item.title)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataList[position].run {
            holder.ivAvatar.load(icon) { error(R.mipmap.ic_launcher) }
            holder.tvTitle.text = title
            holder.tvTime.text = DateUtil.getConvertedDate(date)
            holder.tvContent.text = content
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvContent: TextView = view.findViewById(R.id.tvContent)
        val ivAvatar: ImageView = view.findViewById(R.id.ivAvatar)
    }

    fun addData(dataList: List<PushMessage.Message>) {
        if (dataList.isNullOrEmpty()) {
            return
        }
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun setData(dataList: List<PushMessage.Message>) {
        if (dataList.isNullOrEmpty()) {
            return
        }
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }
}