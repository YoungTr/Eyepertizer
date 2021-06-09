package com.eyepertizer.androidx.ui.main.view

import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.activity.BaseActivity
import com.eyepertizer.androidx.databinding.ActivityMainBinding
import com.eyepertizer.androidx.extension.getString
import com.eyepertizer.androidx.extension.setOnClickListener
import com.eyepertizer.androidx.extension.showToast
import com.eyepertizer.androidx.ui.community.CommunityFragment
import com.eyepertizer.androidx.ui.home.HomePageFragment
import com.eyepertizer.androidx.ui.login.LoginActivity
import com.eyepertizer.androidx.ui.main.presenter.MainPresenter
import com.eyepertizer.androidx.ui.notification.NotificationFragment
import com.eyepertizer.androidx.util.GlobalUtil
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
    private var communityFragment: CommunityFragment? = null
    private var notificationFragment: NotificationFragment? = null


    override fun bindView(): View {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setUp() {
        presenter.onAttach(this)
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
                    LoginActivity.start(this@MainActivity)
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
                    if (communityFragment == null) {
                        communityFragment = CommunityFragment.newInstance()
                        add(R.id.homeActivityFragContainer, communityFragment!!)
                    } else {
                        show(communityFragment!!)
                    }
                }
                TAB_INDEX_NOTIFICATION,
                -> {
                    if (notificationFragment == null) {
                        notificationFragment = NotificationFragment.newInstance()
                        add(R.id.homeActivityFragContainer, notificationFragment!!)
                    } else {
                        show(notificationFragment!!)
                    }
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
            if (communityFragment != null) hide(communityFragment!!)
            if (notificationFragment != null) hide(notificationFragment!!)
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
        presenter.onDetach()
        _binding = null
    }
}