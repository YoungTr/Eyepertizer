package com.eyepertizer.androidx.ui.notification.push

import android.os.Bundle
import com.eyepertizer.androidx.base.fragment.BaseRefreshFragment
import com.eyepertizer.androidx.data.network.model.PushMessage
import javax.inject.Inject

class PushFragment :
    BaseRefreshFragment<PushMessage, PushViewModel>() {

    private val adapter: PushAdapter by lazy { PushAdapter(this, arrayListOf()) }

    @Inject
    override lateinit var viewModel: PushViewModel


    override fun isDataNullOrEmpty(data: PushMessage): Boolean {
        return data.itemList.isNullOrEmpty()
    }

    override fun isNextUrlNullOrEmpty(data: PushMessage): Boolean {
        return data.nextPageUrl.isNullOrEmpty()
    }

    override fun addData(data: PushMessage) {
        adapter.addData(data.itemList)
    }

    override fun setData(data: PushMessage) {
        adapter.setData(data.itemList)
    }

    override fun setUpRefresh() {
        binding.recyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): PushFragment {
            val args = Bundle()
            val fragment = PushFragment()
            fragment.arguments = args
            return fragment
        }
    }

}