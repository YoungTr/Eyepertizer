package com.eyepertizer.androidx.ui.community.commend

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.fragment.BaseFragment
import com.eyepertizer.androidx.databinding.FragmentRefreshLayoutBinding
import com.eyepertizer.androidx.extension.dp2px
import com.eyepertizer.androidx.extension.showToast
import com.eyepertizer.androidx.util.GlobalUtil
import com.scwang.smart.refresh.layout.constant.RefreshState
import javax.inject.Inject

class CommendFragment : BaseFragment() {

    /**
     * 列表左or右间距
     */
    val bothSideSpace = GlobalUtil.getDimension(R.dimen.listSpaceSize)

    /**
     * 列表中间内间距，左or右。
     */
    val middleSpace = dp2px(3f)


    /**
     * 通过获取屏幕宽度来计算出每张图片最大的宽度。
     *
     * @return 计算后得出的每张图片最大的宽度。
     */
    val maxImageWidth: Int
        get() {
            val windowManager =
                getBaseActivity()?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val metrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay?.getMetrics(metrics)
            val columnWidth = metrics.widthPixels
            return (columnWidth - ((bothSideSpace * 2) + (middleSpace * 2))) / 2
        }


    private var _binding: FragmentRefreshLayoutBinding? = null

    private val binding
        get() = _binding!!

    private lateinit var adapter: CommendAdapter

    @Inject
    lateinit var viewModel: CommendViewModel

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRefreshLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setUp() {
        adapter = CommendAdapter(this, arrayListOf())
        val mainLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mainLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        binding.recyclerView.layoutManager = mainLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(CommendAdapter.ItemDecoration(this))
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.itemAnimator = null
        binding.refreshLayout.setOnRefreshListener { viewModel.onRefresh() }
        binding.refreshLayout.setOnLoadMoreListener { viewModel.onLoadMore() }
        viewModel.liveData.observe(this, Observer { result ->
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
        super.lazyInit()
        viewModel.onRefresh()
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
        fun newInstance(): CommendFragment {
            val args = Bundle()
            val fragment = CommendFragment()
            fragment.arguments = args
            return fragment
        }
    }
}