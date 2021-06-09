package com.eyepertizer.androidx.base.viewmodel

import androidx.lifecycle.ViewModel
import com.eyepertizer.androidx.data.IDataManager

abstract class BaseViewCoroutinesModel(
    private val dataManager: IDataManager
) : ViewModel() {

    protected fun getDataManager(): IDataManager = dataManager

    override fun onCleared() {
        super.onCleared()
    }

    abstract fun onRefresh()


    abstract fun onLoadMore()
}