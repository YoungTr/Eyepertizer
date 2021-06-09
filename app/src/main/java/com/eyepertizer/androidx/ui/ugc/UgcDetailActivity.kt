package com.eyepertizer.androidx.ui.ugc

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.activity.BaseActivity
import com.eyepertizer.androidx.data.network.model.CommunityRecommend
import com.eyepertizer.androidx.databinding.ActivityUgcDetailBinding
import com.eyepertizer.androidx.extension.showToast
import com.eyepertizer.androidx.ui.common.AutoPlayPageChangeListener
import com.eyepertizer.androidx.util.GlobalUtil
import com.eyepertizer.androidx.util.IntentDataHolderUtil
import com.shuyu.gsyvideoplayer.GSYVideoManager

class UgcDetailActivity : BaseActivity() {

    private var _binding: ActivityUgcDetailBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var adapter: UgcDetailAdapter

    override fun bindView(): View {
        _binding = ActivityUgcDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setStatusBarPrimaryDark() {
        setStatusBarBackground(R.color.black)
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        _binding = null
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.anl_push_bottom_out)
    }


    override fun setUp() {

        val position = getCurrentItemPosition()
        val list: List<CommunityRecommend.Item>? =
            IntentDataHolderUtil.getData<List<CommunityRecommend.Item>>(
                EXTRA_RECOMMEND_ITEM_LIST_JSON
            )

        if (list.isNullOrEmpty()) {
            GlobalUtil.getString(R.string.jump_page_unknown_error).showToast()
            finish()
        } else {
            adapter = UgcDetailAdapter(this, list)
            binding.viewPager.adapter = adapter
            binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
            binding.viewPager.offscreenPageLimit = 1
            binding.viewPager.registerOnPageChangeCallback(
                AutoPlayPageChangeListener(
                    binding.viewPager,
                    position,
                    R.id.videoPlayer
                )
            )
            binding.viewPager.setCurrentItem(position, false)
        }
    }

    private fun getCurrentItemPosition(): Int {
        val list = IntentDataHolderUtil.getData<List<CommunityRecommend.Item>>(
            EXTRA_RECOMMEND_ITEM_LIST_JSON
        )
        val currentItem =
            IntentDataHolderUtil.getData<CommunityRecommend.Item>(EXTRA_RECOMMEND_ITEM_JSON)
        var position: Int = -1

        list?.forEachIndexed { index, item ->
            if (currentItem == item) {
                position = index
                return@forEachIndexed
            }
        }
        return position
    }

    companion object {
        const val TAG = "UgcDetailActivity"
        const val EXTRA_RECOMMEND_ITEM_LIST_JSON = "recommend_item_list"
        const val EXTRA_RECOMMEND_ITEM_JSON = "recommend_item"

        fun start(
            context: Activity,
            dataList: List<CommunityRecommend.Item>,
            currentItem: CommunityRecommend.Item
        ) {
            IntentDataHolderUtil.setData(EXTRA_RECOMMEND_ITEM_LIST_JSON, dataList)
            IntentDataHolderUtil.setData(EXTRA_RECOMMEND_ITEM_JSON, currentItem)
            val starter = Intent(context, UgcDetailActivity::class.java)
            context.startActivity(starter)
            context.overridePendingTransition(R.anim.anl_push_bottom_in, R.anim.anl_push_up_out)
        }
    }
}