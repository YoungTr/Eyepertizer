package com.eyepertizer.androidx.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eyepertizer.androidx.base.viewmodel.BaseRefreshViewModel
import com.eyepertizer.androidx.data.IDataManager
import com.eyepertizer.androidx.data.db.dao.repository.search.model.SearchHistory
import com.eyepertizer.androidx.data.db.dao.repository.search.SearchRepo
import com.eyepertizer.androidx.data.network.api.MainPageApis
import com.eyepertizer.androidx.util.logD
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchHotViewModel @Inject constructor(
    dataManager: IDataManager,
    private val searchRepoHelper: SearchRepo,
) :

    BaseRefreshViewModel<List<String>>(dataManager) {

    val historyLiveData = MutableLiveData<List<SearchHistory>>()

    override fun getURL(): String {
        return MainPageApis.PUSHMESSAGE_URL
    }

    fun loadHistories() {
        viewModelScope.launch {
            val loadSearchHistories = searchRepoHelper.loadSearchHistories()
            logD(TAG, loadSearchHistories.joinToString())
            historyLiveData.postValue(loadSearchHistories)
        }
    }


    override suspend fun fetchData(_url: String): List<String>? {
        return getDataManager().getHotSearch()
    }

    override fun getNextUrl(data: List<String>?): String? {
        return null
    }

    fun insert(value: String) {
        viewModelScope.launch {
            val insertSearchHistory = searchRepoHelper.insertSearchHistory(value)
            if (insertSearchHistory != null) {
                historyLiveData.postValue(listOf(insertSearchHistory))
            } else {
                logD(TAG, "$value exit")
            }
        }
    }

    companion object {
        const val TAG = "SearchHotViewModel"
    }

}