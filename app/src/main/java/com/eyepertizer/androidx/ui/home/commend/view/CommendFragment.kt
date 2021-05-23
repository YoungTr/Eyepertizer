package com.eyepertizer.androidx.ui.home.commend.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.eyepertizer.androidx.base.fragment.BaseFragment
import com.eyepertizer.androidx.databinding.FragmentRefreshLayoutBinding
import com.eyepertizer.androidx.ui.home.binder.*
import com.eyepertizer.androidx.ui.home.commend.presenter.CommendPresenter
import com.eyepertizer.androidx.util.logD
import javax.inject.Inject

class CommendFragment : BaseFragment(), CommendMvpView {

    @Inject
    lateinit var presenter: CommendPresenter<CommendMvpView>

    private lateinit var adapter: MultiTypeAdapter
    private val items: MutableList<Any> = ArrayList()


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

        adapter = MultiTypeAdapter()
        adapter.register(TextCardViewHeader5Binder())
        adapter.register(TextCardViewHeader7Binder())
        adapter.register(TextCardViewHeader8Binder())
        adapter.register(TextCardViewFooter2Binder())
        adapter.register(TextCardViewFooter3Binder())
        adapter.register(InformationFollowCardViewBinder())
        adapter.register(FollowCardViewBinder())
        adapter.register(VideoSmallCardBinder())
        adapter.register(Banner3Binder())
        adapter.register(BannerBinder())
        adapter.register(UgcSelectedCardCollectionBinder())
        adapter.register(TagBriefCardBinder())
        adapter.register(TopicBriefCardBinder())
        adapter.register(AutoPlayVideoBinder())
        adapter.items = items

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        binding.refreshLayout.setOnRefreshListener {
            logD(TAG, "refresh")
            presenter.getHomePageRecommend()
        }
        binding.refreshLayout.setOnLoadMoreListener {
            logD(TAG, "load more")
            presenter.loadMorePageRecommend()
        }


    }

    override fun lazyInit() {
        super.lazyInit()
        presenter.getHomePageRecommend()
    }

    override fun setData(data: List<Any>) {
        items.clear()
        items.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun addData(data: List<Any>) {
        items.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun closeHeaderOrFooter() {
        binding.refreshLayout.closeHeaderOrFooter()
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