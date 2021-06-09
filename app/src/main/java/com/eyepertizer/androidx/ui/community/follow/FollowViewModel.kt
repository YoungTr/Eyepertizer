package com.eyepertizer.androidx.ui.community.follow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.viewmodel.BaseViewCoroutinesModel
import com.eyepertizer.androidx.data.IDataManager
import com.eyepertizer.androidx.data.network.api.MainPageApis
import com.eyepertizer.androidx.data.network.model.Follow
import com.eyepertizer.androidx.extension.getString
import com.eyepertizer.androidx.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class FollowViewModel @Inject constructor(dataManager: IDataManager) :
    BaseViewCoroutinesModel(dataManager) {

    val liveData: MutableLiveData<Resource<Follow>> = MutableLiveData()
    private var url: String? = null

    override fun onRefresh() {
        url = MainPageApis.FOLLOW_URL
        fetchCommend()
    }

    override fun onLoadMore() {
        fetchCommend()
    }

    private fun fetchCommend() {
        url?.let { _url ->
            viewModelScope.launch {
                try {
                    val follow = getDataManager().getFollow(_url)
                    url = follow.nextPageUrl
                    liveData.postValue(Resource.success(follow))
                } catch (e: Exception) {
                    liveData.postValue(
                        Resource.error(
                            e.message
                                ?: R.string.data_request_failed.getString(), null
                        )
                    )
                }
            }
        }

    }


}