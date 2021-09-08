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

package com.eyepertizer.androidx.util

import android.util.Log
import com.dianping.logan.Logan
import com.eyepertizer.androidx.BuildConfig

/**
 * 日志调试工具类。
 *
 * @author vipyinzhiwei
 * @since  2020/4/29
 */
private const val VERBOSE = 1
private const val DEBUG = 2
private const val INFO = 3
private const val WARN = 4
private const val ERROR = 5

private val level = if (BuildConfig.DEBUG) VERBOSE else WARN
private const val TAG = "Eyepertizer-"

fun logV(tag: String, msg: String?) {
    if (level <= VERBOSE) {
        Log.v(TAG + tag, msg.toString())
    }
    Logan.w(msg, 1)
}

fun logD(tag: String, msg: String?) {
    if (level <= DEBUG) {
        Log.d(TAG + tag, msg.toString())
    }
    Logan.w(msg, 2)
}

fun logI(tag: String, msg: String?) {
    if (level <= INFO) {
        Log.i(TAG + tag, msg.toString())
    }
    Logan.w(msg, 3)
}

fun logW(tag: String, msg: String?, tr: Throwable? = null) {
    if (level <= WARN) {
        if (tr == null) {
            Log.w(TAG + tag, msg.toString())
        } else {
            Log.w(TAG + tag, msg.toString(), tr)
        }
    }
    Logan.w(msg, 4)
}

fun logE(tag: String, msg: String?, tr: Throwable) {
    if (level <= ERROR) {
        Log.e(TAG + tag, msg.toString(), tr)
    }
    Logan.w(msg, 5)
}

