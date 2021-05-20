package com.eyepertizer.androidx.extension

import android.widget.Toast
import com.eyepertizer.androidx.EyepertizerApplication

/**
 * @author youngtr
 * @data 2021/5/16
 */

fun CharSequence.showToast(duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(EyepertizerApplication.context, this, duration).show()