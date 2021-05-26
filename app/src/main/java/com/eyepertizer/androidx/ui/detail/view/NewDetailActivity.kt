package com.eyepertizer.androidx.ui.detail.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.activity.BaseActivity
import com.eyepertizer.androidx.data.network.model.VideoDetail
import com.eyepertizer.androidx.data.network.model.VideoRelated
import com.eyepertizer.androidx.data.network.model.VideoReplies
import com.eyepertizer.androidx.databinding.ActivityNewDetailBinding
import com.eyepertizer.androidx.extension.*
import com.eyepertizer.androidx.ui.detail.adapter.NewDetailRelatedAdapter
import com.eyepertizer.androidx.ui.detail.adapter.NewDetailReplyAdapter
import com.eyepertizer.androidx.ui.detail.model.VideoInfo
import com.eyepertizer.androidx.ui.detail.presenter.NewDetailPresenter
import com.eyepertizer.androidx.util.GlobalUtil
import com.eyepertizer.androidx.util.logD
import com.eyepertizer.androidx.widget.NoStatusFooter
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView
import javax.inject.Inject

class NewDetailActivity : BaseActivity(), NewDetailMvpView {
    @Inject
    lateinit var presenter: NewDetailPresenter<NewDetailMvpView>

    private var orientationUtils: OrientationUtils? = null

    private lateinit var relatedAdapter: NewDetailRelatedAdapter
    private lateinit var replyAdapter: NewDetailReplyAdapter
    private lateinit var mergeAdapter: ConcatAdapter

    private val relatedItems: MutableList<VideoRelated.Item> = ArrayList()
    private val repliesItems: MutableList<VideoReplies.Item> = ArrayList()


    private var _binding: ActivityNewDetailBinding? = null
    private val binding: ActivityNewDetailBinding
        get() = _binding!!

    override fun bindView(): View {
        _binding = ActivityNewDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setUp() {
        presenter.onAttach(this)
        initParams()
        relatedAdapter =
            NewDetailRelatedAdapter(this, relatedItems, intent.getParcelableExtra(EXTRA_VIDEOINFO))
        replyAdapter = NewDetailReplyAdapter(this, repliesItems)
        mergeAdapter = ConcatAdapter(relatedAdapter, replyAdapter)
        orientationUtils = OrientationUtils(this, binding.videoPlayer)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = mergeAdapter
        binding.recyclerView.setHasFixedSize(true)
        binding.refreshLayout.run {
            setDragRate(0.7f)
            setHeaderTriggerRate(0.6f)
            setFooterTriggerRate(0.6f)
            setEnableLoadMoreWhenContentNotFull(true)
            setEnableFooterFollowWhenNoMoreData(true)
            setEnableFooterTranslationContent(true)
            setEnableScrollContentWhenLoaded(true)
            binding.refreshLayout.setEnableNestedScroll(true)
            setFooterHeight(153f)
            setRefreshFooter(NoStatusFooter(this@NewDetailActivity).apply {
                setAccentColorId(R.color.white)
                setTextTitleSize(16f)
            })
            setOnRefreshListener { finish() }
            setOnLoadMoreListener {
                logD(TAG, "load more")
                presenter.fetchVideoReplies()
            }
        }
        setOnClickListener(
            binding.ivPullDown,
            binding.ivMore,
            binding.ivShare,
            binding.ivCollection,
            binding.ivToWechatFriends,
            binding.ivShareToWechatMemories,
            binding.ivShareToWeibo,
            binding.ivShareToQQ,
            binding.ivShareToQQzone,
            binding.ivAvatar,
            binding.etComment,
            binding.ivReply,
            binding.tvReplyCount,
            listener = ClickListener()
        )
        presenter.play()
        presenter.fetchVideoDetail()
    }

    private fun initParams() {
        presenter.setInfo(
            intent.getParcelableExtra(EXTRA_VIDEOINFO),
            intent.getLongExtra(EXTRA_VIDEO_ID, 0L)
        )
    }

    override fun setStatusBarPrimaryDark() {
        setStatusBarBackground(R.color.black)
    }

    override fun play(videoInfo: VideoInfo?) {
        videoInfo?.run {
            binding.ivBlurredBg.load(cover.blurred)
            binding.tvReplyCount.text = consumption.replyCount.toString()
            binding.videoPlayer.play(videoInfo)
        }
    }

    private fun GSYVideoPlayer.play(videoInfo: VideoInfo?) {
        videoInfo?.let {
            //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
            fullscreenButton.setOnClickListener { showFull() }
            //防止错位设置
            playTag = TAG
            //音频焦点冲突时是否释放
            isReleaseWhenLossAudio = false
            //增加封面
            val imageView = ImageView(this@NewDetailActivity)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.load(it.cover.detail)
            thumbImageView = imageView
            thumbImageView.setOnClickListener {
//                switchTitleBarVisible()
            }
            //是否开启自动旋转
            isRotateViewAuto = false
            //是否需要全屏锁定屏幕功能
            isNeedLockFull = true
            //是否可以滑动调整
            setIsTouchWiget(true)
            //设置触摸显示控制ui的消失时间
            dismissControlTime = 5000
            //设置播放过程中的回调
            setVideoAllCallBack(VideoCallPlayBack())
            //设置播放URL
            setUp(it.playUrl, false, it.title)
            //开始播放
            startPlayLogic()
        }
    }

    private fun showFull() {
        orientationUtils?.run { if (isLand != 1) resolveByClick() }
        binding.videoPlayer.startWindowFullscreen(this, true, false)
    }

    fun scrollTop() {
        if (relatedAdapter.itemCount != 0) {
            binding.recyclerView.scrollToPosition(0)
            binding.refreshLayout.invisibleAlphaAnimation(2500)
            binding.refreshLayout.visibleAlphaAnimation(1500)
        }
    }

    override fun setData(videoInfo: VideoInfo?, videoDetail: VideoDetail) {
        relatedAdapter.bindVideoInfo(videoInfo)
        relatedItems.clear()
        relatedItems.addAll(videoDetail.videoRelated!!.itemList)
        repliesItems.clear()
        val itemList = videoDetail.videoReplies.itemList
        repliesItems.addAll(itemList)
        relatedAdapter.notifyDataSetChanged()
        replyAdapter.notifyDataSetChanged()
    }

    override fun addReplies(data: List<VideoReplies.Item>) {
        repliesItems.addAll(data)
        replyAdapter.notifyDataSetChanged()
    }

    override fun closeHeaderOrFooter() {
        binding.refreshLayout.closeHeaderOrFooter()
    }

    override fun finishLoadMoreWithNoMoreData() {
        binding.refreshLayout.finishLoadMoreWithNoMoreData()
    }

    inner class VideoCallPlayBack : GSYSampleCallBack() {
        override fun onStartPrepared(url: String?, vararg objects: Any?) {
            super.onStartPrepared(url, *objects)
            binding.flHeader.gone()
            binding.llShares.gone()
        }

        override fun onClickBlank(url: String?, vararg objects: Any?) {
            super.onClickBlank(url, *objects)
            switchTitleBarVisible()
        }

        override fun onClickStop(url: String?, vararg objects: Any?) {
            super.onClickStop(url, *objects)
            presenter.delayHideBottomContainer(binding.videoPlayer.dismissControlTime.toLong())
        }

        override fun onAutoComplete(url: String?, vararg objects: Any?) {
            super.onAutoComplete(url, *objects)
            binding.flHeader.visible()
            binding.ivPullDown.visible()
            binding.ivCollection.gone()
            binding.ivShare.gone()
            binding.ivMore.gone()
            binding.llShares.visible()
        }
    }

    private fun switchTitleBarVisible() {
        if (binding.videoPlayer.currentPlayer.currentState == GSYVideoView.CURRENT_STATE_AUTO_COMPLETE) return
        if (binding.flHeader.visibility == View.VISIBLE) {
            hideTitleBar()
        } else {
            binding.flHeader.visibleAlphaAnimation(1000)
            binding.ivPullDown.visibleAlphaAnimation(1000)
            binding.ivCollection.visibleAlphaAnimation(1000)
            binding.ivMore.visibleAlphaAnimation(1000)
            binding.ivShare.visibleAlphaAnimation(1000)
            presenter.delayHideTitleBar(binding.videoPlayer.dismissControlTime.toLong())
        }
    }


    override fun hideBottomContainer() {
        binding.videoPlayer.getBottomContainer().gone()
        binding.videoPlayer.startButton.gone()
    }

    override fun hideTitleBar() {
        binding.flHeader.invisibleAlphaAnimation(1000)
        binding.ivPullDown.goneAlphaAnimation(1000)
        binding.ivCollection.goneAlphaAnimation(1000)
        binding.ivMore.goneAlphaAnimation(1000)
        binding.ivShare.goneAlphaAnimation(1000)
    }

    override fun onResume() {
        super.onResume()
        binding.videoPlayer.onVideoResume()
    }

    override fun onPause() {
        super.onPause()
        binding.videoPlayer.onVideoPause()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            logD(TAG, "orientation: ${newConfig.orientation}")
        }
        logD(TAG, "onConfigurationChanged: $newConfig")
        binding.videoPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (checkArguments()) {
            initParams()
            presenter.play()
            presenter.fetchVideoDetail()
        }
    }

    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(this)) return
        super.onBackPressed()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.anl_push_bottom_out)
    }

    private fun checkArguments() =
        if (intent.getParcelableExtra<VideoInfo>(EXTRA_VIDEOINFO) == null && intent.getLongExtra(
                EXTRA_VIDEO_ID,
                0L
            ) == 0L
        ) {
            GlobalUtil.getString(R.string.jump_page_unknown_error).showToast()
            finish()
            false
        } else {
            true
        }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
        binding.videoPlayer.release()
        orientationUtils?.releaseListener()
        binding.videoPlayer.setVideoAllCallBack(null)
        _binding = null
    }

    inner class ClickListener : View.OnClickListener {
        override fun onClick(v: View) {
            logD(TAG, "View click: $v")
//            viewModel.videoInfoData?.let {
//                when (v) {
//                    binding.ivPullDown -> finish()
//                    binding.ivMore -> {
//                    }
//                    binding.ivShare -> showDialogShare(it.webUrl.raw)
//                    binding.ivCollection -> LoginActivity.start(this@NewDetailActivity)
//                    binding.ivToWechatFriends -> share(it.webUrl.raw, SHARE_WECHAT)
//                    binding.ivShareToWechatMemories -> share(it.webUrl.raw, SHARE_WECHAT_MEMORIES)
//                    binding.ivShareToWeibo -> share(it.webUrl.forWeibo, SHARE_WEIBO)
//                    binding.ivShareToQQ -> share(it.webUrl.raw, SHARE_QQ)
//                    binding.ivShareToQQzone -> share(it.webUrl.raw, SHARE_QQZONE)
//                    binding.ivAvatar, binding.etComment -> LoginActivity.start(this@NewDetailActivity)
//                    binding.ivReply, binding.tvReplyCount -> scrollRepliesTop()
//                    else -> {
//                    }
//                }
//            }
        }
    }

    companion object {

        const val TAG = "NewDetailActivity"

        const val EXTRA_VIDEOINFO = "videoInfo"
        const val EXTRA_VIDEO_ID = "videoId"

        fun start(context: Context, videoInfo: VideoInfo) {
            val starter = Intent(context, NewDetailActivity::class.java)
            starter.putExtra(EXTRA_VIDEOINFO, videoInfo)
            context.startActivity(starter)
            if (context is Activity) {
                context.overridePendingTransition(
                    R.anim.anl_push_bottom_in,
                    R.anim.anl_push_up_out
                )
            }
        }

        fun start(context: Context, videoId: Long) {
            val starter = Intent(context, NewDetailActivity::class.java)
            starter.putExtra(EXTRA_VIDEO_ID, videoId)
            context.startActivity(starter)
            if (context is Activity) {
                context.overridePendingTransition(
                    R.anim.anl_push_bottom_in,
                    R.anim.anl_push_up_out
                )
            }
        }
    }

}