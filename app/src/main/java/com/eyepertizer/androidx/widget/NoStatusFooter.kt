/*
 * Copyright (c) 2020. vipyinzhiwei <vipyinzhiwei@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.extension.dp2px
import com.eyepertizer.androidx.util.TypeFaceUtil
import com.eyepertizer.androidx.util.logD
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshKernel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.simple.SimpleComponent


/**
 * SmartRefreshLayout自定义Footer，上拉加载时没有文字和加载进度提示。
 *
 * @author vipyinzhiwei
 * @since  2020/5/29
 */
class NoStatusFooter : SimpleComponent, RefreshFooter {

    private var mTextNothing = "" //没有更多数据了

    private var mTitleText: TextView

    private var mFooterHeight = 0

    private var mBackgroundColor = 0

    private var mNoMoreData = false

    private var mRefreshKernel: RefreshKernel? = null

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        View.inflate(context, R.layout.layout_srl_classics_footer, this)
        mTitleText = findViewById(R.id.srl_classics_title)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NoStatusFooter, 0, 0)
        typedArray.let {
            if (it.hasValue(R.styleable.NoStatusFooter_srlPrimaryColor)) {
                setPrimaryColor(it.getColor(R.styleable.NoStatusFooter_srlPrimaryColor, 0))
            }
            if (it.hasValue(R.styleable.NoStatusFooter_srlAccentColor)) {
                setAccentColor(it.getColor(R.styleable.NoStatusFooter_srlAccentColor, 0))
            }
            if (it.hasValue(R.styleable.NoStatusFooter_srlTextSizeTitle)) {
                mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.getDimensionPixelSize(R.styleable.NoStatusFooter_srlTextSizeTitle, dp2px(16f)).toFloat())
            }
            mTextNothing = when {
                it.hasValue(R.styleable.NoStatusFooter_srlTextNothing) -> {
                    it.getString(R.styleable.NoStatusFooter_srlTextNothing) ?: context.getString(R.string.srl_footer_nothing)
                }
                REFRESH_FOOTER_NOTHING != null -> {
                    REFRESH_FOOTER_NOTHING!!
                }
                else -> {
                    context.getString(R.string.srl_footer_nothing)
                }
            }
            mTitleText.typeface = TypefaceTextView.getTypeface(it.getInt(R.styleable.NoStatusFooter_srlTextTitleTypeface, TypeFaceUtil.LOBSTER_TYPEFACE))
            typedArray.recycle()
        }
    }

    override fun onInitialized(@NonNull kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
        logD(TAG, "onInitialized: height=${height},noMoreData=${mNoMoreData}")
        mRefreshKernel = kernel
        mRefreshKernel?.requestDrawBackgroundFor(this, mBackgroundColor)
        if (mFooterHeight == 0) mFooterHeight = height    //获取SmartRefreshLayout全局设置的Footer高度。
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        logD(TAG, "onFinish: $success")
        super.onFinish(refreshLayout, success)
        return 0
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    override fun setNoMoreData(noMoreData: Boolean): Boolean {
        logD(TAG, "setNoMoreData: $noMoreData")
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData
            refreshFooterHeight()
            if (noMoreData) {
                mTitleText.text = mTextNothing
            }
        }
        return true
    }

    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
        super.onStateChanged(refreshLayout, oldState, newState)
        logD(TAG, "onStateChanged: newState=${newState},noMoreData=${mNoMoreData}")
        if (!mNoMoreData) {
            when (newState) {
                RefreshState.None -> {

                }
                RefreshState.PullUpToLoad -> {
                    refreshFooterHeight()
                }
                RefreshState.Loading, RefreshState.LoadReleased -> {

                }
                RefreshState.ReleaseToLoad -> {

                }
                RefreshState.Refreshing -> {

                }
                else -> {

                }
            }
        } else {
            refreshFooterHeight()
        }
    }

    private fun refreshFooterHeight() {
        logD(TAG, "refreshFooterHeight: noMoreData=${mNoMoreData}")
        if (mNoMoreData) {
            mRefreshKernel?.refreshLayout?.setFooterHeightPx(mFooterHeight)
        } else {
            mRefreshKernel?.refreshLayout?.setFooterHeight(0f)
        }
        mRefreshKernel?.requestRemeasureHeightFor(this)
    }

    fun setTextTitleSize(size: Float): NoStatusFooter {
        mTitleText.textSize = size
        mRefreshKernel?.requestRemeasureHeightFor(this)
        return this
    }

    fun setTextTitleTypeface(tf: Typeface): NoStatusFooter {
        mTitleText.typeface = tf
        mRefreshKernel?.requestRemeasureHeightFor(this)
        return this
    }

    fun setPrimaryColor(@ColorInt primaryColor: Int): NoStatusFooter {
        mBackgroundColor = primaryColor
        mRefreshKernel?.requestDrawBackgroundFor(this, primaryColor)
        return this
    }

    fun setAccentColor(@ColorInt accentColor: Int): NoStatusFooter {
        mTitleText.setTextColor(accentColor)
        return this
    }

    fun setAccentColorId(@ColorRes colorId: Int): NoStatusFooter {
        val thisView: View = this
        setAccentColor(ContextCompat.getColor(thisView.context, colorId))
        return this
    }

    companion object {
        const val TAG = "NoStatusFooter"
        var REFRESH_FOOTER_NOTHING: String? = null      //没有更多数据了
    }
}