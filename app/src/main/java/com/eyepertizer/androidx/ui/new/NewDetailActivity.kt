package com.eyepertizer.androidx.ui.new

import android.view.View
import com.eyepertizer.androidx.base.activity.BaseActivity
import com.eyepertizer.androidx.databinding.ActivityNewDetailBinding
import com.eyepertizer.androidx.ui.splash.presenter.SplashPresenter
import com.eyepertizer.androidx.ui.splash.view.SplashMvpView
import javax.inject.Inject

class NewDetailActivity : BaseActivity() {
    @Inject
    lateinit var presenter: SplashPresenter<SplashMvpView>


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
//        fun start(context: Context) {
//            context.startActivity(Intent(context, NewDetailActivity::class.java))
//        }
    }
}