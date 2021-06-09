package com.eyepertizer.androidx.ui.community.commend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eyepertizer.androidx.R
import com.eyepertizer.androidx.base.viewmodel.BaseViewCoroutinesModel
import com.eyepertizer.androidx.data.IDataManager
import com.eyepertizer.androidx.data.network.api.MainPageApis
import com.eyepertizer.androidx.data.network.model.CommunityRecommend
import com.eyepertizer.androidx.extension.getString
import com.eyepertizer.androidx.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommendViewModel @Inject constructor(dataManager: IDataManager) :
    BaseViewCoroutinesModel(dataManager) {

    val liveData: MutableLiveData<Resource<CommunityRecommend>> = MutableLiveData()

    private var url: String? = null

    override fun onRefresh() {
        url = MainPageApis.COMMUNITY_RECOMMEND_URL
        fetchCommend()
    }

    override fun onLoadMore() {
        fetchCommend()
    }

    private fun fetchCommend() {
        url?.let { _url ->
            viewModelScope.launch {
                try {
                    val communityRecommend = getDataManager().getCommunityRecommend(_url)
                    url = communityRecommend.nextPageUrl
                    liveData.postValue(Resource.success(communityRecommend))
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