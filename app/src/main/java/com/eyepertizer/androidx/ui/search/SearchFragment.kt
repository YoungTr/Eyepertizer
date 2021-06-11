package com.eyepertizer.androidx.ui.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.activity.BaseActivity
import com.eyepertizer.androidx.base.fragment.BaseFragment
import com.eyepertizer.androidx.databinding.FragmentSearchBinding
import com.eyepertizer.androidx.extension.setDrawable
import com.eyepertizer.androidx.extension.showToast
import com.eyepertizer.androidx.extension.visibleAlphaAnimation
import com.eyepertizer.androidx.util.logW
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    private var _binding: FragmentSearchBinding? = null

    private val binding: FragmentSearchBinding
        get() = _binding!!

    private lateinit var adapter: HotSearchAdapter

    @Inject
    lateinit var viewModel: SearchHotViewModel

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setUp() {
        binding.llSearch.visibleAlphaAnimation(500)
        binding.etQuery.setDrawable(
            ContextCompat.getDrawable(
                getBaseActivity()!!,
                R.drawable.ic_search_gray_17dp
            ), 14f, 14f
        )
        binding.etQuery.setOnEditorActionListener(EditorActionListener())
        binding.tvCancel.setOnClickListener {
            hideSoftKeyboard()
            removeFragment(getBaseActivity()!!, this)
        }
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        adapter = HotSearchAdapter(this, mutableListOf())
        binding.recyclerView.adapter = adapter
        viewModel.liveData.observe(this, Observer { result ->
            hideLoading()
            binding.etQuery.showSoftKeyboard()
            adapter.setData(result.data as MutableList<String>)
        })
        viewModel.historyLiveData.observe(this, Observer { histories ->
            adapter.addHistories(histories)
        })
    }

    override fun lazyInit() {
        viewModel.onRefresh()
        viewModel.loadHistories()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    /**
     * 隐藏软键盘
     */
    private fun hideSoftKeyboard() {
        try {
            getBaseActivity()?.currentFocus?.run {
                val imm =
                    getBaseActivity()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(this.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        } catch (e: Exception) {
            logW(TAG, e.message, e)
        }
    }

    /**
     * 拉起软键盘
     */
    private fun View.showSoftKeyboard() {
        try {
            this.isFocusable = true
            this.isFocusableInTouchMode = true
            this.requestFocus()
            val manager =
                getBaseActivity()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.showSoftInput(this, 0)
        } catch (e: Exception) {
            logW(TAG, e.message, e)
        }
    }

    inner class EditorActionListener : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (binding.etQuery.text.toString().isEmpty()) {
                    R.string.input_keywords_tips.showToast()
                    return false
                }
                viewModel.insert(binding.etQuery.text.toString())
                R.string.currently_not_supported.showToast()
                return true
            }
            return true
        }
    }

    companion object {

        /**
         * 切换Fragment，会加入回退栈。
         */
        fun switchFragment(activity: Activity) {
            (activity as BaseActivity).supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, SearchFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        /**
         * 先移除Fragment，并将Fragment从堆栈弹出。
         */
        fun removeFragment(activity: Activity, fragment: Fragment) {
            (activity as BaseActivity).supportFragmentManager.run {
                beginTransaction().remove(fragment).commitAllowingStateLoss()
                popBackStack()
            }
        }


        fun newInstance(): SearchFragment {
            val args = Bundle()

            val fragment = SearchFragment()
            fragment.arguments = args
            return fragment
        }
    }
}