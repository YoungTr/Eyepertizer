package com.eyepertizer.androidx.ui.main.view

import android.os.Bundle
import com.eyepertizer.androidx.base.BaseActivity
import com.eyepertizer.androidx.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    var _binding: ActivityMainBinding? = null
    val binding: ActivityMainBinding
        get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}