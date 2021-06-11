package com.eyepertizer.androidx.data.db.dao.repository.search

import androidx.room.*

@Dao
interface SearchDao {

    @Query("SELECT * FROM search_history")
    suspend fun loadAll(): List<SearchHistory>

    @Query("SELECT * FROM search_history WHERE id IN (:searchIds)")
    suspend fun loadAllByIds(searchIds: IntArray): List<SearchHistory>

    @Query("SELECT * FROM search_history WHERE value = (:value)")
    suspend fun getSearchHistory(value: String): SearchHistory?

    @Insert
    suspend fun insertAll(vararg searches: SearchHistory)

    @Insert
    suspend fun insert(searches: SearchHistory)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(search: SearchHistory)

    @Delete
    suspend fun delete(search: SearchHistory)

    @Delete
    suspend fun deleteSearchHistories(vararg searches: SearchHistory)

    @Query("DELETE FROM search_history")
    suspend fun deleteAll()
}