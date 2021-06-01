package com.eyepertizer.androidx.ui.home.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.eyepertizer.androidx.base.fragment.BaseFragment
import com.eyepertizer.androidx.databinding.FragmentRefreshLayoutBinding
import com.eyepertizer.androidx.extension.showToast
import com.eyepertizer.androidx.ui.home.discovery.adapter.DiscoveryAdapter
import com.scwang.smart.refresh.layout.constant.RefreshState
import javax.inject.Inject

class DiscoveryFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: DiscoveryViewModel

    private var adapter = DiscoveryAdapter(arrayListOf())


    private var _binding: FragmentRefreshLayoutBinding? = null

    private val binding
        get() = _binding!!


    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRefreshLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun setUp() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.itemAnimator = null
        binding.refreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }
        binding.refreshLayout.setOnLoadMoreListener {
            viewModel.loadMore()
        }
        viewModel.discovery.observe(this, Observer { result ->
            hideLoading()
            if (result.data == null) {
                result.message?.showToast()
                return@Observer
            }

            if (result.data.itemList.isNullOrEmpty()) {
                finishLoadMoreWithNoMoreData()
                return@Observer
            }
            when (binding.refreshLayout.state) {
                RefreshState.None, RefreshState.RefreshFinish -> {
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
        super.lazyInit()
        viewModel.onRefresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun closeHeaderOrFooter() {
        binding.refreshLayout.closeHeaderOrFooter()
    }

    private fun finishLoadMoreWithNoMoreData() {
        binding.refreshLayout.finishLoadMoreWithNoMoreData()
    }

    companion object {
        fun newInstance(): DiscoveryFragment {
            val args = Bundle()

            val fragment = DiscoveryFragment()
            fragment.arguments = args
            return fragment
        }
    }

}