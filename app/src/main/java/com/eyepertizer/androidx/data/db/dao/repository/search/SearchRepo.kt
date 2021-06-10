package com.eyepertizer.androidx.data.db.dao.repository.search

interface SearchRepo {

    suspend fun loadSearchHistories(): List<SearchHistory>

    suspend fun insertSearchHistories(vararg searchHistory: SearchHistory)

    suspend fun insertSearchHistory(value: String)

    suspend fun deleteAll()

}