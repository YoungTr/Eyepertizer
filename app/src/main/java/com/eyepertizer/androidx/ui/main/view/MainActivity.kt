package com.eyepertizer.androidx.ui.main.view

import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.activity.BaseActivity
import com.eyepertizer.androidx.databinding.ActivityMainBinding
import com.eyepertizer.androidx.extension.getString
import com.eyepertizer.androidx.extension.showToast
import com.eyepertizer.androidx.ui.home.HomePageFragment
import com.eyepertizer.androidx.ui.main.presenter.MainPresenter
import com.eyepertizer.androidx.util.GlobalUtil
import com.eyepertizer.androidx.util.GlobalUtil.setOnClickListener
import com.eyepertizer.androidx.util.logD
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMvpView {

    companion object {
        const val PRESS_BACK_DURATION = 2000L
        const val TAB_INDEX_HOME = 0
        const val TAB_INDEX_COMMUNITY = 1
        const val TAB_INDEX_NOTIFICATION = 3
        const val TAB_INDEX_RELEASE = 2
        const val TAB_INDEX_MINE = 4
    }

    @Inject
    lateinit var presenter: MainPresenter<MainMvpView>

    private var backPressTime = 0L
    var _binding: ActivityMainBinding? = null
    val binding: ActivityMainBinding
        get() = _binding!!

    private var homePageFragment: HomePageFragment? = null


    override fun bindView(): View {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setUp() {
        logD(TAG, "setUp: ")
        setOnClickListener(
            binding.navigationBar.btnHomePage,
            binding.navigationBar.btnCommunity,
            binding.navigationBar.btnNotification,
            binding.navigationBar.ivRelease,
            binding.navigationBar.btnMine
        ) {
            when (this) {
                binding.navigationBar.btnHomePage -> {
                    notificationUiRefresh(TAB_INDEX_HOME)
                    setTabSelection(TAB_INDEX_HOME)
                }
                binding.navigationBar.btnCommunity -> {
                    notificationUiRefresh(TAB_INDEX_COMMUNITY)
                    setTabSelection(TAB_INDEX_COMMUNITY)
                }
                binding.navigationBar.btnNotification -> {
                    notificationUiRefresh(TAB_INDEX_NOTIFICATION)
                    setTabSelection(TAB_INDEX_NOTIFICATION)
                }
                binding.navigationBar.ivRelease -> {
                    logD(TAG, "Go to login")
//                    LoginActivity.start(this@MainActivity)
                }
                binding.navigationBar.btnMine -> {
                    notificationUiRefresh(TAB_INDEX_MINE)
                    setTabSelection(TAB_INDEX_MINE)
                }
            }
        }
        setTabSelection(TAB_INDEX_HOME)
    }

    private fun notificationUiRefresh(selectionIndex: Int) {
//        when (selectionIndex) {
//            0 -> {
//                if (binding.navigationBar.ivHomePage.isSelected) EventBus.getDefault().post(RefreshEvent(HomePageFragment::class.java))
//            }
//            1 -> {
//                if (binding.navigationBar.ivCommunity.isSelected) EventBus.getDefault().post(RefreshEvent(CommunityFragment::class.java))
//            }
//            2 -> {
//                if (binding.navigationBar.ivNotification.isSelected) EventBus.getDefault().post(RefreshEvent(NotificationFragment::class.java))
//            }
//            3 -> {
//                if (binding.navigationBar.ivMine.isSelected) EventBus.getDefault().post(RefreshEvent(MineFragment::class.java))
//            }
//        }
    }

    private fun setTabSelection(index: Int) {
        clearAllSelected()
        supportFragmentManager.beginTransaction().apply {
            hideAllFragments(this)
            when (index) {
                TAB_INDEX_HOME -> {
                }
                TAB_INDEX_COMMUNITY
                -> {
                }
                TAB_INDEX_NOTIFICATION
                -> {
                }
                TAB_INDEX_MINE
                -> {
                }

            }
        }
        //        fragmentManager.beginTransaction().apply {
//            hideFragments(this)
//            when (index) {
//                0 -> {
//                    binding.navigationBar.ivHomePage.isSelected = true
//                    binding.navigationBar.tvHomePage.isSelected = true
//                    if (homePageFragment == null) {
//                        homePageFragment = HomePageFragment.newInstance()
//                        add(R.id.homeActivityFragContainer, homePageFragment!!)
//                    } else {
//                        show(homePageFragment!!)
//                    }
//                }
//                1 -> {
//                    binding.navigationBar.ivCommunity.isSelected = true
//                    binding.navigationBar.tvCommunity.isSelected = true
//                    if (communityFragment == null) {
//                        communityFragment = CommunityFragment()
//                        add(R.id.homeActivityFragContainer, communityFragment!!)
//                    } else {
//                        show(communityFragment!!)
//                    }
//                }
//                2 -> {
//                    binding.navigationBar.ivNotification.isSelected = true
//                    binding.navigationBar.tvNotification.isSelected = true
//                    if (notificationFragment == null) {
//                        notificationFragment = NotificationFragment()
//                        add(R.id.homeActivityFragContainer, notificationFragment!!)
//                    } else {
//                        show(notificationFragment!!)
//                    }
//                }
//                3 -> {
//                    binding.navigationBar.ivMine.isSelected = true
//                    binding.navigationBar.tvMine.isSelected = true
//                    if (mineFragment == null) {
//                        mineFragment = MineFragment.newInstance()
//                        add(R.id.homeActivityFragContainer, mineFragment!!)
//                    } else {
//                        show(mineFragment!!)
//                    }
//                }
//                else -> {
//                    binding.navigationBar.ivHomePage.isSelected = true
//                    binding.navigationBar.tvHomePage.isSelected = true
//                    if (homePageFragment == null) {
//                        homePageFragment = HomePageFragment.newInstance()
//                        add(R.id.homeActivityFragContainer, homePageFragment!!)
//                    } else {
//                        show(homePageFragment!!)
//                    }
//                }
//            }
//        }.commitAllowingStateLoss()
    }

    private fun clearAllSelected() {
        binding.navigationBar.ivHomePage.isSelected = false
        binding.navigationBar.ivCommunity.isSelected = false
        binding.navigationBar.ivNotification.isSelected = false
        binding.navigationBar.ivMine.isSelected = false
    }

    fun hideAllFragments(transaction: FragmentTransaction) {
        transaction.run {
            if (homePageFragment != null) hide(homePageFragment!!)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            processBackPressed()
        }
    }

    private fun processBackPressed() {
        val now = System.currentTimeMillis()
        if (now - backPressTime > PRESS_BACK_DURATION) {
            String.format(R.string.press_again_to_exit.getString(), GlobalUtil.appName).showToast()
            backPressTime = now
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}