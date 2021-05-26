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
import com.eyepertizer.androidx.extension.conversionVideoDuration
import com.eyepertizer.androidx.extension.inflate
import com.eyepertizer.androidx.extension.load
import com.eyepertizer.androidx.extension.showDialogShare
import com.eyepertizer.androidx.ui.detail.model.VideoInfo
import com.eyepertizer.androidx.ui.detail.view.NewDetailActivity

class VideoSmallCardBinder :
    ItemViewBinder<VideoSmallModel, VideoSmallCardBinder.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, item: VideoSmallModel) {
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
                    it.context,
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

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(R.layout.item_video_small_card_type.inflate(inflater, parent))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPicture: ImageView = itemView.findViewById(R.id.ivPicture)
        val tvVideoDuration: TextView = itemView.findViewById(R.id.tvVideoDuration)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val ivShare: ImageView = itemView.findViewById(R.id.ivShare)
    }

    companion object {
        private const val TAG = "MultiTypeAdapter"
    }

}