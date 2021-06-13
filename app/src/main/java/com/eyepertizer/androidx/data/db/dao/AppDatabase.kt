package com.eyepertizer.androidx.data.db.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eyepertizer.androidx.data.db.dao.repository.search.SearchDao
import com.eyepertizer.androidx.data.db.dao.repository.search.model.SearchHistory

@Database(entities = [SearchHistory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun searchDao(): SearchDao
}