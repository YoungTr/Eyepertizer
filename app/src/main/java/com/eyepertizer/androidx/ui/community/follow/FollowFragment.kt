package com.eyepertizer.androidx.ui.community.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.fragment.BaseFragment
import com.eyepertizer.androidx.databinding.FragmentRefreshLayoutBinding
import com.eyepertizer.androidx.extension.gone
import com.eyepertizer.androidx.extension.showToast
import com.eyepertizer.androidx.extension.visible
import com.eyepertizer.androidx.ui.common.AutoPlayScrollListener
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.shuyu.gsyvideoplayer.GSYVideoManager
import javax.inject.Inject

class FollowFragment : BaseFragment() {

    private var _binding: FragmentRefreshLayoutBinding? = null

    private val binding
        get() = _binding!!

    @Inject
    lateinit var viewModel: FollowViewModel

    private lateinit var adapter: FollowAdapter

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRefreshLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setUp() {
        adapter = FollowAdapter(this, arrayListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.addOnScrollListener(
            AutoPlayScrollListener(
                R.id.videoPlayer,
                AutoPlayScrollListener.PLAY_RANGE_TOP,
                AutoPlayScrollListener.PLAY_RANGE_BOTTOM
            )
        )
        binding.refreshLayout.setOnRefreshListener {
            binding.refreshLayout.gone()
            viewModel.onRefresh()
        }
        binding.refreshLayout.setOnLoadMoreListener { viewModel.onLoadMore() }
        binding.refreshLayout.gone()
        viewModel.liveData.observe(this, Observer { result ->
            hideLoading()
            binding.refreshLayout.visible()
            if (result.data == null) {
                result.message?.showToast()
                return@Observer
            }

            if (result.data.itemList.isNullOrEmpty()) {
                finishLoadMoreWithNoMoreData()
                return@Observer
            }
            when (binding.refreshLayout.state) {
                RefreshState.None, RefreshState.Refreshing -> {
                    adapter.setData(result.data.itemList)
                }
                RefreshState.Loading -> {
                    adapter.addData(result.data.itemList)
                }
                else -> {

                }
            }
            if (result.data.nextPageUrl.isNullOrEmpty()) {
                finishLoadMoreWithNoMoreData()
            } else {
                closeHeaderOrFooter()
            }
        })
    }

    override fun lazyInit() {
        viewModel.onRefresh()
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


    private fun closeHeaderOrFooter() {
        binding.refreshLayout.closeHeaderOrFooter()
    }

    private fun finishLoadMoreWithNoMoreData() {
        binding.refreshLayout.finishLoadMoreWithNoMoreData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): FollowFragment {
            val args = Bundle()
            val fragment = FollowFragment()
            fragment.arguments = args
            return fragment
        }
    }
}