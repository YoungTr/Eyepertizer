package com.eyepertizer.androidx.base.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.view.MvpView
import com.eyepertizer.androidx.extension.showToast
import com.eyepertizer.androidx.util.logD
import com.gyf.immersionbar.ImmersionBar
import dagger.android.AndroidInjection

/**
 * @author youngtr
 * @data 2021/5/14
 */
abstract class BaseActivity : AppCompatActivity(), MvpView {

    protected var TAG: String = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        performDI()
        super.onCreate(savedInstanceState)
        val rootView = bindView()
        if (rootView != null) {
            setContentView(rootView)
            setStatusBarPrimaryDark()
            setUp()
        }
    }

    open fun setStatusBarPrimaryDark() {
        setStatusBarBackground(R.color.colorPrimaryDark)
    }

    abstract fun bindView(): View?

    open fun setUp() {}


    private fun performDI() {
        AndroidInjection.inject(this)
    }

    override fun showLoading() {
        logD(TAG, "showLoading")
    }

    override fun hideLoading() {
        logD(TAG, "hideLoading")
    }

    override fun showToast(message: String?) {
        message?.showToast()
    }

    /**
     * 设置状态栏背景色
     */
    open fun setStatusBarBackground(@ColorRes statusBarColor: Int) {
        ImmersionBar.with(this).autoStatusBarDarkModeEnable(true, 0.2f)
            .statusBarColor(statusBarColor).fitsSystemWindows(true).init()
    }
}