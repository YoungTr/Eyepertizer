package com.eyepertizer.androidx.base

/**
 * @author youngtr
 * @data 2021/5/14
 */
interface MvpPresenter<V : MvpView> {

    fun onAttach(view: V?)

    fun onDetach()

    fun getMvpView(): V?

}