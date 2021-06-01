package com.eyepertizer.androidx.ui.home.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.eyepertizer.androidx.base.fragment.BaseFragment
import com.eyepertizer.androidx.databinding.FragmentRefreshLayoutBinding
import com.eyepertizer.androidx.ui.home.discovery.adapter.DiscoveryAdapter
import com.eyepertizer.androidx.util.Status
import com.eyepertizer.androidx.util.logD
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
        logD(TAG, "set up")
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        binding.refreshLayout.setOnRefreshListener {
            logD(TAG, "refresh")
            viewModel.onRefresh()
        }
        binding.refreshLayout.setOnLoadMoreListener {
            logD(TAG, "load more")
            viewModel.loadMore()
        }
        viewModel.discovery.observe(this, Observer { result ->
            hideLoading()
            closeHeaderOrFooter()
            when (result.status) {
                Status.SUCCESS -> {
                    when (binding.refreshLayout.state) {
                        RefreshState.None, RefreshState.RefreshReleased -> {
                            adapter.setData(result.data!!.itemList)
                        }
                        RefreshState.Loading -> {
                            adapter.addData(result.data!!.itemList)
                        }
                        else -> {

                        }
                    }
                }
                Status.ERROR -> finishLoadMoreWithNoMoreData()
                Status.LOADING -> showLoading()
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

    fun closeHeaderOrFooter() {
        binding.refreshLayout.closeHeaderOrFooter()
    }

    fun finishLoadMoreWithNoMoreData() {
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