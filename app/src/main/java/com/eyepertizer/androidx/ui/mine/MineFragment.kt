package com.eyepertizer.androidx.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.fragment.BaseFragment
import com.eyepertizer.androidx.constants.Const
import com.eyepertizer.androidx.databinding.FragmentMineBinding
import com.eyepertizer.androidx.extension.setOnClickListener
import com.eyepertizer.androidx.ui.login.LoginActivity
import com.eyepertizer.androidx.ui.web.WebViewActivity
import com.eyepertizer.androidx.ui.web.WebViewActivity.Companion.MODE_SONIC_WITH_OFFLINE_CACHE
import com.eyepertizer.androidx.util.GlobalUtil

class MineFragment : BaseFragment() {

    override fun performDI() {

    }

    private var _binding: FragmentMineBinding? = null

    private val binding: FragmentMineBinding
        get() = _binding!!

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setUp() {
        binding.tvVersionNumber.text = String.format(
            GlobalUtil.getString(R.string.version_show),
            GlobalUtil.eyepetizerVersionName
        )
        setOnClickListener(
            binding.ivMore,
            binding.ivAvatar,
            binding.tvLoginTips,
            binding.tvFavorites,
            binding.tvCache,
            binding.tvFollow,
            binding.tvWatchRecord,
            binding.tvNotificationToggle,
            binding.tvMyBadge,
            binding.tvFeedback,
            binding.tvContribute,
            binding.tvVersionNumber,
            getRootView(),
            binding.llScrollViewContent
        ) {
            when (this) {
//                binding.ivMore -> SettingActivity.start(activity)

                binding.ivAvatar, binding.tvLoginTips, binding.tvFavorites, binding.tvCache, binding.tvFollow, binding.tvWatchRecord, binding.tvNotificationToggle, binding.tvMyBadge -> {
                    LoginActivity.start(getBaseActivity()!!)
                }
                binding.tvContribute -> {
                    WebViewActivity.start(
                        getBaseActivity()!!,
                        WebViewActivity.DEFAULT_TITLE,
                        Const.Url.AUTHOR_OPEN,
                        false,
                        false
                    )
                }
                binding.tvFeedback -> {
                    WebViewActivity.start(
                        getBaseActivity()!!,
                        WebViewActivity.DEFAULT_TITLE,
                        WebViewActivity.DEFAULT_URL,
                        true,
                        false,
                        MODE_SONIC_WITH_OFFLINE_CACHE
                    )
                }
                binding.tvVersionNumber -> {
                    WebViewActivity.start(
                        getBaseActivity()!!,
                        WebViewActivity.DEFAULT_TITLE,
                        WebViewActivity.DEFAULT_URL,
                        true,
                        false,
                        MODE_SONIC_WITH_OFFLINE_CACHE
                    )
                }
                this@MineFragment.getRootView(), binding.llScrollViewContent -> {
//                    MobclickAgent.onEvent(activity, Const.Mobclick.EVENT4)
//                    AboutActivity.start(activity)
                }
            }
        }
    }

    companion object {
        fun newInstance(): MineFragment {
            val args = Bundle()

            val fragment = MineFragment()
            fragment.arguments = args
            return fragment
        }
    }
}