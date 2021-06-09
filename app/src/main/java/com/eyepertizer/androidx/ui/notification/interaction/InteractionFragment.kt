package com.eyepertizer.androidx.ui.notification.interaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eyepertizer.androidx.base.fragment.BaseFragment
import com.eyepertizer.androidx.databinding.FragmentNotificationLoginTipsBinding
import com.eyepertizer.androidx.ui.login.LoginActivity

class InteractionFragment : BaseFragment() {

    private var _binding: FragmentNotificationLoginTipsBinding? = null

    private val binding: FragmentNotificationLoginTipsBinding
        get() = _binding!!


    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationLoginTipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setUp() {
        binding.tvLogin.setOnClickListener { LoginActivity.start(getBaseActivity()!!) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): InteractionFragment {
            val args = Bundle()

            val fragment = InteractionFragment()
            fragment.arguments = args
            return fragment
        }
    }
}