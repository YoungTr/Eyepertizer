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
        const val TAB_INDEX_NOTIFICATION = 2
        const val TAB_INDEX_MINE = 3
    }

    @Inject
    lateinit var presenter: MainPresenter<MainMvpView>

    private var backPressTime = 0L
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
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
        logD(TAG, "selectionIndex: $selectionIndex")
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
        setNavigationBarSelected(index)
        supportFragmentManager.beginTransaction().apply {
            hideAllFragments(this)
            when (index) {
                TAB_INDEX_HOME -> {
                    if (homePageFragment == null) {
                        homePageFragment = HomePageFragment.newInstance()
                        add(R.id.homeActivityFragContainer, homePageFragment!!)
                    } else {
                        show(homePageFragment!!)
                    }
                }
                TAB_INDEX_COMMUNITY,
                -> {

                }
                TAB_INDEX_NOTIFICATION,
                -> {

                }
                TAB_INDEX_MINE,
                -> {

                }

            }
        }.commitAllowingStateLoss()
    }

    private fun setNavigationBarSelected(index: Int) {
        binding.navigationBar.ivHomePage.isSelected = index == TAB_INDEX_HOME
        binding.navigationBar.ivCommunity.isSelected = index == TAB_INDEX_COMMUNITY
        binding.navigationBar.ivNotification.isSelected = index == TAB_INDEX_NOTIFICATION
        binding.navigationBar.ivMine.isSelected = index == TAB_INDEX_MINE
    }

    private fun hideAllFragments(transaction: FragmentTransaction) {
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