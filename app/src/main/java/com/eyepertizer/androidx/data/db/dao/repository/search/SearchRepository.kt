package com.eyepertizer.androidx.data.db.dao.repository.search

import javax.inject.Inject

class SearchRepository @Inject constructor(private val searchDao: SearchDao) : SearchRepo {

    override suspend fun loadSearchHistories(): List<SearchHistory> {
        return searchDao.loadAll()
    }

    override suspend fun insertSearchHistories(vararg searchHistory: SearchHistory) {
        searchDao.insertAll(*searchHistory)
    }

    override suspend fun insertSearchHistory(value: String): SearchHistory? {
        val searchHistory = getSearchHistory(value)
        return if (searchHistory == null) {
            val search = SearchHistory(value = value, time = System.currentTimeMillis())
            insertSearchHistories(search)
            search
        } else {
            null
        }
    }

    override suspend fun deleteAll() {
        searchDao.deleteAll()
    }

    override suspend fun getSearchHistory(value: String): SearchHistory? {
        return searchDao.getSearchHistory(value)
    }
}