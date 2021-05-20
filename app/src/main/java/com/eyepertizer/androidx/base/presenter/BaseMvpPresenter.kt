package com.eyepertizer.androidx.base.presenter

import com.eyepertizer.androidx.base.view.MvpView
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.data.IDataManager

/**
 * @author youngtr
 * @data 2021/5/14
 */
abstract class BaseMvpPresenter<V : MvpView> constructor(private val dataManager: AppDataManager) :
    MvpPresenter<V> {

    private var mMvpView: V? = null

    override fun onAttach(view: V?) {
        this.mMvpView = view
    }

    override fun onDetach() {
        this.mMvpView = null
    }

    override fun getMvpView(): V? = mMvpView

    fun getDataManager(): IDataManager = dataManager
}