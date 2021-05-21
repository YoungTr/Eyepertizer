package com.eyepertizer.androidx.ui.splash.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.eyepertizer.androidx.base.activity.BaseActivity
import com.eyepertizer.androidx.databinding.ActivitySplashBinding
import com.eyepertizer.androidx.ui.main.view.MainActivity
import com.eyepertizer.androidx.ui.splash.presenter.SplashPresenter
import com.permissionx.guolindev.PermissionX
import javax.inject.Inject

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashActivity : BaseActivity(), SplashMvpView {

    private var _binding: ActivitySplashBinding? = null
    private val binding: ActivitySplashBinding
        get() = _binding!!

    @Inject
    lateinit var presenter: SplashPresenter<SplashMvpView>

    private val splashDuration = 3 * 1000L

    private val alphaAnimation by lazy {
        AlphaAnimation(0.5f, 1.0f).apply {
            duration = splashDuration
            fillAfter = true
        }
    }

    private val scaleAnimation by lazy {
        ScaleAnimation(
            1f,
            1.05f,
            1f,
            1.05f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).apply {
            duration = splashDuration
            fillAfter = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onAttach(this)
        presenter.requestPermission(PermissionX.init(this))
    }

    override fun bindView(): View? {
        // 获取权限之后再加载页面，这里直接返回 null
        return null
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDetach()
    }

    override fun setUpView() {
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivSlogan.startAnimation(alphaAnimation)
        binding.ivSplashPicture.startAnimation(scaleAnimation)
    }

    override fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}