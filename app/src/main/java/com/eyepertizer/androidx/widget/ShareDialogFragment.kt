/*
 * Copyright (c) 2020. vipyinzhiwei <vipyinzhiwei@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eyepertizer.androidx.widget

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.databinding.FragmentShareDialogBinding
import com.eyepertizer.androidx.extension.setDrawable
import com.eyepertizer.androidx.extension.share
import com.eyepertizer.androidx.util.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 * 分享对话框的弹出界面。
 *
 * @author vipyinzhiwei
 * @since  2020/5/24
 */
open class ShareDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentShareDialogBinding? = null

    private val binding
        get() = _binding!!


    private lateinit var shareContent: String

    private lateinit var attachedActivity: Activity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShareDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let { act ->
            attachedActivity = act
            binding.tvToWechatFriends.setDrawable(
                ContextCompat.getDrawable(
                    act,
                    R.drawable.ic_share_wechat_black_30dp
                ), 30f, 30f, 1
            )
            binding.tvShareToWeibo.setDrawable(
                ContextCompat.getDrawable(
                    act,
                    R.drawable.ic_share_weibo_black_30dp
                ), 30f, 30f, 1
            )
            binding.tvShareToQQ.setDrawable(
                ContextCompat.getDrawable(
                    act,
                    R.drawable.ic_share_qq_black_30dp
                ), 30f, 30f, 1
            )
            binding.tvShareToQQzone.setDrawable(
                ContextCompat.getDrawable(
                    act,
                    R.drawable.ic_share_qq_zone_black_30dp
                ), 30f, 30f, 1
            )

            binding.tvShareToQQ.setOnClickListener {
                share(attachedActivity, shareContent, SHARE_QQ)
                dismiss()
            }
            binding.tvToWechatFriends.setOnClickListener {
                share(attachedActivity, shareContent, SHARE_WECHAT)
                dismiss()
            }
            binding.tvShareToWeibo.setOnClickListener {
                share(attachedActivity, shareContent, SHARE_WEIBO)
                dismiss()
            }
            binding.tvShareToQQzone.setOnClickListener {
                share(attachedActivity, shareContent, SHARE_QQZONE)
                dismiss()
            }
            binding.llMore.setOnClickListener {
                share(attachedActivity, shareContent, SHARE_MORE)
                dismiss()
            }
            binding.tvCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    fun showDialog(activity: AppCompatActivity, shareContent: String) {
        show(activity.supportFragmentManager, "share_dialog")
        this.shareContent = shareContent
    }
}