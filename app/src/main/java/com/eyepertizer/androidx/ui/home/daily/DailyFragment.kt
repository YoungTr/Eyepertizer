package com.eyepertizer.androidx.ui.home.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eyepertizer.androidx.base.fragment.BaseFragment
import com.eyepertizer.androidx.databinding.FragmentRefreshLayoutBinding
import com.eyepertizer.androidx.util.logD

class DailyFragment : BaseFragment() {

    private var _binding: FragmentRefreshLayoutBinding? = null

    private val binding
        get() = _binding!!


    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRefreshLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun setUp() {
        logD(TAG, "set up")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): DailyFragment {
            val args = Bundle()

            val fragment = DailyFragment()
            fragment.arguments = args
            return fragment
        }
    }

}