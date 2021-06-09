package com.eyepertizer.androidx.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.eyepertizer.androidx.base.viewmodel.BaseRefreshViewModel
import com.eyepertizer.androidx.databinding.FragmentRefreshLayoutBinding
import com.eyepertizer.androidx.extension.showToast
import com.eyepertizer.androidx.util.logD
import com.scwang.smart.refresh.layout.constant.RefreshState

abstract class BaseRefreshFragment<T, VM : BaseRefreshViewModel<T>> :
    BaseFragment() {

    protected var _binding: FragmentRefreshLayoutBinding? = null

    protected val binding
        get() = _binding!!


    abstract var viewModel: VM

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRefreshLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    final override fun setUp() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.itemAnimator = null
        binding.refreshLayout.setOnRefreshListener { viewModel.onRefresh() }
        binding.refreshLayout.setOnLoadMoreListener { viewModel.onLoadMore() }
        setUpRefresh()
        viewModel.liveData.observe(this, Observer { result ->
            hideLoading()
            if (result.data == null) {
                result.message?.showToast()
                return@Observer
            }

            if (isDataNullOrEmpty(result.data)) {
                finishLoadMoreWithNoMoreData()
                return@Observer
            }
            when (binding.refreshLayout.state) {
                RefreshState.None, RefreshState.Refreshing -> {
                    setData(result.data)
                }
                RefreshState.Loading -> {
                    addData(result.data)
                }
                else -> {
                    logD(TAG, "refreshLayout state ${binding.refreshLayout.state}")
                }
            }
            if (isNextUrlNullOrEmpty(result.data)) {
                finishLoadMoreWithNoMoreData()
            } else {
                closeHeaderOrFooter()
            }
        })
    }

    override fun lazyInit() {
        viewModel.onRefresh()
    }

    abstract fun setUpRefresh()

    abstract fun isDataNullOrEmpty(data: T): Boolean

    abstract fun isNextUrlNullOrEmpty(data: T): Boolean

    abstract fun addData(data: T)

    abstract fun setData(data: T)


    private fun closeHeaderOrFooter() {
        binding.refreshLayout.closeHeaderOrFooter()
    }

    private fun finishLoadMoreWithNoMoreData() {
        binding.refreshLayout.finishLoadMoreWithNoMoreData()
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}