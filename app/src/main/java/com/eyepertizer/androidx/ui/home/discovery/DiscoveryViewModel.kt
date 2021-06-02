package com.eyepertizer.androidx.ui.home.discovery

import androidx.lifecycle.MutableLiveData
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.viewmodel.BaseViewModel
import com.eyepertizer.androidx.data.IDataManager
import com.eyepertizer.androidx.data.network.api.MainPageApis
import com.eyepertizer.androidx.data.network.model.Discovery
import com.eyepertizer.androidx.extension.getString
import com.eyepertizer.androidx.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DiscoveryViewModel @Inject constructor(
    dataManager: IDataManager,
    compositeDisposable: CompositeDisposable,
) :
    BaseViewModel(dataManager, compositeDisposable) {
    val discovery = MutableLiveData<Resource<Discovery>>()
    private var url: String? = null

    private fun fetchDiscovery() {
        url?.let {
            addSubscribe(
                getDataManager().getDiscovery(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        url = response.nextPageUrl
                        discovery.value = Resource.success(response)
                    }, { error ->
                        discovery.value = Resource.error(error.message
                            ?: R.string.data_request_failed.getString(), null)
                    })
            )
        }


    }

    fun onRefresh() {
        url = MainPageApis.DISCOVERY_URL
        fetchDiscovery()
    }

    fun loadMore() {
        fetchDiscovery()
    }
}