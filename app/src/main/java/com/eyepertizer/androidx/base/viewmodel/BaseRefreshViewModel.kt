package com.eyepertizer.androidx.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.data.IDataManager
import com.eyepertizer.androidx.extension.getString
import com.eyepertizer.androidx.util.Resource
import kotlinx.coroutines.launch

abstract class BaseRefreshViewModel<T>(
    private val dataManager: IDataManager
) : ViewModel() {

    val liveData = MutableLiveData<Resource<T>>()
    protected var url: String? = null

    protected fun getDataManager(): IDataManager = dataManager


    fun onRefresh() {
        url = getURL()
        getData()
    }

    abstract fun getURL(): String


    fun onLoadMore() {
        getData()
    }

    private fun getData() {
        url?.let { _url ->
            viewModelScope.launch {
                try {
                    val data = fetchData(_url)
                    url = getNextUrl(data)
                    liveData.postValue(Resource.success(data))
                } catch (e: Exception) {
                    liveData.postValue(
                        Resource.error(
                            e.message ?: R.string.data_request_failed.getString(), null
                        )
                    )
                }
            }

        }
    }

    abstract fun getNextUrl(data: T?): String?

    abstract suspend fun fetchData(_url: String): T?


}