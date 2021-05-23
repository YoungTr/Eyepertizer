package com.eyepertizer.androidx.ui.home.commend.view

import com.eyepertizer.androidx.base.view.MvpView

/**
 * @author youngtr
 * @data 2021/5/22
 */
interface CommendMvpView : MvpView {


    fun setData(data: List<Any>)

    fun addData(data: List<Any>)

    fun closeHeaderOrFooter()

}