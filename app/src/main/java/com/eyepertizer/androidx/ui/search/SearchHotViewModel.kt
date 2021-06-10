package com.eyepertizer.androidx.ui.search

import com.eyepertizer.androidx.base.viewmodel.BaseRefreshViewModel
import com.eyepertizer.androidx.data.IDataManager
import com.eyepertizer.androidx.data.db.dao.repository.search.SearchRepo
import com.eyepertizer.androidx.data.network.api.MainPageApis
import javax.inject.Inject

class SearchHotViewModel @Inject constructor(
    dataManager: IDataManager,
    private val searchRepoHelper: SearchRepo
) :

    BaseRefreshViewModel<List<String>>(dataManager) {

    override fun getURL(): String {
        return MainPageApis.PUSHMESSAGE_URL
    }


    override suspend fun fetchData(_url: String): List<String>? {
        return getDataManager().getHotSearch()
    }

    override fun getNextUrl(data: List<String>?): String? {
        return null
    }


}