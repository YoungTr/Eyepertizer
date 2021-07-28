package com.eyepertizer.androidx

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import androidx.multidex.MultiDex
import com.didichuxing.doraemonkit.DoKit
import com.didichuxing.doraemonkit.DoKitCallBack
import com.didichuxing.doraemonkit.kit.core.McClientProcessor
import com.didichuxing.doraemonkit.kit.network.NetworkManager
import com.didichuxing.doraemonkit.kit.network.bean.NetworkRecord
import com.didichuxing.doraemonkit.kit.network.okhttp.interceptor.DokitExtInterceptor
import com.eyepertizer.androidx.di.component.AppComponent
import com.eyepertizer.androidx.di.component.DaggerAppComponent
import com.eyepertizer.androidx.di.module.AppModule
import com.eyepertizer.androidx.extension.showToast
import com.eyepertizer.androidx.util.GlobalUtil
import com.eyepertizer.androidx.widget.NoStatusFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * @author youngtr
 * @data 2021/5/16
 */
class EyepertizerApplication : Application(), HasAndroidInjector {

    init {
        SmartRefreshLayout.setDefaultRefreshInitializer { _, layout ->
            layout.setEnableLoadMore(true)
            layout.setEnableLoadMoreWhenContentNotFull(true)
        }

        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setEnableHeaderTranslationContent(true)
            MaterialHeader(context).setColorSchemeResources(
                R.color.blue,
                R.color.blue,
                R.color.blue
            )
        }

        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setEnableFooterFollowWhenNoMoreData(true)
            layout.setEnableFooterTranslationContent(true)
            layout.setFooterHeight(153f)
            layout.setFooterTriggerRate(0.6f)
            NoStatusFooter.REFRESH_FOOTER_NOTHING = GlobalUtil.getString(R.string.footer_not_more)
            NoStatusFooter(context).apply {
                setAccentColorId(R.color.colorTextPrimary)
                setTextTitleSize(16f)
            }
        }
    }

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Any>


    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Application
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        val component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        component.inject(this)
        appComponent = component

        DoKit.Builder(this)
            .productId("032816b2650df80d886f300e861eb3bf")
            .disableUpload()
            .fileManagerHttpPort(9001)
            .databasePass(mapOf("Person.db" to "a_password"))
            .mcWSPort(5555)
            .alwaysShowMainIcon(true)
            .callBack(object : DoKitCallBack {
                override fun onCpuCallBack(value: Float, filePath: String) {
                    super.onCpuCallBack(value, filePath)
                }

                override fun onFpsCallBack(value: Float, filePath: String) {
                    super.onFpsCallBack(value, filePath)
                }

                override fun onMemoryCallBack(value: Float, filePath: String) {
                    super.onMemoryCallBack(value, filePath)
                }

                override fun onNetworkCallBack(record: NetworkRecord) {
                    super.onNetworkCallBack(record)
                }
            })
            .netExtInterceptor(object : DokitExtInterceptor.DokitExtInterceptorProxy {
                override fun intercept(chain: Interceptor.Chain): Response {
                    return chain.proceed(chain.request())
                }

            })
            .mcClientProcess(object : McClientProcessor {
                override fun process(
                    activity: Activity?,
                    view: View?,
                    eventType: String,
                    params: Map<String, String>
                ) {
                    when (eventType) {
                        "un_lock" -> {
                            params["unlock"]?.showToast()
                        }
                        "lock_process" -> {
                            params["progress"]?.showToast()


                        }
                        else -> {

                        }
                    }

                }

            })
            .build()

        if (!NetworkManager.isActive()) {
            NetworkManager.get().startMonitor()
        }


    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingActivityInjector
}