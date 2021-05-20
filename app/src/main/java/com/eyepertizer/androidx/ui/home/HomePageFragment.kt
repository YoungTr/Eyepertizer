package com.eyepertizer.androidx.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.fragment.BaseViewPagerFragment
import com.eyepertizer.androidx.databinding.FragmentMainContainerBinding
import com.eyepertizer.androidx.extension.getString
import com.eyepertizer.androidx.ui.model.TabEntity
import com.flyco.tablayout.listener.CustomTabEntity

class HomePageFragment : BaseViewPagerFragment() {

    private var _binding: FragmentMainContainerBinding? = null

    private val binding
        get() = _binding!!

    override val createTitles = ArrayList<CustomTabEntity>().apply {
        add(TabEntity(R.string.discovery.getString()))
        add(TabEntity(R.string.commend.getString()))
        add(TabEntity(R.string.daily.getString()))
    }

    override val createFragments: Array<Fragment> = arrayOf(
//        DiscoveryFragment.newInstance(),
//        CommendFragment.newInstance(),
//        DailyFragment.newInstance()
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainContainerBinding.inflate(layoutInflater, container, false)
        return super.onCreateView(binding.root)
    }

    override fun vpSetUp() {
        binding.titleBar.ivCalendar.visibility = View.VISIBLE
        viewPager?.currentItem = 1
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}