package com.eyepertizer.androidx.extension

import android.widget.Toast
import com.eyepertizer.androidx.EyepertizerApplication
import com.eyepertizer.androidx.util.logD
import com.eyepertizer.androidx.util.vassonic.SonicRuntimeImpl
import com.tencent.sonic.sdk.SonicConfig
import com.tencent.sonic.sdk.SonicEngine
import com.tencent.sonic.sdk.SonicSessionConfig

/**
 * @author youngtr
 * @data 2021/5/16
 */

fun CharSequence.showToast(duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(EyepertizerApplication.context, this, duration).show()

/**
 * VasSonic预加载session。
 *
 * @param CharSequence 预加载url
 */
fun CharSequence.preCreateSession(): Boolean {
    if (!SonicEngine.isGetInstanceAllowed()) {
        SonicEngine.createInstance(SonicRuntimeImpl(EyepertizerApplication.context), SonicConfig.Builder().build())
    }
    val sessionConfigBuilder = SonicSessionConfig.Builder().apply { setSupportLocalServer(true) }
    val preloadSuccess = SonicEngine.getInstance().preCreateSession(this.toString(), sessionConfigBuilder.build())
    logD("preCreateSession()", "${this}\t:${if (preloadSuccess) "Preload start up success!" else "Preload start up fail!"}")
    return preloadSuccess
}