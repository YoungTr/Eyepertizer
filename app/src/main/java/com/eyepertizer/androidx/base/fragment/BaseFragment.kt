package com.eyepertizer.androidx.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.activity.BaseActivity
import com.eyepertizer.androidx.base.view.MvpView
import com.eyepertizer.androidx.extension.showToast
import com.eyepertizer.androidx.util.logD
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment(), MvpView {

    /**
     * 日志输出标志
     */
    protected val TAG: String = this.javaClass.simpleName

    private var parentActivity: BaseActivity? = null

    /**
     * Fragment中由于服务器或网络异常导致加载失败显示的布局。
     */
    private var loadErrorView: View? = null

    /**
     * Fragment中inflate出来的布局。
     */
    private var rootView: View? = null

    /**
     * Fragment中显示加载等待的控件。
     */
    private var loading: ProgressBar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.parentActivity = activity

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDI()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = bindView(inflater, container, savedInstanceState)
        onCreateView(view)
        return view
    }

    abstract fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View


    private fun onCreateView(view: View) {
        logD(TAG, "BaseFragment-->onCreateView()")
        rootView = view
        loading = view.findViewById(R.id.loading)
//        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this)
    }

    private fun performDI() {
        AndroidSupportInjection.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    abstract fun setUp()

    fun getBaseActivity() = parentActivity

    fun getRootView() = rootView

    override fun showLoading() {
        loading?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading?.visibility = View.GONE
    }

    override fun showToast(message: String?) {
        message?.showToast()
    }
}