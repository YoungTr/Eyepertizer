package com.eyepertizer.androidx.base.presenter

import com.eyepertizer.androidx.base.view.MvpView
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.data.IDataManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author youngtr
 * @data 2021/5/14
 */
abstract class BaseMvpPresenter<V : MvpView> constructor(
    private val dataManager: AppDataManager,
    private val compositeDisposable: CompositeDisposable,
) :
    MvpPresenter<V> {

    protected var TAG = this.javaClass.simpleName

    private var mMvpView: V? = null

    override fun onAttach(view: V?) {
        this.mMvpView = view
    }

    override fun onDetach() {
        compositeDisposable.clear()
        this.mMvpView = null
    }

    override fun getMvpView(): V? = mMvpView

    fun getDataManager(): IDataManager = dataManager

    fun addSubscribe(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}