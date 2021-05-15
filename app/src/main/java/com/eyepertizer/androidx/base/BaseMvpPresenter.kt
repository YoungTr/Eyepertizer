package com.eyepertizer.androidx.base

/**
 * @author youngtr
 * @data 2021/5/14
 */
abstract class BaseMvpPresenter<V : MvpView>() : MvpPresenter<V> {

    private var mMvpView: V? = null

    override fun onAttach(view: V?) {
        this.mMvpView = view
    }

    override fun onDetach() {
        this.mMvpView = null
    }

    override fun getMvpView(): V? = mMvpView
}