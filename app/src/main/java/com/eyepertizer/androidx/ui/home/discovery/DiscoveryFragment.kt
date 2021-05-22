package com.eyepertizer.androidx.ui.home.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.eyepertizer.androidx.base.fragment.BaseFragment
import com.eyepertizer.androidx.data.network.model.Commend
import com.eyepertizer.androidx.databinding.FragmentRefreshLayoutBinding
import com.eyepertizer.androidx.util.logD

class DiscoveryFragment : BaseFragment() {


    private lateinit var adapter: MultiTypeAdapter

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

        var items: MutableList<Any> = ArrayList()

        adapter = MultiTypeAdapter()
        adapter.register(TextCardViewHeader7Binder())

        items.add(Commend("五分钟新知"))
        items.add(Commend("Tang"))

        adapter.items = items

    }

    override fun lazyInit() {
        super.lazyInit()
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.itemAnimator = null
        binding.refreshLayout.setOnRefreshListener { logD(TAG, "refresh") }
        binding.refreshLayout.setOnLoadMoreListener { logD(TAG, "load more") }
        hideLoading()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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