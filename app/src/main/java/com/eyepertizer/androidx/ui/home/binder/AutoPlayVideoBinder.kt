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
import com.eyepertizer.androidx.extension.inflate
import com.eyepertizer.androidx.extension.load
import com.eyepertizer.androidx.util.GlobalUtil.setOnClickListener
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer

class AutoPlayVideoBinder :
    ItemViewBinder<AutoPlayModel, AutoPlayVideoBinder.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, item: AutoPlayModel) {
        item.data?.run {
            holder.ivAvatar.load(item.data.icon)
            holder.tvTitle.text = item.data.title
            holder.tvDescription.text = item.data.description
//            startAutoPlay(fragment.activity,
//                holder.videoPlayer,
//                position,
//                url,
//                imageUrl,
//                TAG,
//                object : GSYSampleCallBack() {
//                    override fun onPrepared(url: String?, vararg objects: Any?) {
//                        super.onPrepared(url, *objects)
//                        GSYVideoManager.instance().isNeedMute = true
//                    }
//
//                    override fun onClickBlank(url: String?, vararg objects: Any?) {
//                        super.onClickBlank(url, *objects)
////                        ActionUrlUtil.process(fragment, item.data.actionUrl)
//                    }
//                })
            setOnClickListener(holder.videoPlayer.thumbImageView, holder.itemView) {
//                ActionUrlUtil.process(fragment, item.data.detail.actionUrl)
            }
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(R.layout.item_text_card_type_header_five.inflate(inflater, parent))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLabel: TextView = itemView.findViewById(R.id.tvLabel)
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val videoPlayer: GSYVideoPlayer = itemView.findViewById(R.id.videoPlayer)
    }

    companion object {
        const val TAG = "TextCardViewHeader5Binder"

        fun startAutoPlay(
            activity: Activity,
            player: GSYVideoPlayer,
            position: Int,
            playUrl: String,
            coverUrl: String,
            playTag: String,
            callBack: GSYSampleCallBack? = null,
        ) {
            player.run {
                //防止错位设置
                setPlayTag(playTag)
                //设置播放位置防止错位
                setPlayPosition(position)
                //音频焦点冲突时是否释放
                setReleaseWhenLossAudio(false)
                //设置循环播放
                setLooping(true)
                //增加封面
                val cover = ImageView(activity)
                cover.scaleType = ImageView.ScaleType.CENTER_CROP
                cover.load(coverUrl, 4f)
                cover.parent?.run { removeView(cover) }
                setThumbImageView(cover)
                //设置播放过程中的回调
                setVideoAllCallBack(callBack)
                //设置播放URL
                setUp(playUrl, false, null)
            }
        }
    }

}