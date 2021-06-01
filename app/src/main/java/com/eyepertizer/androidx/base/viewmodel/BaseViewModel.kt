package com.eyepertizer.androidx.base.viewmodel

import androidx.lifecycle.ViewModel
import com.eyepertizer.androidx.data.IDataManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel(
    private val dataManager: IDataManager,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {


    fun getDataManager(): IDataManager = dataManager

    fun addSubscribe(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}