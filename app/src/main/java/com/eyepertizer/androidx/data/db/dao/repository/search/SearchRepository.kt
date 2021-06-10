package com.eyepertizer.androidx.data.db.dao.repository.search

import javax.inject.Inject

class SearchRepository @Inject constructor(private val searchDao: SearchDao) : SearchRepo {

    override suspend fun loadSearchHistories(): List<SearchHistory> {
        return searchDao.getAll()
    }

    override suspend fun insertSearchHistories(vararg searchHistory: SearchHistory) {
        searchDao.insertAll(*searchHistory)
    }

    override suspend fun insertSearchHistory(value: String) {
        val search = SearchHistory(value = value, time = System.currentTimeMillis())
        insertSearchHistories(search)
    }

    override suspend fun deleteAll() {
        searchDao.deleteAll()
    }
}