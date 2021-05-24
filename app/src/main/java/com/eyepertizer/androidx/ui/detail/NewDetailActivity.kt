package com.eyepertizer.androidx.ui.detail

import android.content.Context
import android.content.Intent
import android.view.View
import com.eyepertizer.androidx.base.activity.BaseActivity
import com.eyepertizer.androidx.databinding.ActivityNewDetailBinding

class NewDetailActivity : BaseActivity() {


    private var _binding: ActivityNewDetailBinding? = null
    private val binding: ActivityNewDetailBinding
        get() = _binding!!

    override fun bindView(): View {
        _binding = ActivityNewDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, NewDetailActivity::class.java))
        }
    }
}