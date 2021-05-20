package com.eyepertizer.androidx.ui.main.view

import android.view.View
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.activity.BaseActivity
import com.eyepertizer.androidx.databinding.ActivityMainBinding
import com.eyepertizer.androidx.extension.getString
import com.eyepertizer.androidx.extension.showToast
import com.eyepertizer.androidx.ui.home.HomePageFragment
import com.eyepertizer.androidx.ui.main.presenter.MainPresenter
import com.eyepertizer.androidx.util.GlobalUtil
import com.eyepertizer.androidx.util.logD
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMvpView {

    companion object {
        const val PRESS_BACK_DURATION = 2000L
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