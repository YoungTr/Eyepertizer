package com.eyepertizer.androidx.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eyepertizer.androidx.extension.showToast
import dagger.android.AndroidInjection

/**
 * @author youngtr
 * @data 2021/5/14
 */
abstract class BaseActivity : AppCompatActivity(), MvpView {

    override fun onCreate(savedInstanceState: Bundle?) {
//        performDI()
        super.onCreate(savedInstanceState)
    }

    private fun performDI() {
        AndroidInjection.inject(this)
    }

    override fun showLoading() {
        println("showLoading")
    }

    override fun showToast(message: String?) {
        message?.showToast()
    }
}