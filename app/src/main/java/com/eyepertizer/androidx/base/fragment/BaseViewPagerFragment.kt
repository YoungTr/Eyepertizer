/*
 * Copyright (c) 2020. vipyinzhiwei <vipyinzhiwei@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eyepertizer.androidx.base.fragment

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.extension.setOnClickListener
import com.eyepertizer.androidx.extension.showToast
import com.eyepertizer.androidx.ui.search.SearchFragment
import com.eyepertizer.androidx.util.logE
import com.eyepertizer.androidx.util.logV
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener

/**
 * Fragment基类，适用场景：页面含有ViewPager+TabLayout的界面。
 *
 * @author vipyinzhiwei
 * @since  2020/5/1
 */
abstract class BaseViewPagerFragment : BaseFragment() {

    protected var ivCalendar: ImageView? = null

    protected var ivSearch: ImageView? = null

    protected var viewPager: ViewPager2? = null

    protected var tabLayout: CommonTabLayout? = null

    protected var pageChangeCallback: PageChangeCallback? = null

    protected val adapter: VpAdapter by lazy {
        VpAdapter(requireActivity()).apply {
            addFragments(
                createFragments
            )
        }
    }

    protected var offscreenPageLimit = 1

    abstract val createTitles: ArrayList<CustomTabEntity>

    abstract val createFragments: Array<Fragment>


    override fun setUp() {
        setupViews()
        viewPagerSetUp()
    }

    abstract fun viewPagerSetUp()


    open fun setupViews() {
        ivCalendar = getRootView()?.findViewById(R.id.ivCalendar)
        ivSearch = getRootView()?.findViewById(R.id.ivSearch)
        setOnClickListener(ivCalendar, ivSearch) {
            if (this == ivCalendar) {
                Thread {
                    triggerGc()
                    triggerGc()
                    triggerGc()
                }.start()
//                ivCalendar?.postDelayed({
//                    KOOM.getInstance().manualTrigger()
//                    KOOM.getInstance().setProgressListener { process ->
//                        process.name.showToast()
//                        logD(TAG, "process:" + process.name)
//                    }
//                }, 3000)
                R.string.currently_not_supported.showToast()
            } else if (this == ivSearch) {
                SearchFragment.switchFragment(getBaseActivity()!!)
            }
        }
        initViewPager()
    }

    protected fun initViewPager() {
        viewPager = getRootView()?.findViewById(R.id.viewPager)
        tabLayout = getRootView()?.findViewById(R.id.tabLayout)

        viewPager?.offscreenPageLimit = offscreenPageLimit
        viewPager?.adapter = adapter
        tabLayout?.setTabData(createTitles)
        tabLayout?.setOnTabSelectListener(object : OnTabSelectListener {

            override fun onTabSelect(position: Int) {
                viewPager?.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }
        })
        pageChangeCallback = PageChangeCallback()
        viewPager?.registerOnPageChangeCallback(pageChangeCallback!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        pageChangeCallback?.run { viewPager?.unregisterOnPageChangeCallback(this) }
        pageChangeCallback = null
    }


    inner class PageChangeCallback : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            tabLayout?.currentTab = position
        }
    }

    inner class VpAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        private val fragments = mutableListOf<Fragment>()

        fun addFragments(fragment: Array<Fragment>) {
            fragments.addAll(fragment)
        }

        override fun getItemCount() = fragments.size

        override fun createFragment(position: Int) = fragments[position]
    }

    open fun triggerGc() {
        logV(TAG, "triggering gc...")
        Runtime.getRuntime().gc()
        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
            logE(TAG, e.message, e)
        }
        Runtime.getRuntime().runFinalization()
        logV(TAG, "gc was triggered.")
    }
}