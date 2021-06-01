package com.eyepertizer.androidx.ui.home.daily

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eyepertizer.androidx.base.viewmodel.BaseViewCoroutinesModel
import com.eyepertizer.androidx.data.IDataManager
import com.eyepertizer.androidx.data.network.api.MainPageApis
import com.eyepertizer.androidx.data.network.model.Daily
import com.eyepertizer.androidx.util.Resource
import com.eyepertizer.androidx.util.logD
import kotlinx.coroutines.launch
import javax.inject.Inject

class DailyViewModel @Inject constructor(dataManager: IDataManager) :
    BaseViewCoroutinesModel(dataManager) {

    private val daily = MutableLiveData<Resource<Daily>>()
    private var url: String? = null

    init {
        fetchDaily()
    }

    private fun fetchDaily() {
        viewModelScope.launch {
            try {
                url?.let {
                    val dailyFromApi = getDataManager().getDaily(it)
                    url = dailyFromApi.nextPageUrl
                    logD(TAG, "url: $url")
                    logD(TAG, dailyFromApi.itemList.size.toString())
                    daily.postValue(Resource.success(dailyFromApi))
                }
            } catch (e: Exception) {
                daily.postValue(Resource.error(e.message!!, null))
            }
        }
    }

    fun onRefresh() {
        url = MainPageApis.DAILY_URL
        fetchDaily()
    }

    fun loadMore() {
        fetchDaily()
    }

    fun getDaily() = daily

    companion object {
        const val TAG = "DailyViewModel"
    }
}