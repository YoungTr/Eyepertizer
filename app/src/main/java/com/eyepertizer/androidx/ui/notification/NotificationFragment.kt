package com.eyepertizer.androidx.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.fragment.BaseViewPagerFragment
import com.eyepertizer.androidx.databinding.FragmentMainContainerBinding
import com.eyepertizer.androidx.ui.model.TabEntity
import com.eyepertizer.androidx.ui.notification.inbox.InboxFragment
import com.eyepertizer.androidx.ui.notification.interaction.InteractionFragment
import com.eyepertizer.androidx.ui.notification.push.PushFragment
import com.eyepertizer.androidx.util.GlobalUtil
import com.eyepertizer.androidx.util.logD
import com.flyco.tablayout.listener.CustomTabEntity

class NotificationFragment : BaseViewPagerFragment() {

    private var _binding: FragmentMainContainerBinding? = null

    private val binding
        get() = _binding!!


    override val createTitles = ArrayList<CustomTabEntity>().apply {
        add(TabEntity(GlobalUtil.getString(R.string.push)))
        add(TabEntity(GlobalUtil.getString(R.string.interaction)))
        add(TabEntity(GlobalUtil.getString(R.string.inbox)))
    }

    override val createFragments: Array<Fragment> = arrayOf(
        PushFragment.newInstance(),
        InteractionFragment.newInstance(),
        InboxFragment.newInstance()
    )


    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainContainerBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun viewPagerSetUp() {
        logD(TAG, "view pager setup")
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance(): NotificationFragment {
            val args = Bundle()

            val fragment = NotificationFragment()
            fragment.arguments = args
            return fragment
        }
    }
}