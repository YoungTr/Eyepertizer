package com.eyepertizer.androidx.ui.home.commend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.eyepertizer.androidx.base.fragment.BaseFragment
import com.eyepertizer.androidx.data.network.model.Commend
import com.eyepertizer.androidx.databinding.FragmentRefreshLayoutBinding
import com.eyepertizer.androidx.ui.home.commend.presenter.CommendPresenter
import com.eyepertizer.androidx.ui.home.discovery.TextCardViewHeader7Binder
import com.eyepertizer.androidx.util.logD
import javax.inject.Inject

class CommendFragment : BaseFragment(), CommendMvpView {

    @Inject
    lateinit var presenter: CommendPresenter<CommendMvpView>

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
        presenter.onAttach(this)

        val items: MutableList<Any> = ArrayList()
        adapter = MultiTypeAdapter()
        adapter.register(TextCardViewHeader7Binder())
        adapter.items = items



        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.itemAnimator = null
        binding.refreshLayout.setOnRefreshListener { logD(TAG, "refresh") }
        binding.refreshLayout.setOnLoadMoreListener { logD(TAG, "load more") }






    }

    override fun lazyInit() {
        super.lazyInit()
        presenter.getHomePageRecommend()

        val items: MutableList<Any> = ArrayList()


        items.add(Commend("五分钟新知"))
        items.add(Commend("网易云音乐"))

        adapter.items = items
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.onDetach()
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