package com.eyepertizer.androidx.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.fragment.BaseViewPagerFragment
import com.eyepertizer.androidx.databinding.FragmentMainContainerBinding
import com.eyepertizer.androidx.ui.community.commend.CommendFragment
import com.eyepertizer.androidx.ui.community.follow.FollowFragment
import com.eyepertizer.androidx.ui.model.TabEntity
import com.eyepertizer.androidx.util.GlobalUtil
import com.eyepertizer.androidx.util.logD
import com.flyco.tablayout.listener.CustomTabEntity

class CommunityFragment : BaseViewPagerFragment() {

    private var _binding: FragmentMainContainerBinding? = null

    private val binding
        get() = _binding!!


    override val createTitles = ArrayList<CustomTabEntity>().apply {
        add(TabEntity(GlobalUtil.getString(R.string.commend)))
        add(TabEntity(GlobalUtil.getString(R.string.follow)))
    }

    override val createFragments: Array<Fragment> =
        arrayOf(CommendFragment.newInstance(), FollowFragment.newInstance())


    override fun viewPagerSetUp() {
        logD(TAG, "view pager setup")
    }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): CommunityFragment {
            val args = Bundle()
            val fragment = CommunityFragment()
            fragment.arguments = args
            return fragment
        }
    }
}