package com.eyepertizer.androidx.data.db.dao

import androidx.room.*
import com.eyepertizer.androidx.data.db.model.SearchHistory

@Dao
interface SearchDao {

    @Query("SELECT * FROM search_history")
    fun getAll(): List<SearchHistory>

    @Query("SELECT * FROM search_history WHERE id IN (:searchIds)")
    fun loadAllByIds(searchIds: IntArray): List<SearchHistory>

    @Insert
    suspend fun insertAll(vararg searches: SearchHistory)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(search: SearchHistory)

    @Delete
    suspend fun delete(search: SearchHistory)

    @Delete
    suspend fun deleteAll(vararg searches: SearchHistory)
}